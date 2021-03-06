package com.example.android.wednesday.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by hp pc on 1/22/2017.
 */

@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String userPhoto = null;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
