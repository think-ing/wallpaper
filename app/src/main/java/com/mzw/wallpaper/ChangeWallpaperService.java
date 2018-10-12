package com.mzw.wallpaper;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * Created by think on 2018/10/7.
 */

public class ChangeWallpaperService extends Service {
    private WallpaperManager manager;
    private Context mContext;

    private List<WallpaperBean> list;
    private int interval;
    private int i = 0;


    Handler handler=new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //要做的事情
            WallpaperBean bean = list.get(i % list.size());
            if(bean != null && bean.pic > 0){
                Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),bean.pic);
                start(bitmap);
            }
            i++;
            handler.postDelayed(this, interval * 1000);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        manager = WallpaperManager.getInstance(mContext);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        interval = intent.getIntExtra("interval",3);
        list = (List<WallpaperBean>)intent.getSerializableExtra("list");
        Log.i("-----","interval：" + interval);
        handler.removeCallbacks(runnable);//停止任务
        handler.postDelayed(runnable, interval * 1000);//执行任务

        return super.onStartCommand(intent, flags, startId);
    }

    private void start(Bitmap bitmap) {
        if(bitmap != null){
            try {
                manager.setBitmap(bitmap);
                Log.i("-----","更改壁纸成功");
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("-----","设置失败");
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);//停止任务
    }
}
