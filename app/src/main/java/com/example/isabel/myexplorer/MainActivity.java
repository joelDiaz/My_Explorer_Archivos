package com.example.isabel.myexplorer;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ListActivity {

    private List<String> listaNombresArchivos;
    private List<String> listaRutasArchivos;
    private ArrayAdapter<String> adaptador;
    private String directorioRaiz;
    private TextView carpetaActual;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carpetaActual = (TextView) findViewById(R.id.rutaActual);

        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        verarchivosDirectorio(directorioRaiz);


    }


    public void verarchivosDirectorio(String rutaDirectorio) {
        carpetaActual.setText("Estas en :" + rutaDirectorio);

        listaNombresArchivos = new ArrayList<String>();
        listaRutasArchivos = new ArrayList<String>();
        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles();

        int x = 0;


        if (!rutaDirectorio.equals(directorioRaiz)) {
            listaNombresArchivos.add("../");
            listaRutasArchivos.add(directorioActual.getParent());
            x = 1;
        }

        for (File archivo : listaArchivos) {
            listaRutasArchivos.add(archivo.getPath());
        }

        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);


        for (int i = x; i < listaRutasArchivos.size(); i++) {
            File archivo = new File(listaRutasArchivos.get(i));
            if (archivo.isFile()) {
                listaNombresArchivos.add(archivo.getName());
            } else {
                listaNombresArchivos.add("/" + archivo.getName());
            }
        }
        if (listaArchivos.length < 1) {
            listaNombresArchivos.add("No hay ningun archivo");
            listaRutasArchivos.add(rutaDirectorio);
        }

        adaptador = new ArrayAdapter<String>(this,
                R.layout.text_view_lista_archivos, listaNombresArchivos);
        setListAdapter(adaptador);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {


        File archivo = new File(listaRutasArchivos.get(position));

        if (archivo.isFile()) {
            Toast.makeText(this,
                    "Has seleccionado el archivo: " + archivo.getName(),
                    Toast.LENGTH_LONG).show();
                    String dato= archivo.getAbsolutePath();
                   reproducir(dato);
        } else {
            verarchivosDirectorio(listaRutasArchivos.get(position));
        }

    }

    public  void reproducir(String ruta){
        mp = MediaPlayer.create(MainActivity.this, Uri.parse(ruta));
        mp.start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
