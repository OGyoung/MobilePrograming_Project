package com.example.mob_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("메인 액티비티");
        Button btnSelfIntroduction = (Button) findViewById(R.id.btnSelfIntroduction);
        Button btnSimpleDiary = (Button) findViewById(R.id.btnSimpleDiary);
        Button btnClock = (Button) findViewById(R.id.btnClock);
        Button btnMp3 = (Button) findViewById(R.id.btnMp3);
        Button btnProject = findViewById(R.id.btnProject);

        btnSelfIntroduction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Self_Introduction.class);
                startActivity(intent);
            }
        });

        btnSimpleDiary.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Simple_Diary.class);
                startActivity(intent);
            }
        });

        btnClock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Clock.class);
                startActivity(intent);
            }
        });

        btnMp3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mp3.class);
                startActivity(intent);
            }
        });

        //프로젝트를 보여줄 버튼
        btnProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProjectMain.class);
                startActivity(intent);
            }
        });
    }

}