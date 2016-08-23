package com.dragon.notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * author: zhang.longping
 * date: 16/8/23
 * time: 下午3:50
 */
public class SendMsgActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn1:
                NotificationCenter.getDefaultCenter().send("FIRST_KEY",new NotificationCenter.Notification(null));
                break;
            case R.id.btn2:
                NotificationCenter.getDefaultCenter().send("SECOND_KEY",new NotificationCenter.Notification(null,"dragon"));
                break;
        }


    }
}
