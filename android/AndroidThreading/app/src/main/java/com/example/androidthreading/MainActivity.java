package com.example.androidthreading;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private LooperThread looperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        looperThread =new LooperThread();
        looperThread.start();
    }

   @OnClick(R.id.button)
    public void onClick() {
           Message msg = looperThread.handler.obtainMessage();
           looperThread.handler.sendMessage(msg);
    }

    private static class LooperThread extends Thread {
        public Handler handler;

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        Log.d(LOG_TAG , "message received, " + message.arg1 + ", " + message.arg2);
                    }
                }
            };
            Looper.loop();
        }
    }
}
