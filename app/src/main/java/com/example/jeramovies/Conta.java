package com.example.jeramovies;

import androidx.annotation.NonNull;

public class Conta {
    private int id;
    private String nome;

    public Conta(String nome){
        this.nome = nome;
    }

    public Conta() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString(){
        return nome;
    }
}
