package com.example.mob_final_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class Self_Introduction extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_introduction);
        setTitle("자기 소개서");
        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        Button btnReturn2 = (Button) findViewById(R.id.btnReturn2);
        final EditText intro = (EditText) findViewById(R.id.intro);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intro.setText("한림대학교 콘텐츠IT학과 16학번 오기용입니다.\n" +
                        "취미는 농구와 게임입니다.");
            }
        });
    }
}