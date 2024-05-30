package com.example.morse_code.vibration;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.morse_code.fragments.DoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class VibrationAdapter {
    private DoFragment parent;
    private Vibrator vibrator;
    private Context context;
    private Handler handler;
    private Runnable setDoRunnable;

    private String code;
    private int dotDur;
    private boolean runStatus;

    public void setCode(String code) {
        this.code = code;
    }

    public void setDotDur(int dotDur) {
        this.dotDur = dotDur;
    }

    public boolean isRunStatus() {
        return runStatus;
    }

    public VibrationAdapter(Context context, DoFragment parent) {
        this.context = context;
        this.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.parent = parent;
        this.handler = new Handler(Looper.getMainLooper());
        this.setDoRunnable = new Runnable() {
            @Override
            public void run() {
                parent.setDo();
                runStatus = false;
            }
        };
        runStatus = false;
    }

    public void doVibrator() {
        runStatus = true;
        parent.setStop();
        ArrayList<Long> pattern = new ArrayList<>();
        int time = 0;
        pattern.add(0L);
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) == ' ') {
                pattern.set(pattern.size() - 1, pattern.get(pattern.size() - 1) + (long) dotDur * 2);
                time += dotDur * 2;
                continue;
            }
            if (code.charAt(i) == '.') {
                pattern.add((long) dotDur);
                time += dotDur;
            }
            if (code.charAt(i) == '-') {
                pattern.add((long) dotDur * 3);
                time += dotDur * 3;
            }
            pattern.add((long) dotDur);
            time += dotDur;
        }
        pattern.remove(pattern.size() - 1);
        time -= dotDur;
        long[] vibrationPattern = new long[pattern.size()];
        for (int i = 0; i < pattern.size(); i++) {
            vibrationPattern[i] = pattern.get(i);
        }
        vibrator.vibrate(vibrationPattern, -1);
        handler.postDelayed(setDoRunnable, time);
    }

    public void stopVibrator() {
        runStatus = false;
        vibrator.cancel();
        parent.setDo();
        handler.removeCallbacks(setDoRunnable);
    }
}
