package com.classliu.music.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.classliu.music.R;
import com.classliu.music.adapter.MyRecylerAdapter;
import com.classliu.music.bean.Modle;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    private final static String EXTRA_IMAGE = "ScrollingActivity:images";

    public static void taransfromInNavgetion(AppCompatActivity activity, View transfromImage, Object o) {
        Intent intent = new Intent(activity, ScrollingActivity.class);
//        intent.putExtra(EXTRA_IMAGE,EXTRA_IMAGE);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transfromImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }



    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private RecyclerView recyclerView;
    private State mCurrentState = State.IDLE;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        setContentView(R.layout.activity_scrolling);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar), EXTRA_IMAGE);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (mCurrentState != State.EXPANDED) {
                        Log.e("--appBarLayout----", "State.EXPANDED====" + verticalOffset);
                    }
                    mCurrentState = State.EXPANDED;
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (mCurrentState != State.COLLAPSED) {
                        Log.e("--appBarLayout----", "State.COLLAPSED====" + verticalOffset);
                    }
                    mCurrentState = State.COLLAPSED;
                } else {
                    if (mCurrentState != State.IDLE) {
                        Log.e("--appBarLayout----", "State.IDLE====" + verticalOffset);
                    }
                    mCurrentState = State.IDLE;
                }
            }
        });


        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*final ImageView image = (ImageView) findViewById(R.id.iv_star);
        Picasso.with(this).load(getIntent().getStringExtra(EXTRA_IMAGE)).into(image, new Callback() {
            @Override public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override public void onError() {

            }
        });*/
        /*recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);*/

        /*List<Modle> tempList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Modle modle = new Modle();
            modle.text = "this is numer" + (i + 1) + "song";
            tempList.add(modle);
        }

        adapter = new MyRecylerAdapter(this, tempList);*/
        //recyclerView.setAdapter(adapter);
       // handler.sendEmptyMessageDelayed(0, 500);
    }

    MyRecylerAdapter adapter;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            recyclerView.setAdapter(adapter);
        }
    };


    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }


    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void setView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
