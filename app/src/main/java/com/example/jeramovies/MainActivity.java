package com.example.jeramovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputLogin = login.getText().toString();
                String inputPassword = password.getText().toString();

                if (inputLogin.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Usuário ou senha não inseridos!", Toast.LENGTH_SHORT).show();
                } else{
                    validateLogin();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void validateLogin(){

    }
}