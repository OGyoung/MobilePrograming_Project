package com.example.mob_final_project;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Mp3 extends Activity {

    Button btnStart, btnStop;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mp3);


        final MediaPlayer mPlayer;
        mPlayer = MediaPlayer.create(this,R.raw.epic);

        final Switch switch1 = (Switch) findViewById(R.id.switch1);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch1.isChecked() == true)
                    mPlayer.start();
                else
                    mPlayer.pause();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.start();
                Toast.makeText(Mp3.this,"Media Play Button",Toast.LENGTH_LONG).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayer.pause();
                Toast.makeText(Mp3.this,"Media Pause Button",Toast.LENGTH_LONG).show();
            }
        });
    }
}