package com.example.morse_code.flash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.morse_code.flash.Flash;
import com.example.morse_code.fragments.DoFragment;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FlashAdapter {
    public static final String TAG = "Flash Adapter";

    private boolean runStatus;
    private String code;
    private Flash flash;
    private int dotDur;
    private Context context;
    private Thread thread;
    private DoFragment parent;
    private CountDownLatch latch;
    private Handler handler;

    public boolean isRunStatus() {
        return runStatus;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDotDur(int dotDur) {
        this.dotDur = dotDur;
    }

    public FlashAdapter(Context context, DoFragment parent) {
        this.flash = new Flash(context);
        this.context = context;
        this.runStatus = false;
        this.parent = parent;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                parent.setDo();
            }
        };
    }

    public void doFlash() {
        runStatus = true;
        latch = new CountDownLatch(1);
        thread = new Thread(new FlashCall());
        thread.setName("Flash Thread");
        thread.start();
        parent.setStop();
        latch.countDown();
    }

    public void stopFlash() {
        runStatus = false;
        parent.setDo();
        thread.interrupt();
        flash.flashOff();
    }

    private class FlashCall implements Runnable {

        @Override
        public void run() {
            try {
                latch.await();
                Log.d(TAG, "call");
                flash.flashOff();
                for (int i = 0; i < code.length(); i++) {
                    if (!runStatus) {
                        break;
                    }
                    if (code.charAt(i) == '.') {
                        flash.flashOn();
                        Thread.sleep(dotDur);
                        flash.flashOff();
                    } else if (code.charAt(i) == '-') {
                        flash.flashOn();
                        Thread.sleep(3 * dotDur);
                        flash.flashOff();
                    } else if (code.charAt(i) == ' ') {
                        Thread.sleep(dotDur);
                    }
                }
                runStatus = false;
                handler.sendMessage(handler.obtainMessage());
            } catch (Exception e) {
                Log.e(TAG, "run: " + e.toString());
            }
        }
    }
}
