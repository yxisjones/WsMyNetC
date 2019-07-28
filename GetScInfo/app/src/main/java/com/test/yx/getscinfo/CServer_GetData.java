package com.test.yx.getscinfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

/*
 *	CR(R) 2019, GetScInfo
 *	文件名:	
 *	摘  要: 
 *			
 *	当前版本:	1.00
 *	作    者:	developer.yx (Administrator)
 *	完成日期:	2019-07-??
 *	备    注:	 
 *				
 */
/*	-----------------------------------------------------------------------------------------------	*/
public class CServer_GetData extends Service {
    private final String TAG = "CGetDataService";
    private final int NOTIFICATION_ID = android.os.Process.myPid(); //notification使用 启动notification的id，两次启动应是同一个id

    private boolean mThreadRuning = true;      //控制线程循环用 false 时退出

    //测试内部通讯----
    //[binder 方式使用]
    //通过binder 实现调用者client与Service之间的通信
    private CMyBinder binder = new CMyBinder();
    //--->>end

    public CServer_GetData() {
        super();
    }

    //必须要实现的方法
    //// TODO: [测@] --> TMP: Server Bind 入口位置 [key:223]
    // [binder 方式使用] ///////////////////////////////////////////////////////////////////////////
    @Nullable   //调用可以返回null
    @Override
    public IBinder onBind(Intent intent) {
        //// TODO: [测@] --> Bind 运行线程对象 [Key:223]
        //测试线程，判断service是否在工作
        mThreadRuning = true;  //将标志设置为true;
        new Thread(mRunnable).start();
        // 设置为前台进程，降低oom_adj，提高进程优先级，提高存活机率
        setForeground();
        //end

        return binder;  //默认方法
    }

    //[binder 方式使用]
    @Override
    public boolean onUnbind(Intent intent){
        return  super.onUnbind(intent);
    }
    // end -----------------------------------------------------------------------------------------

    //Service被创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //// TODO: [测@] --> Server 运行线程对象 [Key:200]
        //测试线程，判断service是否在工作
        mThreadRuning = true;  //将标志设置为true;
        new Thread(mRunnable).start();

        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @Override
    public void onDestroy() {
        mThreadRuning = false;     //设置线程标志量

        super.onDestroy();
        stopForeground(true);   //线程部分添加
    }

    //测试通讯用的内部类
    public class CMyBinder extends Binder {
        public CServer_GetData getService() {
            return CServer_GetData.this;
        }
    }

    //功能函数 #####################################################################################
    //线程工作
    //创建Runnable对象 Runnable更容易实现资源共享，能多个线程同时处理一个资源。
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //// TODO: [测@] --> Server工作循环线程
            Log.i(TAG, "thread -> " + AEsApplication.get_dataConfig().counter_checkSpan + "_" + AEsApplication.get_dataConfig().checkSpan);
            while (mThreadRuning) {
                try {
                    funTestServerActivity_changeNotiByThread(true);
                    Thread.sleep(AEsApplication.get_dataConfig().sleepTimeSpan);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            funTestServerActivity_changeNotiByThread(false);
            Log.i(TAG, "---> 线程退出");
        }
    };
    //end 创建Runnable

    // 要注意的是android4.3之后Service.startForeground() 会强制弹出通知栏，解决办法是再
    // 启动一个service和推送共用一个通知栏，然后stop这个service使得通知栏消失。
    private void setForeground() {
        if (Build.VERSION.SDK_INT < 18)
        {
            startForeground(NOTIFICATION_ID, getNotification());
            return;
        }

    }
    //----> end 线程测试

    //功能函数 #####################################################################################
    //设置服务器状态
    private void  funTestServerActivity_changeNotiByThread(boolean nBoole) {      //线程中调用该方法进行noti的内容修改
        //System.currentTimeMillis());
        if (nBoole) {
            mNotification.contentView.setTextViewText(R.id.notification_title, "数据获取");
            mNotification.contentView.setTextViewText(R.id.notification_txt, "数据模拟 ");
            mNotification.contentView.setImageViewResource(R.id.notification_icon, R.mipmap.ic_test);
            mNotification.contentView.setImageViewResource(R.id.notification_title_icon, R.mipmap.ic_view_pointblue);
        }
        else {
            mNotification.contentView.setTextViewText(R.id.notification_title, "数据获取");
            mNotification.contentView.setTextViewText(R.id.notification_txt, "服务停止");
            mNotification.contentView.setImageViewResource(R.id.notification_icon, R.mipmap.ic_test);
            mNotification.contentView.setImageViewResource(R.id.notification_title_icon, R.mipmap.ic_view_pointred);

        }
        //mNotification.defaults = Notification.DEFAULT_SOUND;      //声音提示

        mNotiManager.notify(R.string.app_name,mNotification);
    }

    //功能函数 #####################################################################################
    //service 标签UI使用
    public Notification mNotification;       //为修改notification内容将其修改为全局
    //建立Notification
    NotificationManager mNotiManager;       //使用manager来更新？

    //创建Notification
    private Notification getNotification()
    {
        Intent intent = new Intent(this, MainActivity.class);   //Intent Android通信的桥梁
        PendingIntent pendingIntent = PendingIntent.getActivity(        //等待的，未决定的Intent
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder localBuilder = new Notification.Builder(this)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_test)
                .setContentTitle("服务端")
                .setContentText("正在运行...")
                .setTicker(R.string.app_name + "启动");
        Notification notification = localBuilder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;

        return notification;
    }
    //end notification
    //----> end serviec activity

    //测试
    /*
    private  void funTestServerActivity_mkNotification()
    {
        mNotification =
                //getNotification();              //不修改notification的版本
                getNotification_CanChange();
        //startForeground(1,mNotification);     //不修改notification的版本 用这个开始notification

        //修改notification使用以下
        mNotiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotiManager.notify(R.string.app_name, mNotification);
        //end 修改
    }

    private Notification getNotification_CanChange() {  //修改notification的版本
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_main);

        Intent intent = new Intent(this, MainActivity.class);   //Intent Android通信的桥梁
        PendingIntent pendingIntent = PendingIntent.getActivity(        //等待的，未决定的Intent
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification.Builder localBuilder = new Notification.Builder(this)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_test)
                .setContentTitle("Socket服务端")
                .setContentText("正在运行...")
                .setTicker(R.string.app_name + "启动")
                .setContent(remoteViews);

        Notification notification = localBuilder.build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.contentView.setTextViewText(R.id.notification_title,"Test");
        notification.contentView.setTextViewText(R.id.notification_txt,"Test1234");
        notification.contentView.setImageViewResource(R.id.notification_icon,R.mipmap.ic_test);
        return notification;
    }
     */
}
