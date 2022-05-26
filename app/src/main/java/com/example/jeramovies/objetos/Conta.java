package com.example.jeramovies.objetos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Conta implements Parcelable {
    private int id;
    private String nome;

    public Conta(String nome){
        this.nome = nome;
    }

    public Conta() {

    }

    protected Conta(Parcel in) {
        id = in.readInt();
        nome = in.readString();
    }

    public static final Creator<Conta> CREATOR = new Creator<Conta>() {
        @Override
        public Conta createFromParcel(Parcel in) {
            return new Conta(in);
        }

        @Override
        public Conta[] newArray(int size) {
            return new Conta[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
    }
}
