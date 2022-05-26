package com.example.jeramovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jeramovies.R;
import com.example.jeramovies.conexao.ContaDao;
import com.example.jeramovies.loginActivity.LoginActivity;
import com.example.jeramovies.objetos.Conta;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ContasActivity extends AppCompatActivity {

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

        setContentView(R.layout.contas_screen);

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
                    Toast.makeText(ContasActivity.this, "Máximo de quatro contas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarConta() {
        Conta conta = new Conta(nomeNovaConta.getText().toString().trim());
        contaDao.inserir(conta);
        conta = contaDao.maisRecente();
        contas.add(conta);
        itens.add("Conta de: " + conta.getNome());
        adapter.notifyDataSetChanged();
    }

    private void acessarConta(int position) {
        Conta contaAtual = contas.get(position);
        Intent intent = new Intent(ContasActivity.this, MenuActivity.class);
        intent.putExtra("contaSelecionada", (Parcelable) contaAtual);
        startActivity(intent);
    }

    private void gerarContas() {
        contas = contaDao.carregarContas();
        for(Conta conta : contas){
            itens.add("Conta de: " + conta.getNome());
        }
        adapter = new ArrayAdapter<String>(ContasActivity.this,
                android.R.layout.simple_list_item_1,itens);
        listaContas.setAdapter(adapter);
    }

    @Override

    public void onStart(){

        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        acessToken = AccessToken.getCurrentAccessToken();

        if(currentUser==null && acessToken==null) {
            startActivity(new Intent(ContasActivity.this, LoginActivity.class));

        } }

    private void deslogar(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        startActivity(new Intent(ContasActivity.this, LoginActivity.class));
    }
}
