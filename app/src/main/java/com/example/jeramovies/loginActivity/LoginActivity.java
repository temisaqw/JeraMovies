package com.example.jeramovies.loginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.jeramovies.activity.ContasActivity;
import com.example.jeramovies.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends Activity {
    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private Button faceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        faceButton = findViewById(R.id.faceButton);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(LoginActivity.this, ContasActivity.class));
                finish();
                Toast.makeText(LoginActivity.this, "Logado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login cancelado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Toast.makeText(LoginActivity.this, "Falha ao logar", Toast.LENGTH_SHORT).show();
                Log.d("Erro: ", e.toString());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentaLogarUsuario();
            }
        });

        faceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_birthday"));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });
    }

    private void tentaLogarUsuario() {
        String inputLogin = login.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();
        if (inputLogin.isEmpty() || inputPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Usu??rio ou senha n??o inseridos!", Toast.LENGTH_SHORT).show();
        } else {
            logarUsuario(inputLogin, inputPassword);
        }
    }

    private void logarUsuario(String inputLogin, String inputPassword) {
        mAuth.signInWithEmailAndPassword(inputLogin, inputPassword)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, ContasActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Falha ao logar!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}