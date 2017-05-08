package com.classliu.music.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.classliu.music.MusicApplication;
import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;

import java.io.File;

/**
 */
public class MusicService extends Service implements CacheListener {

    private String[] musicDir = new String[]{Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/Come Edit.mp3",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/Legend Of The Martial Artist.mp3",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/依兰爱情故事.mp3",
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/music_1.mp3"};

    private String url = "http://hzjaikan.b0.upaiyun.com/tmp/music_1.mp3";


    private String[] musicArr = {"http://hzjaikan.b0.upaiyun.com/tmp/music_1.mp3", "http://hzjaikan.b0.upaiyun.com/tmp/music_2.mp3",
            "http://hzjaikan.b0.upaiyun.com/tmp/music_3.mp3"};

    private int musicIndex = 1;

    private Context mContext;
    private OnCacheListener onCacheListener;

    public final IBinder binder = new MyBinder();

    public interface OnCacheListener {
        void getCacheProgress(int progress);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        if (onCacheListener != null) {
            onCacheListener.getCacheProgress(percentsAvailable);
        }
    }

    public class MyBinder extends Binder {
        public   MusicService getService() {
            return MusicService.this;
        }
    }

    public static MediaPlayer mp = new MediaPlayer();

    public MusicService() {

    }

    public MusicService(Context context, OnCacheListener onCacheListener) {
        mContext = context;
        this.onCacheListener = onCacheListener;
        //loadMusicFormNet(1);
        loadLastMusic();
    }


    private void loadMusicFormNet(int index) {
        try {

            String url = musicArr[index - 1];
            Log.e("------------" + getClass().getSimpleName(), url);
            checkCachedState(url);

            HttpProxyCacheServer proxy = MusicApplication.getProxy(mContext);
            proxy.registerCacheListener(this, url);
            String proxyUrl = proxy.getProxyUrl(url);

            mp.setDataSource(proxyUrl);
            //mp.setDataSource(Environment.getDataDirectory().getAbsolutePath()+"/You.mp3");
            mp.prepareAsync();
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            musicIndex = index;
        } catch (Exception e) {
            Log.d("hint", "can't get to the song");
            e.printStackTrace();
        }
    }

    /**
     * 检查缓存的状态
     */
    private void checkCachedState(String url) {
        HttpProxyCacheServer proxy = MusicApplication.getProxy(mContext);
        boolean fullyCached = proxy.isCached(url);
        if (fullyCached && onCacheListener != null) {
            onCacheListener.getCacheProgress(100);
        }
    }

    public void loadLastMusic() {
        try {
            mp.setDataSource(musicDir[musicIndex - 1]);
            mp.prepare();
            mp.seekTo(0);
            mp.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void 上一首() {
        resetMediaPlayer();
        loadMusicFormNet(--musicIndex);
    }


    public void 下一首() {
        resetMediaPlayer();
        loadMusicFormNet(++musicIndex);
    }

    private void resetMediaPlayer() {
        if (mp != null) {
            mp.stop();
            try {
                mp.reset();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void playOrPause() {
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }
    }

    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
//                mp.prepareAsync();
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void nextMusic() {
        if (mp != null && musicIndex < 3) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(musicDir[musicIndex + 1]);
                musicIndex++;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump next music");
                e.printStackTrace();
            }
        }
    }

    public void preMusic() {
        if (mp != null && musicIndex > 0) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource(musicDir[musicIndex - 1]);
                musicIndex--;
                mp.prepare();
                mp.seekTo(0);
                mp.start();
            } catch (Exception e) {
                Log.d("hint", "can't jump pre music");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        MusicApplication.getProxy(getApplication()).unregisterCacheListener(this);
        super.onDestroy();
    }

    /**
     * onBind 是 Service 的虚方法，因此我们不得不实现它。
     * 返回 null，表示客服端不能建立到此服务的连接。
     */
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
