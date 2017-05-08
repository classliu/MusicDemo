package com.classliu.music;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 */
public class MusicApplication extends Application {

    private HttpProxyCacheServer proxy;
    private static MusicApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static HttpProxyCacheServer getProxy(Context context) {
        MusicApplication musicApplication = MusicApplication.context;
        return musicApplication.proxy == null ? (musicApplication.proxy = musicApplication.newProxy()) : musicApplication.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }
}
