package com.example.morse_code.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morse_code.R;
import com.example.morse_code.databinding.ActivityToMorseBinding;
import com.example.morse_code.domain.Morse;

public class ToMorseActivity extends AppCompatActivity {

    private ActivityToMorseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityToMorseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.teToMorse.getText().toString();
                String code = Morse.textToMorse(text);
                binding.twToMorse.setText(code);
            }
        });
        binding.twToMorse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.twToMorse.getText().toString();
                if (!text.isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", text);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ToMorseActivity.this, "The text has been copied", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}