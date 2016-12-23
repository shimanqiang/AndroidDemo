package com.smq.notification_demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private Handler mHadler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_simple:
                simpleNotification();
                break;
            case R.id.btn_simple_can_click:
                simpleCanClickNotification();
                break;
            case R.id.btn_sound_and_vibrate:
                soundAndVibrateNotification();
                break;
            case R.id.btn_sound_and_vibrate_led:
                mHadler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        soundAndVibrateNotification();
                    }
                }, 10 * 1000);
                break;
            case R.id.btn_big_text:
                bigTextNotify();
                break;
            case R.id.btn_priority:
                bigPictureNotify();
                break;
            case R.id.btn_progess:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download();
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    private void download() {
        int i = 0;
        while (true) {
            if (i == 100) {
                break;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;

            /**
             * 调用
             */
            dowloadProgress(i);
        }
    }

    private void dowloadProgress(int progress) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        /***
         * 在这里我们用自定的view来显示Notification;自定义视图
         */
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setTextViewText(R.id.notificationTitle, "正在下载");
        contentView.setTextViewText(R.id.notificationPercent, progress + "%");
        contentView.setProgressBar(R.id.notificationProgress, 100, progress, false);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("简单通知栏");//通知的标题
        builder.setContentText("这是一个简单的通知内容");//通知的正文内容
        builder.setWhen(System.currentTimeMillis()); //指定通知被创建的时间
        builder.setSmallIcon(R.mipmap.ic_launcher); //通知的小图标，显示在系统状态栏上
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.t10)); //通知的大图标，显示在，下拉通知栏的时候

        /**
         * TODO 2选1使用
         * .setContent(contentView) 是自定义的视图
         * .setProgress(100,progress,false) //设置为true，表示流动
         */
        //builder.setContent(contentView);
        builder.setProgress(100, progress, false); //设置为true，表示流动
        manager.notify(5, builder.build());
    }

    private void bigPictureNotify() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /**
         * 设置style
         */

        String text = "通知（Notification）是 Android 系统中比较有特色的一个功能，当某个应用程序希望向\n" +
                "用户发出一些提示信息，而该应用程序又不在前台运行时，就可以借助通知来实现。发出一\n" +
                "条通知后，手机最上方的状态栏中会显示一个通知的图标，下拉状态栏后可以看到通知的详\n" +
                "细内容。\n" +
                "使用：当程序进入到后台的时候我们才需要使用通知，所以在广播接收器和服务中使用通知较多。";
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText(text);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.t10));


        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("简单通知栏")//通知的标题
                .setContentText("这是一个简单的通知内容")//通知的正文内容
                .setWhen(System.currentTimeMillis()) //指定通知被创建的时间
                .setSmallIcon(R.mipmap.ic_launcher) //通知的小图标，显示在系统状态栏上
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.t10)) //通知的大图标，显示在，下拉通知栏的时候
                //设置长段文字
                //.setStyle(bigTextStyle)
                //设置大图片
                .setStyle(bigPictureStyle)

                //TODO 设置重要程度
                .setPriority(NotificationCompat.PRIORITY_MAX)

                .build();
        manager.notify(4, notification);
    }

    private void bigTextNotify() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /**
         * 设置style
         */

        String text = "通知（Notification）是 Android 系统中比较有特色的一个功能，当某个应用程序希望向\n" +
                "用户发出一些提示信息，而该应用程序又不在前台运行时，就可以借助通知来实现。发出一\n" +
                "条通知后，手机最上方的状态栏中会显示一个通知的图标，下拉状态栏后可以看到通知的详\n" +
                "细内容。\n" +
                "使用：当程序进入到后台的时候我们才需要使用通知，所以在广播接收器和服务中使用通知较多。";
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText(text);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.t10));


        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("简单通知栏")//通知的标题
                .setContentText("这是一个简单的通知内容")//通知的正文内容
                .setWhen(System.currentTimeMillis()) //指定通知被创建的时间
                .setSmallIcon(R.mipmap.ic_launcher) //通知的小图标，显示在系统状态栏上
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.t10)) //通知的大图标，显示在，下拉通知栏的时候
                //设置长段文字
                .setStyle(bigTextStyle)
                //TODO 设置大图片
                //.setStyle(bigPictureStyle)

                .build();
        manager.notify(3, notification);
    }

    private void soundAndVibrateNotification() {
        /**
         * 获取通知栏管理器
         */
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /**
         * 构建通知栏
         */
        String uriStr = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.ding;
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("播放声音和振动")//通知的标题
                .setContentText("这是一个简单的通知内容")//通知的正文内容
                .setWhen(System.currentTimeMillis()) //指定通知被创建的时间
                .setSmallIcon(R.mipmap.ic_launcher) //通知的小图标，显示在系统状态栏上
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.t10)) //通知的大图标，显示在，下拉通知栏的时候
                .setSound(Uri.parse(uriStr))
                //振动需要添加权限:android.permission.VIBRATE
                .setVibrate(new long[]{0, 1000, 1000, 1000}) //奇数下标：手机静止时长，偶数下标：手机振动时长
                //LED灯：颜色，手机亮灯时长，手机等暗去的时长：：貌似需要手机锁屏的情况下，才显示
                .setLights(Color.GREEN, 1000, 1000)
                /**
                 * 默认效果：会根据当前手机的环境决定播放什么铃声，振动等
                 */
                //.setDefaults(NotificationCompat.DEFAULT_ALL)

                .build();

        /**
         * 发送通知:第一个参数id,要保证每个通知的id都不同。
         */
        manager.notify(2, notification);

    }

    private void simpleCanClickNotification() {
        /**
         * 获取通知栏管理器
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /**
         * 构建通知栏点击需要启动的Activity:NotifyActivity
         */
        Intent intent = new Intent(this, NotifyActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);


        /**
         * 构建通知栏
         */
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("通知栏可以点击")
                .setContentText("测试内容数据")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.t10))
                .setContentIntent(pi) //TODO 设置启动的界面
                .setAutoCancel(true) //设置true:点击后，取消通知栏显示
                .build();

        /**
         * 发送通知:第一个参数id,要保证每个通知的id都不同。
         */
        notificationManager.notify(1, notification);

        /**
         * 取消显示通知栏:id要和notify的时候一致
         */
        //notificationManager.cancel(1);
    }

    private void simpleNotification() {
        /**
         * 获取通知栏管理器
         */
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        /**
         * 构建通知栏
         */
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("简单通知栏")//通知的标题
                .setContentText("这是一个简单的通知内容")//通知的正文内容
                .setWhen(System.currentTimeMillis()) //指定通知被创建的时间
                .setSmallIcon(R.mipmap.ic_launcher) //通知的小图标，显示在系统状态栏上
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.t10)) //通知的大图标，显示在，下拉通知栏的时候
                .build();

        /**
         * 发送通知:第一个参数id,要保证每个通知的id都不同。
         */
        manager.notify(0, notification);

    }
}
