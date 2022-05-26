package com.example.jeramovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FilmesDao {

    private BancoDados bd;
    private SQLiteDatabase banco;

    public FilmesDao(Context context){
        bd = new BancoDados(context);
        banco = bd.getWritableDatabase();
    }

    public long adicionarFilmeVerDepois(FilmesVerDepois filmes){
        ContentValues values = new ContentValues();
        values.put("idFilme", filmes.getIdFilme());
        values.put("nomeFilme",filmes.getNomeFilme());
        return banco.insert("filmes", null, values);
    }

    public List<FilmesVerDepois> carregarFilmesVerDepois(){
        List<FilmesVerDepois> listaFilmes = new ArrayList<FilmesVerDepois>();
        Cursor cursor = banco.query("filmes", new String[]{"idFilme","nomeFilme"},null,null,null,null,null,null);
        while (cursor.moveToNext()){
            FilmesVerDepois filme = new FilmesVerDepois();
            filme.setIdFilme(cursor.getString(0));
            filme.setNomeFilme(cursor.getString(1));
            listaFilmes.add(filme);
        }
        return listaFilmes;
    }
}
