package com.edu.auri.backend.registration.signup;
import android.util.Log;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;


class signUpManager {
    private static final String TAG = "SignUpManager";
    private FirebaseAuth auth;

    public signUpManager() {
        auth = FirebaseAuth.getInstance();

    }
}

