package com.example.morse_code.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.morse_code.R;
import com.example.morse_code.fragments.DoFragment;

import java.util.concurrent.CountDownLatch;

public class SoundAdapter {
    public static final String TAG = "Sound Adapter";

    private DoFragment parent;

    private Context context;
    private MediaPlayer dot;
    private MediaPlayer dash;

    private boolean runStatus;
    private Thread thread;
    private Handler handler;
    private CountDownLatch latch;

    private String code;
    private int dotDur;

    public boolean isRunStatus() {
        return runStatus;
    }

    public void setDotDur(int dotDur) {
        this.dotDur = dotDur;
        dot.setPlaybackParams(dot.getPlaybackParams().setSpeed(200.0f / dotDur));
        dash.setPlaybackParams(dash.getPlaybackParams().setSpeed(200.0f / dotDur));
    }

    public void setCode(String code) {
        this.code = code;
    }

    public SoundAdapter(Context context, DoFragment parent) {
        this.context = context;
        this.parent = parent;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                parent.setDo();
            }
        };
        runStatus = false;
        dot = MediaPlayer.create(context, R.raw.dot);
        dash = MediaPlayer.create(context, R.raw.dash);
    }

    public void doSound() {
        runStatus = true;
        latch = new CountDownLatch(1);
        thread = new Thread(new SoundCall());
        thread.setName("Sound Thread");
        thread.start();
        parent.setStop();
        latch.countDown();
    }

    public void stopSound() {
        runStatus = false;
        parent.setDo();
        thread.interrupt();
        stop();
    }

    private void stop() {
        if (dot.isPlaying()) {
            dot.pause();
            dot.seekTo(0);
        }
        if (dash.isPlaying()) {
            dash.pause();
            dash.seekTo(0);
        }
    }

    private class SoundCall implements Runnable {

        @Override
        public void run() {
            try {
                latch.await();
                stop();
                for (int i = 0; i < code.length(); i++) {
                    stop();
                    if (!runStatus) {
                        break;
                    }
                    if (code.charAt(i) == '.') {
                        dot.start();
                        Thread.sleep(dotDur);
                    }
                    if (code.charAt(i) == '-') {
                        dash.start();
                        Thread.sleep(3 * dotDur);
                    }
                    if (code.charAt(i) == ' ') {
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
