package com.example.isabel.myexplorer;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listilla;
    private ArrayList<String> listaNombresArchivos;
    private ArrayList<String> listaRutasArchivos;
    private ArrayAdapter<String> adaptador;
    private String directorioRaiz;
    private TextView carpetaActual;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listilla = (ListView)findViewById(R.id.list);

        carpetaActual = (TextView) findViewById(R.id.rutaActual);


        /**ESTE METODO REPORNA LA RUTA RAIZ DE LA TARJETA EXTERNA DEL DISPOSITIVI*/
        directorioRaiz = Environment.getExternalStorageDirectory().getPath();
        verarchivosDirectorio(directorioRaiz);


    }


    public void verarchivosDirectorio(String rutaDirectorio) {
        carpetaActual.setText("Estas en :" + rutaDirectorio);

        listaNombresArchivos = new ArrayList<String>();
        listaRutasArchivos = new ArrayList<String>();
        File directorioActual = new File(rutaDirectorio);
        File[] listaArchivos = directorioActual.listFiles();

        /** VARIABLE DE POSICIONAMIENTO*/
        int x = 0;

        /** COMPROBAR SI ESTOY EN EL DIRECTORIO RAIZ O DENTRO DE UNO RESPECTIVAMENTE*/
        if (!rutaDirectorio.equals(directorioRaiz)) {

            /**SI ESTOY DENTRO DE UNA CARPETA X*/
            /**AGREGRO  UN INDICADOR*/


            listaNombresArchivos.add("../");

            /** AGREGO EL DIRECTORIO PADRE DE DONDE VENGO PARA RETORNAR A "EL"*/


            listaRutasArchivos.add(directorioActual.getParent());

            /**INDICO LA POSICION*/
            x = 1;
        }

        /**
         * RECORRO LOS ARCHIVO SI ES QUE EXISTE ALGUNO Y LO AGREGO A LA LISTA DE RUTAS
         *
         */

        for (File archivo : listaArchivos) {
            listaRutasArchivos.add(archivo.getPath());
        }


        /**
         *  ORDENAR ALFABETICAMENTE LA RUTA DE LOS ARCHIVOS, SIN IMPORTAR MAYUSCULA O MINUSCULA
         *
         */
        Collections.sort(listaRutasArchivos, String.CASE_INSENSITIVE_ORDER);

        /**
         *
         * RECORRO EL DIRECTORIO Y ENCUENTRO ARCHIVOS O SUBCARPETAS, LOS AGREGO RESPECTIVAMENTE
         *
         */
        for (int i = x; i < listaRutasArchivos.size(); i++) {
            File archivo = new File(listaRutasArchivos.get(i));
            if (archivo.isFile()) {
                /**SI ES UN ARCHIVO TOMO EL NOMBRE Y SE LO ADD A LA LISTA*/
                listaNombresArchivos.add(archivo.getName());
            } else {
                /** SI NO ES UN ARCHIVO , OBVIAMENTE ES UN DIRECTORIO
                 * , AGREGO UN "/" MAS EL NOMBRE DEL SUBDIRECTORIO */
                listaNombresArchivos.add("/" + archivo.getName());
            }
        }
        /**SI NO EXISTE NINGUN ARCHIVO DENTRO DEL DIRECTORIO SELECCIONADO
         * RETORNO UN COMENTARIO*/
        if (listaArchivos.length < 1) {
            listaNombresArchivos.add("No hay ningun archivo");
            listaRutasArchivos.add(rutaDirectorio);
        }


        /**SETEO EL ADAPTADOR A LA LISTA DE LOS NOMBRES DE LOS ARCHIVOS*/
        adaptador = new ArrayAdapter<String>(this,
                R.layout.text_view_lista_archivos, listaNombresArchivos);
        listilla.setAdapter(adaptador);
//        setListAdapter(adaptador);


        listilla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File archivo = new File(listaRutasArchivos.get(position));

                if (archivo.isFile()) {
                    Toast.makeText(MainActivity.this,"Has seleccionado el archivo: " + archivo.getName(),Toast.LENGTH_LONG).show();


                    String dato = archivo.getAbsolutePath();
                    reproducir(dato);

                    /**SI NO ES UN ARCHIVO ES UN DIRECTORIO, Y SE LLAMA A LA FUNCION PARA CREAR
                     * LA LISTA NUEVA CON LA NUEVA POSICION DE LOS SUBDIRECTORIOS*/
                } else {
                    verarchivosDirectorio(listaRutasArchivos.get(position));
                }


            }
        });

    }



    /**
     * METODO PARA CAPTURAR LOS CLICK DEL USUARIO SEGUN LA POSICION DEL ELEMENTO
     * */
//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//
//        /**TOMO LA POSICION DEL ARCHIVO SELECCIONADO ,
//         *
//         * NOTA:"SI ES QUE ES UN ARCHIVO"*/
//        File archivo = new File(listaRutasArchivos.get(position));
//
//        if (archivo.isFile()) {
//            Toast.makeText(this,
//                    "Has seleccionado el archivo: " + archivo.getName(),
//                    Toast.LENGTH_LONG).show();
//
//
//            String dato = archivo.getAbsolutePath();
//            reproducir(dato);
//
//            /**SI NO ES UN ARCHIVO ES UN DIRECTORIO, Y SE LLAMA A LA FUNCION PARA CREAR
//             * LA LISTA NUEVA CON LA NUEVA POSICION DE LOS SUBDIRECTORIOS*/
//        } else {
//            verarchivosDirectorio(listaRutasArchivos.get(position));
//        }
//
//    }


    /**ESTO ES CARPINTERIA, ESTE METODO ES PARA CUANDO SELECCIONE EL ARCHIVO TIPO MP3
     * PARA QUE LO REPRODUZCA */

    public void reproducir(String ruta) {
        Intent intent = new Intent(MainActivity.this,Reproductor.class);
        Bundle path = new Bundle();
        path.putString("path", ruta);
        intent.putExtras(path);
        startActivity(intent);


    }
    /**DE AQUI PARA ABAJO NO ME INTERESA, OLVIDENLO POR AHORA*/

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
