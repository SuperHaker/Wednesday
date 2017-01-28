package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.example.android.wednesday.helpers.CredentialHelper;
import com.example.android.wednesday.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "Login";


    @BindView(R.id.name)
    EditText nameField;
    @BindView(R.id.email)
    EditText emailField;
    @BindView(R.id.password)
    EditText passwordField;
    @BindView(R.id.confirmPassword)
    EditText confirmPasswordField;
    @BindView(R.id.email_sign_in_button)
    Button signInButton;
    @BindView(R.id.signup_progress)
    ProgressBar progressBar;




    private FirebaseAuth mAuth;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();




        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createAccount(emailField.getText().toString(), passwordField.getText().toString());
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);

        if(CredentialHelper.checkIfEmpty(nameField, this)){
            return;
        }

        if(CredentialHelper.checkIfEmpty(emailField, this)){
            return;
        }

        if(CredentialHelper.checkIfEmpty(passwordField, this)){
            return;
        }


        if(CredentialHelper.checkIfEmpty(confirmPasswordField, this)){
            return;
        }

        if (!(CredentialHelper.checkPasswordValid(passwordField, this) && CredentialHelper.isEmailValid(emailField.getText().toString()))) {
            return;
        }

        if(!passwordField.getText().toString().equals(confirmPasswordField.getText().toString()) ){
            confirmPasswordField.setError("Passwords do not match");
            return;
        }


//        showProgressDialog();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(SignUpActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(getApplicationContext(), "Auth failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mAuth.getCurrentUser().sendEmailVerification();
                            addUserProfileInfo(task.getResult().getUser());
                            callMainActivity();

                        }

//                        hideProgressDialog();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    public void addUserProfileInfo(FirebaseUser registeredUser){
        String name = nameField.getText().toString();
        String email = registeredUser.getEmail();
        String userId = registeredUser.getUid();
        User user = new User(name, email);
        mDatabaseReference.child("users").child(userId).setValue(user);


    }

    public void callMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginOption)
    public void gotoLogin(){
        Intent intent  = new Intent(getApplicationContext(), LogInActivity.class);
        startActivity(intent);
    }


}

