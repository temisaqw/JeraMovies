package com.example.jeramovies;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends Activity {

    private TextView boasVindas;
    private Button listaButton, buscarButton;
    private ListView listaSugestoes;
    private List<String> nomeFilmes = new ArrayList<>();
    private List<String> idFilmes = new ArrayList<String>();
    private FilmesDao filmesDao;

    private static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=8649b2d9c70b73597a22eb5041abc7b2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen);
        boasVindas = findViewById(R.id.boasVindas);
        listaButton = findViewById(R.id.listaButton);
        buscarButton = findViewById(R.id.buscaButton);
        listaSugestoes = findViewById(R.id.listaRecomendados);

        GetData getData = new GetData();
        getData.execute();

        Intent i = getIntent();
        Conta contaAtual = (Conta) i.getParcelableExtra("contaSelecionada");
        boasVindas.setText("Bem vindo " + contaAtual.getNome());

        registerForContextMenu(listaSugestoes);

    }

    public class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try{
                URL url;
                HttpURLConnection urlConnection = null;

                try{
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while(data != -1){
                        current += (char)data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String movieName = jsonObject1.getString("title");
                    String movieId = jsonObject1.getString("id");//TODO verificar qual o cod no json
                    nomeFilmes.add(movieName);
                    idFilmes.add(movieId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter adapter = new ArrayAdapter(MenuActivity.this,
                    android.R.layout.simple_list_item_1, nomeFilmes);
            listaSugestoes.setAdapter(adapter);
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_geral, menu);
    }

    public void adicionarVerDepois(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        String nome = nomeFilmes.get(menuInfo.position);
        String id = idFilmes.get(menuInfo.position);

        final FilmesVerDepois filmesVerDepois = new FilmesVerDepois(id, nome);
        filmesDao.adicionarFilmeVerDepois(filmesVerDepois);
    }
}
