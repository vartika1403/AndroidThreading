package com.example.androidthreading;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int SHOW_PROGRESS_BAR = 0;
    private static final int HIDE_PROGRESS_BAR = 1;
    public static Handler uiHandler;
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


        uiHandler = new Handler(Looper.getMainLooper()) {
            public void handlerMessage(Message message) {
                Log.d(LOG_TAG, "message received");
                switch (message.what) {
                    case SHOW_PROGRESS_BAR:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case HIDE_PROGRESS_BAR:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();
    }

    @SuppressLint("HandlerLeak")
    private  void startUiHandler() {

    }

    @OnClick(R.id.button)
    public void onClick() {
           Message msg = looperThread.handler.obtainMessage();
           looperThread.handler.sendMessage(msg);
    }

    @OnClick(R.id.start_back_thread)
    public void startBackThread() {
        mBackgroundThread.doWork();
    }

    private static class LooperThread extends Thread {
        public Handler handler;

        @SuppressLint("HandlerLeak")
        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        Log.d(LOG_TAG , "message received, " + message.arg1 + ", " + message.arg2);
                        if (uiHandler != null) {
                            Message uiMsg = uiHandler.obtainMessage(0, 0, 0, null);
                            uiHandler.sendMessage(uiMsg);
                        }
                    }
                }
            };
            Looper.loop();
        }
    }

    public class BackgroundThread extends Thread {
        private Handler mBackgroundHandler;

        @Override
        public void run() {
            Looper.prepare();
            mBackgroundHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub

                    if (uiHandler != null) {
                        Message uiMsg = uiHandler.obtainMessage(0, 0, 0, null);
                        uiHandler.sendMessage(uiMsg);
                    }
                }

            };

            Looper.loop();
        }

        public void doWork() {
            mBackgroundHandler.post(new Runnable() {
                @Override
                public void run() {
                    Message uiMsg = uiHandler.obtainMessage(0, 0, 0, null);
                    uiHandler.sendMessage(uiMsg);

                    Random random = new Random();
                    int randomInt = random.nextInt(5);
                    SystemClock.sleep(randomInt);

                    uiMsg = uiHandler.obtainMessage(1, randomInt, 0, null);
                    uiHandler.sendMessage(uiMsg);
                }
            });
        }

        public void exit() {
            mBackgroundHandler.getLooper().quit();
        }
    }

    @Override
    protected void onDestroy() {
        mBackgroundThread.exit();
        super.onDestroy();
    }
}
