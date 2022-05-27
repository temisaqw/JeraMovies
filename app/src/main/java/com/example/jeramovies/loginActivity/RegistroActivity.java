package com.example.jeramovies.loginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.jeramovies.activity.ContasActivity;
import com.example.jeramovies.R;
import com.example.jeramovies.objetos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends Activity {

    private EditText nome, email, senha, data;
    private Button registrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_screen);

        mAuth = FirebaseAuth.getInstance();
        registrar = findViewById(R.id.registroButton);
        nome = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        senha = findViewById(R.id.editTextTextPassword);
        data = findViewById(R.id.editTextDate);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificaLogin();
            }
        });
    }

    private void verificaLogin() {
        String nomeRegistro = nome.getText().toString().trim();
        String emailRegistro = email.getText().toString().trim();
        String senhaRegistro = senha.getText().toString().trim();
        String dataRegistro = data.getText().toString().trim();

        if (!nomeRegistro.isEmpty()) {
            if (Patterns.EMAIL_ADDRESS.matcher(emailRegistro).matches()) {
                if (!senhaRegistro.isEmpty()) {
                    if (!dataRegistro.isEmpty()) {
                        registrarUsuario(nomeRegistro, emailRegistro, senhaRegistro, dataRegistro);
                    } else {
                        data.setError("Informe uma data!");
                        data.requestFocus();
                    }
                } else {
                    senha.setError("Informe uma senha!");
                    senha.requestFocus();
                }
            } else {
                if (!emailRegistro.isEmpty()) {
                    email.setError("Informe um email válido!");
                } else {
                    email.setError("Informe um email!");
                }
                email.requestFocus();
            }
        } else {
            nome.setError("Informe um nome!");
            nome.requestFocus();
        }
    }

    public void registrarUsuario(String nome, String email, String senha, String data){
        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    geraUsuario(nome, email, data);
                }
            }
        });
    }

    private void geraUsuario(String nome, String email, String data) {
        Usuario usuario = new Usuario(nome, email, data);
        FirebaseDatabase.getInstance().getReference("Usuario")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistroActivity.this, ContasActivity.class));
                        } else {
                            Log.e("", "onComplete: Failed=" + task.getException().getMessage());
                            Toast.makeText(RegistroActivity.this, "Falha ao registrar usuário...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}