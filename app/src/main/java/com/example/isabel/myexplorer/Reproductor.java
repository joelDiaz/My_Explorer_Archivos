package com.example.isabel.myexplorer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toolbar;


public class Reproductor extends AppCompatActivity {
    ImageButton star, pause, stop;
    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproductor);


        String path = getIntent().getStringExtra("path");

        mp = MediaPlayer.create(Reproductor.this, Uri.parse(path));

        mp.isLooping();


        star = (ImageButton) findViewById(R.id.Start);
        pause = (ImageButton) findViewById(R.id.Pause);
        stop = (ImageButton) findViewById(R.id.Stop);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
                mp.prepareAsync();

            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        mp.stop();
    }
}
