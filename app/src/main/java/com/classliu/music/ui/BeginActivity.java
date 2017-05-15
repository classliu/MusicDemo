package com.classliu.music.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.classliu.music.R;
import com.classliu.music.widget.CircleImageView;

/**
 * Created by ji_cheng on 2017/5/5.
 */

public class BeginActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView coverView;
    private Button btnScroller;
    private Button btnTomain;
    private Button btnToplayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView();
        setContentView(R.layout.activity_begin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

    }

    private void initView() {
        btnScroller = (Button) findViewById(R.id.btn_scroller);
        btnScroller.setOnClickListener(this);
        btnTomain = (Button) findViewById(R.id.btn_tomain);
        btnTomain.setOnClickListener(this);
        btnToplayer = (Button) findViewById(R.id.btn_toplayer);
        btnToplayer.setOnClickListener(this);
        coverView = (CircleImageView) findViewById(R.id.imageview);

    }

    private void setView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scroller:
                ScrollingActivity.taransfromInNavgetion(BeginActivity.this, coverView, null);
                break;
            case R.id.btn_tomain:
                MusicMainActivity.taransfromInNavgetion(BeginActivity.this, coverView, null);
                break;
            case R.id.btn_toplayer:
                startActivity(new Intent(BeginActivity.this, MusicPlayerActivity.class));
                break;
        }
    }
}
