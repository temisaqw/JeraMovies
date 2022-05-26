package com.example.jeramovies.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.jeramovies.R;
import com.example.jeramovies.conexao.FilmesDao;
import com.example.jeramovies.objetos.Conta;
import com.example.jeramovies.objetos.FilmesVerDepois;

import java.util.ArrayList;
import java.util.List;

public class MinhaListaActivity extends Activity {

    private ListView minhaLista;
    private FilmesDao filmesDao;
    private List<FilmesVerDepois> filmes;
    private ArrayList<String> listaFilmes = new ArrayList<>();
    private Conta contaAtual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minha_lista_screen);
        minhaLista = findViewById(R.id.lista);

        carregarConta();
        gerarLista();
    }

    private void gerarLista() {
        filmesDao = new FilmesDao(this);
        filmes = filmesDao.carregarFilmesVerDepois(contaAtual.getId());
        for(FilmesVerDepois filmesVerDepois : filmes){
            listaFilmes.add(filmesVerDepois.getNomeFilme());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MinhaListaActivity.this,
                android.R.layout.simple_list_item_1,listaFilmes);
        minhaLista.setAdapter(adapter);
    }

    private void carregarConta() {
        Intent i = getIntent();
        contaAtual = i.getParcelableExtra("contaSelecionada");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_lista, menu);
    }

    public void marcarVisto(MenuItem item){
        Toast.makeText(this, "Não implementado", Toast.LENGTH_SHORT).show();
        //TODO quando marcar visto, jogar nas recomendações
    }

    public void remover(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        FilmesVerDepois filme = filmes.get(menuInfo.position);
        String idFilme = filme.getIdFilme();

        filmesDao.excluir(idFilme);
    }
}
