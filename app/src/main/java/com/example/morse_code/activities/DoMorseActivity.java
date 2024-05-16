package com.example.morse_code.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.morse_code.R;
import com.example.morse_code.databinding.ActivityDoMorseBinding;
import com.example.morse_code.domain.Morse;
import com.example.morse_code.flash.FlashAdapter;
import com.example.morse_code.vibration.VibrationAdapter;
import com.google.android.material.slider.Slider;

public class DoMorseActivity extends AppCompatActivity {

    private ActivityDoMorseBinding binding;
    private String mode;
    private int dotDur;

    private FlashAdapter flashAdapter;
    private VibrationAdapter vibrationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoMorseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.dotDurSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                dotDur = (int) v;
            }
        });

        binding.btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == "flash") {
                    if (flashAdapter == null) {
                        flashAdapter = new FlashAdapter(getApplicationContext());
                    }
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                            PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.CAMERA}, 200);
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    if (flashAdapter.isRunStatus()) {
                        flashAdapter.stopFlash();
                        binding.btnDo.setImageResource(R.drawable.ic_do);
                    } else {
                        String code = binding.te.getText().toString();
                        int pos = Morse.checkMorse(code);
                        if (pos > - 1) {
                            Toast.makeText(DoMorseActivity.this, "Invalid symbol at position " + pos, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        binding.btnDo.setImageResource(R.drawable.ic_stop);
                        flashAdapter.setCode(code);
                        flashAdapter.setDotDur(dotDur);
                        flashAdapter.doFlash();
                        binding.btnDo.setImageResource(R.drawable.ic_do);
                    }
                }
                if (mode == "vibro") {
                    if (vibrationAdapter == null) {
                        vibrationAdapter = new VibrationAdapter(getApplicationContext());
                    }
                    //////// Vibrator permission
                    if (vibrationAdapter.isRunStatus()) {
                        vibrationAdapter.stopVibrator();
                    } else {
                        String code = binding.te.getText().toString();
                        int pos = Morse.checkMorse(code);
                        if (pos > - 1) {
                            Toast.makeText(DoMorseActivity.this, "Invalid symbol at position " + pos, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        vibrationAdapter.setCode(code);
                        vibrationAdapter.setDotDur(dotDur);
                        vibrationAdapter.doVibrator();
                    }
                }
            }
        });
        registerForContextMenu(binding.btnSelect);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.do_select_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_light) {
            mode = "flash";
        }
        if (item.getItemId() == R.id.item_vibro) {
            mode = "vibro";
        }
        return super.onContextItemSelected(item);
    }
}