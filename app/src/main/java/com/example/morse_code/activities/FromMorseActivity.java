package com.example.morse_code.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.morse_code.R;
import com.example.morse_code.databinding.ActivityFromMorseBinding;
import com.example.morse_code.domain.Morse;

public class FromMorseActivity extends AppCompatActivity {
    private ActivityFromMorseBinding binding;
    private Dialog dialog;
    private String mode = "russian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFromMorseBinding.inflate(getLayoutInflater());
        dialog = new Dialog(FromMorseActivity.this);
        setContentView(binding.getRoot());
        binding.twLanguage.setText("Language:\nRussian");
        binding.btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromOptionDialog();
            }
        });
        binding.btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text = Morse.morseToText(binding.teFromMorse.getText().toString(), mode);
                    binding.twFromMorse.setText(text);
                } catch (Exception e) {
                    String[] arr = e.getMessage().split(" ");
                    binding.twFromMorse.setText(String.format("Invalid morse code in word %s " +
                            "at symbol %s", arr[0], arr[1]));
                }
            }
        });
    }

    private void fromOptionDialog() {
        dialog.setContentView(R.layout.from_morse_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageButton btnRus = dialog.findViewById(R.id.btn_rus);
        btnRus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "russian";
                binding.twLanguage.setText("Language:\nRussian");
                dialog.dismiss();
            }
        });
        ImageButton btnEng = dialog.findViewById(R.id.btn_eng);
        btnEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "english";
                binding.twLanguage.setText("Language:\nEnglish");
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}