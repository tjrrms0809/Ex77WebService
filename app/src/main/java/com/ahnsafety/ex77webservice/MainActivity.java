package com.ahnsafety.ex77webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ImageView iv;

    //읽어올 텍스트파일의 주소
    String textUrl="http://tjrrms0809.dothome.co.kr/test.txt";//index.html
    String imgURL="http://tjrrms0809.dothome.co.kr/globe.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv= findViewById(R.id.tv);
        iv= findViewById(R.id.iv);

    }

    public void clickBtn(View view) {
        //네트워크작업은 별도의 Thread이 해야함. 퍼미션도 필요함
        new Thread(){
            @Override
            public void run() {

                //무지개로드(inputStream)를 열어주는 해임달(URL)객체 생성
                try {
                    URL url= new URL(textUrl);

                    InputStream is= url.openStream();
                    InputStreamReader isr= new InputStreamReader(is);
                    BufferedReader reader= new BufferedReader(isr);

                    final StringBuffer buffer= new StringBuffer();

                    String line= reader.readLine();
                    while (line!=null){
                        buffer.append(line+"\n");
                        line= reader.readLine();
                    }

                    //읽어온 글씨를 TextView에 보여주기
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(buffer.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void clickBtn2(View view) {

//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    URL url= new URL(imgURL);
//                    InputStream is= url.openStream();
//
//                    final Bitmap bm= BitmapFactory.decodeStream(is);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            iv.setImageBitmap(bm);
//                        }
//                    });
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
        //Glide 라이브러리를 이용하면
        //네트워크 이미지를 쉽게 읽어와 보여줄 수 있음
        Glide.with(this).load(imgURL).into(iv);


    }
}
