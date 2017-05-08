package com.classliu.music.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.classliu.music.R;

/**
 * Created by ji_cheng on 2017/5/5.
 */

public class BeginActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_begin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BeginActivity.this, ScrollingActivity.class));
                MusicMainActivity.taransfromInNavgetion(BeginActivity.this,imageView,null);
            }
        });
    }





}
