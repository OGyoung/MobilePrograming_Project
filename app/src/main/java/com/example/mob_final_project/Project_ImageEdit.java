package com.example.mob_final_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;


public class Project_ImageEdit extends Activity {

    MyGraphicView graphicView;
    //이미지 버튼 변수
    ImageButton idScale, idBright, idRight_Rotate, idLeft_Rotate;

    //시크바 변수
    SeekBar idScale_SeekBar, idBright_SeekBar, idRightRotate_Seekbar, idLeftRotate_Seekbar;

    //시크바 이동시 수치를 나타내기 위한 변수
    final int[] scale = {0}, bright = {0}, rrotae = {0}, lrotae = {0};

    //시크바 이동시 이미지의 변화를 위해 이전 값을 저장할 변수
    int pred_scale = 0, pred_bright=0;

    static float scaleX = 1, scaleY = 1; //크기에 사용할 변수
    static float satur=1; //밝기에 사용할 변수
    static boolean drawState =false; //이미지 그리기

            //그림판에 쓸 변수
    static int DEFAULT=0, RED=1, GREEN=2, BLUE=3; // 색깔 변경에 쓸 변수
    static int ColorOption =DEFAULT;
    static boolean DRAW_STOP=false;
    Button btnDraw;

    //터치 처리
    int StartX, StartY=-1;
    int StopX, StopY=-1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_image_edit);
        setTitle("이미지 편집");

        LinearLayout pictureLayout = findViewById(R.id.pictureLayout); //레이아웃에 이미지를 넣을 것이기 때문에 선언
        graphicView = new MyGraphicView(this);

        Button btnReturn = findViewById(R.id.btnReturn); //이전 화면으로 돌아가기
        btnDraw=findViewById(R.id.btnDraw); //그림판 그리기

        pictureLayout.addView(graphicView);//화면에 이미지 띄우기

        registerForContextMenu(btnDraw);

        //돌아가기 이벤트
        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DRAW_STOP=true;
            }
        });


        btnEvent();//버튼 이벤트
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getMenuInflater();
        if( v== btnDraw){
            menu.setHeaderTitle("색상변경");
            mInflater.inflate(R.menu.menu,menu);
        }
    }

    //처음에는 옵션메뉴로해서 하려했는데 옵션메뉴를 뜨게하는 점 세개가 보이질 않았습니다.(에러는 없었는데)
    //그래서 컨텍스트 메뉴로 바꿔서 진행하였습니다.
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
           case R.id.colorRed:
                ColorOption= RED;
                return true;
            case R.id.colorGreen:
                ColorOption = GREEN;
                return true;
            case R.id.colorBlue:
                ColorOption = BLUE;
                return true;
            case R.id.DrawingStop:
                DRAW_STOP=false;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        StopX = (int) event.getX();
        StopY = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_UP:
                StartX = -1;
                StartY = -1;
                break;
            case MotionEvent.ACTION_DOWN:
                StartX = StopX;
                StartY = StopY;
            case MotionEvent.ACTION_MOVE:
                StartX = StopX;
                StartY = StopY;
                break;
        }

