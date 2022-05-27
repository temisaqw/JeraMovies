package com.example.jeramovies.conexao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeramovies.objetos.Conta;
import com.example.jeramovies.objetos.FilmesVerDepois;

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
        values.put("idConta", filmes.getIdConta());
        values.put("idFilme", filmes.getIdFilme());
        values.put("nomeFilme",filmes.getNomeFilme());
        return banco.insert("filmes", null, values);
    }

    public void excluir (String idFilme){
        banco.delete("filmes","idFilme=?", new String[]{idFilme});
    }

    public List<FilmesVerDepois> carregarFilmesVerDepois(int idConta){
        List<FilmesVerDepois> listaFilmes = new ArrayList<FilmesVerDepois>();
        Cursor cursor = banco.query("filmes", new String[]{"idFilme","nomeFilme"},"idConta=?",new String[]{Integer.toString(idConta)},null,null,null,null);
        while (cursor.moveToNext()){
            FilmesVerDepois filme = new FilmesVerDepois();
            filme.setIdFilme(cursor.getString(0));
            filme.setNomeFilme(cursor.getString(1));
            listaFilmes.add(filme);
        }
        return listaFilmes;
    }
}
