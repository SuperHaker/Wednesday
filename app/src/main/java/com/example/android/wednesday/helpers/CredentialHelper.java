package com.example.android.wednesday.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by hp pc on 1/20/2017.
 */

public class CredentialHelper {

    private static Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z]\\w{3,14}$"); //^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,64}$

    public static boolean isEmailValid(String email) {
        email = email.trim();
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean checkPasswordValid(EditText password, Context context) {
        if (password == null)
            throw new IllegalArgumentException("No Edittext hosted!");
        if (!isPasswordValid(password.getText().toString())) {
            password.setError("Enter a combination of at least six characters, containing lower- and uppercase letters and numbers");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    public static boolean checkIfEmpty(EditText inputLayout, Context context) {
        if (inputLayout == null)
            throw new IllegalArgumentException("No Edittext hosted!");
        if (TextUtils.isEmpty(inputLayout.getText().toString())) {
            inputLayout.setError("This field cannot be empty");
            return true;
        } else {
            inputLayout.setError(null);
            return false;
        }
    }

}
