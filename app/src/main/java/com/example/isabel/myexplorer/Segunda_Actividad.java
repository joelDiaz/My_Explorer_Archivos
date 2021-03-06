package com.example.isabel.myexplorer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Segunda_Actividad extends AppCompatActivity {

    ImageButton foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda__actividad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView info = (TextView) findViewById(R.id.informacion);
        foto = (ImageButton) findViewById(R.id.imUsuario);
        String datea = getIntent().getStringExtra("elusuario");
        String imagen = getIntent().getStringExtra("imagen");

        info.setText(datea + " Logueado");


//        String path = getIntent().getStringExtra("path");


        if (imagen == null) {

            Toast.makeText(Segunda_Actividad.this, "path vacio", Toast.LENGTH_SHORT).show();

        } else foto.setImageURI(Uri.parse(imagen));


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Segunda_Actividad.this, MainActivity.class);
                startActivity(intent);
            }
        });


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
