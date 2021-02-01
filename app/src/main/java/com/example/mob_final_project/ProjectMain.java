package com.example.mob_final_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;


public class ProjectMain extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_image);
        setTitle("앨범");

        Button btnReturn=findViewById(R.id.btnReturn);

        final ImageView image[] = new ImageView[9]; // 변수들을 일일이 나열하지 않고 배열로 사용

        //for문으로 사용하기 위해서 필요함
        Integer image_Id[] = { R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5,
                R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9 };

        //비트맵으로 이미지를 보낼 때, xml에서 입력된 id는 안보내짐
        //그래서 drawable에 있는 이미지들을 써서 보냄
        final Integer image_drawble[] = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4,
                R.drawable.pic5,R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic9};

        //위젯 대입
        for(int i=0; i<image.length; i++){
            image[i]=findViewById(image_Id[i]);
        }

        // 이미지를 클릭했을 때 해당 이미지를 새로운 액티비티에 띄우는 이벤트
        for(int j=0; j<image.length; j++){
            final int finalJ = j; // final을 사용해야 배열에 입력할 수 있었음(빨간색 오류뜬거 자동완성으로 해결)
            image[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //이미지를 새로운 액티비티로 보냄
                    Intent intent = new Intent(getApplicationContext(), Project_ImageEdit.class);
                    Bitmap sendImage = BitmapFactory.decodeResource(getResources(), image_drawble[finalJ]);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    sendImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image", byteArray);
                    startActivity(intent);
                }
            });
        }
        //돌아가기
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

