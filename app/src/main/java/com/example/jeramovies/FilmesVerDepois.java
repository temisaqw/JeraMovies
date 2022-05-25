package com.example.jeramovies;

import android.os.Parcel;
import android.os.Parcelable;

public class FilmesVerDepois{

    private String idFilme;
    private String nomeFilme;

    public FilmesVerDepois(String idFilme, String nomeFilme){
        this.idFilme = idFilme;
        this.nomeFilme = nomeFilme;
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
