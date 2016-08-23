package com.dragon.notification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * author: zhang.longping
 * date: 16/8/23
 * time: 下午3:10
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private NotificationCenter.NotificationObserver thirdObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(this);
        addListener();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,SendMsgActivity.class));

    }


    private void addListener(){

        NotificationCenter.getDefaultCenter().on("FIRST_KEY", new NotificationCenter.NotificationObserver() {
            @Override
            public void notify(String name, NotificationCenter.Notification notification) {
                Toast.makeText(MainActivity.this,"收到通知了",Toast.LENGTH_SHORT).show();
            }
        });


        NotificationCenter.getDefaultCenter().on("SECOND_KEY", new NotificationCenter.NotificationObserver() {
            @Override
            public void notify(String name, NotificationCenter.Notification notification) {
                String param =(String) notification.getArgs()[0];
                Toast.makeText(MainActivity.this,"hello " + param,Toast.LENGTH_SHORT).show();
            }
        });

        thirdObserver = new NotificationCenter.NotificationObserver() {
            @Override
            public void notify(String name, NotificationCenter.Notification notification) {
                String param =(String) notification.getArgs()[0];
                Toast.makeText(MainActivity.this,"hello " + param + " by thirdObserver", Toast.LENGTH_SHORT).show();
            }
        };

        NotificationCenter.getDefaultCenter().on("SECOND_KEY",thirdObserver);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 页面退出的时候取消监听，避免内存泄漏
         */
        NotificationCenter.getDefaultCenter().off(thirdObserver);


    }
}
