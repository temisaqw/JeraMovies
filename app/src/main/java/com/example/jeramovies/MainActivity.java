package com.example.jeramovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.jeramovies.activity.ContasActivity;
import com.example.jeramovies.loginActivity.LoginActivity;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
     private FirebaseAuth mAuth;
     AccessToken accessToken;

     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
            mAuth = FirebaseAuth.getInstance();
            accessToken = AccessToken.getCurrentAccessToken();
        }

        @Override
        protected void onStart() {
            super.onStart();
            if (mAuth.getCurrentUser() == null || accessToken == null) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, ContasActivity.class));
            }
     }
}