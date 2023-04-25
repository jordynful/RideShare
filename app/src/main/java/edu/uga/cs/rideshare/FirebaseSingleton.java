package edu.uga.cs.rideshare;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseSingleton extends Application{
        private FirebaseAuth mAuth;

        @Override
        public void onCreate() {
            super.onCreate();
            mAuth = FirebaseAuth.getInstance();
        }

        public FirebaseAuth getFirebaseAuth() {
            return mAuth;
        }

}