/*
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                StartX = (int) event.getX();
                StartY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                StopX = (int) event.getX();
                StopY = (int) event.getY();
                this.invalidateOptionsMenu();
                break;
        }
        */

        return true;
    }


    private void btnEvent() {

        final TextView idText_Seek_Info=findViewById(R.id.idText_Seek_Info);
        //위젯변수 대입
        idScale_SeekBar=findViewById(R.id.idScale_SeekBar);
        idBright_SeekBar=findViewById(R.id.idBright_SeekBar);
        idRightRotate_Seekbar=findViewById(R.id.idRightRotate_SeekBar);
        idLeftRotate_Seekbar=findViewById(R.id.idLeftRotate_SeekBar);


        idScale = (ImageButton) findViewById(R.id.idScale);
        idScale.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scale 빼고 다 안보이게 함
                idBright_SeekBar.setVisibility(View.INVISIBLE);
                idRightRotate_Seekbar.setVisibility(View.INVISIBLE);
                idLeftRotate_Seekbar.setVisibility(View.INVISIBLE);

                idText_Seek_Info.setText(String.valueOf(scale[0])); // textView에 보이게 하기.

                idScale_SeekBar.setVisibility(View.VISIBLE);
                idScale_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        idText_Seek_Info.setText(String.valueOf(progress-100));
                        scale[0] = progress-100;

                        //처음에 단순히 0보다 클 때 이미지 늘리고 작을때는 이미지를 작게하였는데 오류가 계속났음
                        //시크바에서 나타내는 값이 이전에 나타내던 값보다 크면 이미지를 키우고 작으면 이미지를 감소시켜서 해결함
                        if (scale[0] >= pred_scale) {
                            scaleX = scaleX + 0.05f;
                            scaleY = scaleX + 0.05f;
                        } else if (scale[0] < pred_scale) {
                            scaleX = scaleX - 0.05f;
                            scaleY = scaleX - 0.05f;
                        }
                        graphicView.invalidate();
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });

        idBright = (ImageButton) findViewById(R.id.idBright);
        idBright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //bright 빼고 다 안보이게 함
                idScale_SeekBar.setVisibility(View.INVISIBLE);
                idRightRotate_Seekbar.setVisibility(View.INVISIBLE);
                idLeftRotate_Seekbar.setVisibility(View.INVISIBLE);

                idText_Seek_Info.setText(String.valueOf(bright[0]));

                idBright_SeekBar.setVisibility(View.VISIBLE);
                idBright_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        idText_Seek_Info.setText(String.valueOf(progress-100));
                        bright[0] = progress-100;

                        //scale에서 한 방식과 동일
                        if (bright[0] >= pred_bright) {
                            satur = satur+0.1f;
                        } else if (bright[0] < pred_bright) {
                            satur = satur-0.1f;
                        }
                        graphicView.invalidate();
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });

        idRight_Rotate=findViewById(R.id.idRight_Rotate);
        idRight_Rotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idScale_SeekBar.setVisibility(View.INVISIBLE);
                idBright_SeekBar.setVisibility(View.INVISIBLE);
                idLeftRotate_Seekbar.setVisibility(View.INVISIBLE);
                idText_Seek_Info.setText(String.valueOf(rrotae[0]));

                idRightRotate_Seekbar.setVisibility(View.VISIBLE);
                idRightRotate_Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        idText_Seek_Info.setText(String.valueOf(progress));
                        rrotae[0] =progress;
                        graphicView.invalidate();
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

            }
        });

        idLeft_Rotate=findViewById(R.id.idLeft_Rotate);
        idLeft_Rotate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                idScale_SeekBar.setVisibility(View.INVISIBLE);
                idRightRotate_Seekbar.setVisibility(View.INVISIBLE);
                idBright_SeekBar.setVisibility(View.INVISIBLE);
                idText_Seek_Info.setText(String.valueOf(lrotae[0]));

                idLeftRotate_Seekbar.setVisibility(View.VISIBLE);
                idLeftRotate_Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        idText_Seek_Info.setText(String.valueOf(progress));
                        lrotae[0] =progress;
                        graphicView.invalidate();
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
            }
        });
    }

    private class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(final Canvas canvas) {
            super.onDraw(canvas);

            final Paint paint = new Paint();
            //크기조절
            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;
            canvas.scale(scaleX, scaleY, cenX, cenY);

            //밝기조절
            float[] array = {satur, 0, 0, 0, 0, 0, satur, 0, 0, 0, 0, 0, satur, 0, 0, 0, 0, 0, 1, 0 };
            ColorMatrix cm = new ColorMatrix(array);
            cm.setSaturation(satur);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            //오른쪽회전조절
            canvas.rotate(rrotae[0], cenX, cenY);

            //왼쪽회전조절
            canvas.rotate(lrotae[0]*-1.0f, cenX, cenY);

            //그림판 그리기, true이면 그리게함. false 처리는 컨텍스트 메뉴에서 설정.
            if(DRAW_STOP==true){
                paint.setAntiAlias(true);
                paint.setStrokeWidth(5);
                paint.setStyle(Paint.Style.STROKE);
                switch (ColorOption){
                    case 0:
                        paint.setColor(Color.BLACK);
                        break;
                    case 1:
                        paint.setColor(Color.RED);
                        break;
                    case 2:
                        paint.setColor(Color.GREEN);
                        break;
                    case 3:
                        paint.setColor(Color.BLUE);
                        break;
                }
                canvas.drawLine(StartX, StartY*-1, StopX, StopY, paint);
            }

            byte[] arr = getIntent().getByteArrayExtra("image");
            Bitmap image = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            image = image.createScaledBitmap(image, image.getWidth() + 200, image.getHeight() * 2, true);//초기 이미지가 작게 나와서 기본으로 좀 커보이게 수정

            //화면 중앙에 이미지 보이게 설정
            int picX = (this.getWidth() - image.getWidth()) / 2;
            int picY = (this.getHeight() - image.getHeight()) / 2;

            canvas.drawBitmap(image, picX, picY, paint);//이미지 그리기

            image.recycle();

            //이전값 저장해서 감소하는 것을 인식시킴
            pred_scale = scale[0];
            pred_bright=bright[0];

        }
    }
}
