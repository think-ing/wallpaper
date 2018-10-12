package com.mzw.wallpaper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<WallpaperBean> list;
    private Context mContext;
    private EditText editText;
    private int interval = 3;

    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        serviceIntent = new Intent(mContext, ChangeWallpaperService.class);
        editText = findViewById(R.id.editText);

        //获取数据
        getData();

        //开始更换桌面壁纸
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interval = Integer.parseInt(editText.getText().toString().trim());
                if(interval <= 0 || interval > 9){
                    Toast.makeText(mContext,"间隔时间应在0和9秒之间",Toast.LENGTH_SHORT).show();
                }else{
                    serviceIntent.putExtra("interval",interval);
                    serviceIntent.putExtra("list", (Serializable) list);
                    startService(serviceIntent);
                }
                finish();
            }
        });

        //关闭服务
        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束服务
                stopService(serviceIntent);
            }
        });
    }

    private void getData() {
        list = new ArrayList<WallpaperBean>();
        list.add(new WallpaperBean("","","","",R.mipmap.aa));
        list.add(new WallpaperBean("","","","",R.mipmap.bb));
        list.add(new WallpaperBean("","","","",R.mipmap.cc));
        list.add(new WallpaperBean("","","","",R.mipmap.dd));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 弃用
     * 不用判断是否开启，已开启时 再次开启 不会创建服务直接开始服务（onCreate - 创建服务；onStartCommand - 开始服务）
     *
     * 判断服务是否开启
     * @return
     */
    public static boolean isServiceRunning(Context context, String ServiceName) {
        if (("").equals(ServiceName) || ServiceName == null)
            return false;
        ActivityManager myManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString()
                    .equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
