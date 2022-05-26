package com.example.jeramovies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MinhaListaActivity extends Activity {

    private ListView minhaLista;
    private FilmesDao filmesDao;
    private List<FilmesVerDepois> filmes;
    private ArrayList<String> listaFilmes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minha_lista_screen);
        minhaLista = findViewById(R.id.lista);
        filmesDao = new FilmesDao(this);
        filmes = filmesDao.carregarFilmesVerDepois();
        for(FilmesVerDepois filmesVerDepois : filmes){
            listaFilmes.add(filmesVerDepois.getNomeFilme());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MinhaListaActivity.this,
                android.R.layout.simple_list_item_1,listaFilmes);
        minhaLista.setAdapter(adapter);

        //TODO implementar botão de "Já assisti". Evitar que o filme repita na lista
    }
}
