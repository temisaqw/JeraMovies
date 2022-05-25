package com.example.jeramovies;

public class Usuario {
    private String nome;
    private String email;
    private String data_nascimento;

    public Usuario(String nome, String email, String data_nascimento){
        this.nome = nome;
        this.email = email;
        this.data_nascimento = data_nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(String data_nascimento) {
        this.data_nascimento = data_nascimento;
    }
}
