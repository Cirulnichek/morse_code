package com.example.morse_code.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morse_code.R;
import com.example.morse_code.databinding.ActivityDoMorseBinding;

public class DoMorseActivity extends AppCompatActivity {

    private ActivityDoMorseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoMorseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}