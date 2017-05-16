package com.classliu.music.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.classliu.music.R;
import com.classliu.music.service.MusicService;
import com.classliu.music.widget.CircleImageView;

import java.text.SimpleDateFormat;


/**
 * 音乐播放器首页
 */
public class MusicMainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The bottom sheet is dragging.
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * The bottom sheet is settling.
     */
    public static final int STATE_SETTLING = 2;

    /**
     * The bottom sheet is expanded.
     */
    public static final int STATE_EXPANDED = 3;

    /**
     * The bottom sheet is collapsed.
     */
    public static final int STATE_COLLAPSED = 4;

    /**
     * The bottom sheet is hidden.
     */
    public static final int STATE_HIDDEN = 5;

    private MusicService musicService;

    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    private Context mContext;
    private final static String EXTRA_IMAGE = "MusicMainActivity:images";

    public static void taransfromInNavgetion(AppCompatActivity activity, View transfromImage, Object o) {
        Intent intent = new Intent(activity, MusicMainActivity.class);

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transfromImage, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
    }


    public class Mp3ProgressHandler extends Handler {
        public void start() {
            sendEmptyMessage(0);
        }

        public void stop() {
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateMp3Progress();
            sendEmptyMessageDelayed(0, 500);
        }
    }

    public Mp3ProgressHandler handler = new Mp3ProgressHandler();
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            musicService = ((MusicService.MyBinder) iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            musicService = null;
        }
    };

    private void bindServiceConnection() {
        Intent intent = new Intent(mContext, MusicService.class);
        startService(intent);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }


    private ImageView ivCloseMusic;
    private CircleImageView ivBookMusicCover;
    private TextView tvBookName;

    private TextView tvMusicPlayingTime;
    private SeekBar musicSeekBar;
    private TextView tvMusicAllTime;

    private ImageView ivDeleteMusic;
    private ImageView ivMusicPrevious;
    private ImageView ivPlayStop;
    private ImageView ivNextMusic;

    private ImageView ivPlaymode;
    private TextView tvPlaymode;
    private FrameLayout flMusicPlaymode;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setView();

        init(savedInstanceState);
        initView();
        initData();
        setListener();
        addFragment();

        musicListBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void addFragment() {
        MusicListFragment fragment = (MusicListFragment) getSupportFragmentManager().findFragmentByTag(MusicListFragment.TAG);
        if (fragment == null) {
            fragment = MusicListFragment.getInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, fragment, MusicListFragment.TAG).commit();
        }
    }

    private FrameLayout flContent;
    private BottomSheetBehavior<FrameLayout> musicListBehavior;

    public void initView() {
        setView();
        setContentView(R.layout.activity_main_music);

        ViewCompat.setTransitionName(findViewById(R.id.iv_music_cover), EXTRA_IMAGE);

        flContent = (FrameLayout) findViewById(R.id.fl_content);
        musicListBehavior = BottomSheetBehavior.from(flContent);


        ivCloseMusic = (ImageView) findViewById(R.id.iv_close_music);
        ivBookMusicCover = (CircleImageView) findViewById(R.id.iv_music_cover);
        tvBookName = (TextView) findViewById(R.id.tv_music_name);
        tvMusicPlayingTime = (TextView) findViewById(R.id.tv_music_playing_time);
        musicSeekBar = (SeekBar) findViewById(R.id.music_seek_bar);
        tvMusicAllTime = (TextView) findViewById(R.id.tv_music_all_time);
        ivDeleteMusic = (ImageView) findViewById(R.id.iv_delete_music);
        ivMusicPrevious = (ImageView) findViewById(R.id.iv_music_previous);
        ivPlayStop = (ImageView) findViewById(R.id.iv_play_stop);
        ivNextMusic = (ImageView) findViewById(R.id.iv_next_music);
        ivPlaymode = (ImageView) findViewById(R.id.iv_playmode);
        tvPlaymode = (TextView) findViewById(R.id.tv_playmode);
        flMusicPlaymode = (FrameLayout) findViewById(R.id.fl_music_playmode);

    }


    public void initData() {
        musicService = new MusicService(mContext, new MusicService.OnCacheListener() {
            @Override
            public void getCacheProgress(int progress) {
                musicSeekBar.setSecondaryProgress(progress * musicService.mp.getDuration() / 100);

            }
        });
        bindServiceConnection();

    }

    public void setListener() {
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicService.mp.seekTo(seekBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ivCloseMusic.setOnClickListener(this);
        ivMusicPrevious.setOnClickListener(this);
        ivPlayStop.setOnClickListener(this);
        ivNextMusic.setOnClickListener(this);
        flMusicPlaymode.setOnClickListener(this);
        ivDeleteMusic.setOnClickListener(this);

    }

    private void updateMp3Progress() {
        tvMusicPlayingTime.setText(time.format(musicService.mp.getCurrentPosition()));
        tvMusicAllTime.setText(time.format(musicService.mp.getDuration()));
        int currentPosition = musicService.mp.getCurrentPosition();
        int duration = musicService.mp.getDuration();

        musicSeekBar.setMax(musicService.mp.getDuration());
        musicSeekBar.setProgress(musicService.mp.getCurrentPosition());
    }


    protected void init(Bundle savedInstanceState) {
        this.mContext = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (musicService.mp.isPlaying()) {
            musicService.playOrPause();
        }
    }

    @Override
    protected void onResume() {

        musicSeekBar.setProgress(musicService.mp.getCurrentPosition());
        musicSeekBar.setMax(musicService.mp.getDuration());
        handler.start();
        super.onResume();
        Log.d("hint", "handler post runnable");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play_stop:
                musicService.playOrPause();
                if (musicService.mp.isPlaying()) {
                    ivPlayStop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.music_stop_selector));
                    handler.start();
                } else {
                    ivPlayStop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.music_playing_selector));
                    handler.stop();
                }
                break;

            case R.id.iv_close_music:

                handler.stop();

                onBackPressed();


                break;
            case R.id.iv_music_previous:
                musicService.preMusic();
                break;
            case R.id.iv_next_music:
                musicService.nextMusic();
                break;
            case R.id.iv_delete_music:
                toggleBottomSheet();
                break;
            case R.id.fl_music_playmode:
                changeMusicMode();
                break;
        }
    }

    @Override
    public void onDestroy() {
        unbindService(sc);
        super.onDestroy();
    }


    private void setView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //改变播放模式
    private void changeMusicMode() {
        if (tvPlaymode.getVisibility() == View.VISIBLE) {
            tvPlaymode.setVisibility(View.INVISIBLE);
        } else {
            tvPlaymode.setVisibility(View.VISIBLE);
        }
    }


    private void toggleBottomSheet() {
        if (musicListBehavior == null) {
            return;
        }
        if (musicListBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            musicListBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            musicListBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}