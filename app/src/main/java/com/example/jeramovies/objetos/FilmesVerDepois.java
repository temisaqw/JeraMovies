package com.example.jeramovies.objetos;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmesVerDepois{

    private int idConta;
    private String idFilme; //String para passar pelo json futuramente;
    private String nomeFilme;

    public FilmesVerDepois(){

    }

    public FilmesVerDepois(String idFilme, String nomeFilme, int idConta){
        this.idFilme = idFilme;
        this.nomeFilme = nomeFilme;
        this.idConta = idConta;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(String idFilme) {
        this.idFilme = idFilme;
    }

    public String getNomeFilme() {
        return nomeFilme;
    }

    public void setNomeFilme(String nomeFilme) {
        this.nomeFilme = nomeFilme;
    }

}
