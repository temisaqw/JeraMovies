package com.example.jeramovies.conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {

    private static String nome = "conta.db";
    private static int versao = 1;

    public BancoDados(Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table conta(id integer primary key autoincrement, " +
                "nome varchar (50), email varchar (50), dataNascimento varchar(10))");
        db.execSQL("create table filmes(id integer primary key autoincrement, " +
                "idConta integer, idFilme varchar (50), nomeFilme varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
