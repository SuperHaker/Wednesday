package com.example.android.wednesday.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.wednesday.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogInActivity extends AppCompatActivity {

    @BindView(R.id.emailLogin)
    EditText loginEmail;
    @BindView(R.id.passwordLogin)
    EditText loginPassword;
    @BindView(R.id.buttonLogin)
    Button loginButton;
    @BindView(R.id.signupOption)
    TextView signupOption;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    // User is signed out


                }
                // ...
            }
        };
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);






    }

    @OnClick(R.id.buttonLogin)
    public void login(){

        String email = loginEmail.getText().toString();
        final String password = loginPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            loginEmail.setError("Enter email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            loginPassword.setError("Enter password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "auth_failed", Toast
                                    .LENGTH_LONG).show();
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }

                    }
                });

    }

    @OnClick(R.id.signupOption)
    public void signUpButtonclicked(){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.forgotPassword)
    public void resetPassword() {
        if (!TextUtils.isEmpty(loginEmail.getText().toString())) {
            mAuth.sendPasswordResetEmail(loginEmail.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                        }
                    });
        }
        else{
            loginEmail.setError("Enter email");
        }
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
}


