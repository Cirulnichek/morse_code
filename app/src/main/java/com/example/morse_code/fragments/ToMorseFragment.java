package com.example.morse_code.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.morse_code.R;
import com.example.morse_code.activities.MainActivity;
import com.example.morse_code.databinding.FragmentToMorseBinding;
import com.example.morse_code.domain.Morse;

public class ToMorseFragment extends Fragment {

    private FragmentToMorseBinding binding;
    private ClipboardManager clipboard;

    public ToMorseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentToMorseBinding.inflate(inflater, container, false);
        binding.btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.et.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(getContext(), "Please, enter some text", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = Morse.textToMorse(text);
                binding.tw.setText(code);
            }
        });
        binding.btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.tw.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(getContext(), "Please, translate something", Toast.LENGTH_SHORT).show();
                    return;
                }
                clipboard.setPrimaryClip(ClipData.newPlainText("Morse code", text));
                Toast.makeText(getContext(), "The text has been copied", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = binding.tw.getText().toString();
                if (text.isEmpty()) {
                    Toast.makeText(getContext(), "Please, translate something", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle args = new Bundle();
                args.putString("text", text);
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                    mainActivity.changeFragment(args);
                }
            }
        });
        return binding.getRoot();
    }
}