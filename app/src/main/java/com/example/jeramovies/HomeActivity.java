package com.example.jeramovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView nomeNovaConta;
    private Button logoutButton, addButton;
    private AccessToken acessToken;
    private ListView listaContas;
    private ContaDao contaDao;
    private List<Conta> contas;
    private ArrayList<String> itens = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_screen);

        mAuth = FirebaseAuth.getInstance();
        contaDao = new ContaDao(this);

        logoutButton = findViewById(R.id.logoutButton);
        addButton = findViewById(R.id.addButton);
        listaContas = findViewById(R.id.listaDeContas);
        nomeNovaConta = findViewById(R.id.nomeNovaConta);

        gerarContas();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogar();
            }
        });

        listaContas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                acessarConta(position);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itens.size()<4) {
                    criarConta();
                } else {
                    Toast.makeText(HomeActivity.this, "Máximo de quatro contas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarConta() {
        Conta conta = new Conta(nomeNovaConta.getText().toString().trim());
        contas.add(conta);
        itens.add("Conta de: " + conta.getNome());
        contaDao.inserir(conta);
        adapter.notifyDataSetChanged();
    }

    private void acessarConta(int position) {
        Conta contaAtual = contas.get(position);
        Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
        intent.putExtra("contaSelecionada", (Parcelable) contaAtual);
        startActivity(intent);
    }

    private void gerarContas() {
        contas = contaDao.carregarContas();
        for(Conta conta : contas){
            itens.add("Conta de: " + conta.getNome());
        }
        adapter = new ArrayAdapter<String>(HomeActivity.this,
                android.R.layout.simple_list_item_1,itens);
        listaContas.setAdapter(adapter);
    }

    @Override

    public void onStart(){

        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        acessToken = AccessToken.getCurrentAccessToken();

        if(currentUser==null && acessToken==null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));

        } }

    private void deslogar(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }
}
