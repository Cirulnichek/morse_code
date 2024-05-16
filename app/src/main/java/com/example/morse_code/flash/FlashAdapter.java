package com.example.morse_code.flash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.morse_code.flash.Flash;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FlashAdapter {
    private boolean runStatus;
    private String code;
    private Flash flash;
    private int dotDur;
    private Context context;
    private Thread thread;

    public boolean isRunStatus() {
        return runStatus;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDotDur(int dotDur) {
        this.dotDur = dotDur;
    }

    public FlashAdapter(Context context) {
        this.flash = new Flash(context);
        this.context = context;
        this.runStatus = false;
    }

    public void doFlash() {
        runStatus = true;
        thread = new Thread(new FlashCall());
        thread.start();
    }

    public void stopFlash() {
        runStatus = false;
        thread.interrupt();
        flash.flashOff();
    }

    private class FlashCall implements Runnable {
        @Override
        public void run() {
            flash.flashOff();
            for (int i = 0; i < code.length(); i++) {
                if (!runStatus) {
                    break;
                }
                try {
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
                } catch (InterruptedException e) {
                    ///
                }
            }
            runStatus = false;
        }
    }
}
