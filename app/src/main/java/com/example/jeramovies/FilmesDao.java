package com.example.jeramovies;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}
