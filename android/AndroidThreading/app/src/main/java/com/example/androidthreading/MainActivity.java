package com.example.androidthreading;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int SHOW_PROGRESS_BAR = 0;
    private static final int HIDE_PROGRESS_BAR = 1;
    public Handler uiHandler;
    private LooperThread looperThread;
    private BackgroundThread mBackgroundThread;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

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

    @OnClick(R.id.start_back_thread)
    public void startBackThread() {
        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();
    }

    private class LooperThread extends Thread {
        public Handler handler;

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        Log.d(LOG_TAG , "message received, " + message.arg1 + ", " + message.arg2);
                        Message message1 = new Message();
                        message1.what =HIDE_PROGRESS_BAR;
                        message1.arg1 = 0;
                        uiHandler.sendMessage(message1);
                    }
                }
            };
            Looper.loop();
        }
    }

    public class BackgroundThread extends Thread {

        @Override
        public void run() {

            Message message = new Message();
            message.what = SHOW_PROGRESS_BAR;
            message.arg1=0;
            uiHandler.sendMessage(message);
        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onResume() {
        super.onResume();
        uiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(LOG_TAG, "message ui handler, " + msg);
                if (msg.what == SHOW_PROGRESS_BAR) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (msg.what == HIDE_PROGRESS_BAR) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
