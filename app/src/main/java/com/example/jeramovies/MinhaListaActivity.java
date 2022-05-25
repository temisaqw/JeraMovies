package com.example.jeramovies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;

public class MinhaListaActivity extends Activity {

    private ListView minhaLista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minha_lista_screen);
        minhaLista = findViewById(R.id.lista);

    }
}
