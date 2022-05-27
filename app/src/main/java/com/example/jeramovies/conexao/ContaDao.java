package com.example.jeramovies.conexao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.jeramovies.objetos.Conta;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContaDao {

    private BancoDados bd;
    private SQLiteDatabase banco;

    public ContaDao(Context context){
        bd = new BancoDados(context);
        banco = bd.getWritableDatabase();
    }

    public long inserir(Conta conta){
        ContentValues values = new ContentValues();
        values.put("nome", conta.getNome());
        return banco.insert("conta", null, values);
    }

    public void excluir (Conta conta){
        banco.delete("conta","id=?", new String[]{String.valueOf(conta.getId())});
    }

    public Conta maisRecente(){
        Conta contaMaisRecente = new Conta();
        Cursor cursor = banco.query("conta", new String[]{"id","nome"},null,null,null,null,null,null);
        while (cursor.moveToNext()){
            contaMaisRecente.setId(cursor.getInt(0));
            contaMaisRecente.setNome(cursor.getString(1));
        }
        return contaMaisRecente;
    }

    public List<Conta> carregarContas(){
        List<Conta> contas = new ArrayList<Conta>();
        Cursor cursor = banco.query("conta", new String[]{"id","nome"},null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Conta conta = new Conta();
            conta.setId(cursor.getInt(0));
            conta.setNome(cursor.getString(1));
            contas.add(conta);
        }
        return contas;
    }


}
