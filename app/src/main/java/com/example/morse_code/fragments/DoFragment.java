package com.example.morse_code.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.morse_code.R;
import com.example.morse_code.databinding.FragmentDoBinding;
import com.example.morse_code.domain.Morse;
import com.example.morse_code.flash.FlashAdapter;
import com.example.morse_code.sound.SoundAdapter;
import com.example.morse_code.vibration.VibrationAdapter;
import com.google.android.material.slider.Slider;

public class DoFragment extends Fragment {

    private FragmentDoBinding binding;
    private static final String TAG = "DoFragment";
    private static final String param = "text";

    public static final String[] items = {"Light","Vibration", "Sound"};
    private String mode;
    private int dotDur;

    private FlashAdapter flashAdapter;
    private VibrationAdapter vibrationAdapter;
    private SoundAdapter soundAdapter;

    public DoFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flashAdapter = new FlashAdapter(getActivity().getApplicationContext(), this);
        vibrationAdapter = new VibrationAdapter(getActivity().getApplicationContext(), this);
        soundAdapter = new SoundAdapter(getContext(), this);
        dotDur = 200;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            binding.et.setText(getArguments().getString(param));
        }
        binding.listWay.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item, items));
        binding.listWay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mode = items[position].toLowerCase();
            }
        });
        binding.btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == null) {
                    Toast.makeText(getContext(), "Please, choose way to do Morse", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (mode) {
                    case "light":
                        doLight();
                        break;
                    case "vibration":
                        doVibrator();
                        break;
                    case "sound":
                        doSound();
                        break;
                }
            }
        });
        binding.sliderDotDur.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                dotDur = (int) v;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mode == null) {
            return;
        }
        switch (mode) {
            case "light":
                flashAdapter.stopFlash();
                break;
            case "vibration":
                vibrationAdapter.stopVibrator();
                break;
            case "sound":
                soundAdapter.stopSound();
                break;
        }
    }

    private void doLight() {
        if (flashAdapter.isRunStatus()) {
            flashAdapter.stopFlash();
        } else {
            String code = binding.et.getText().toString();
            if (code.isEmpty()) {
                Toast.makeText(getContext(), "Please, enter some morse code", Toast.LENGTH_SHORT).show();
                return;
            }
            int pos = Morse.checkMorse(code);
            if (pos > - 1) {
                Toast.makeText(getContext(), "Invalid symbol at position " + pos, Toast.LENGTH_SHORT).show();
                return;
            }
            flashAdapter.setCode(code);
            flashAdapter.setDotDur(dotDur);
            flashAdapter.doFlash();
        }
    }

    private void doVibrator() {
        if (vibrationAdapter.isRunStatus()) {
            vibrationAdapter.stopVibrator();
        } else {
            String code = binding.et.getText().toString();
            if (code.isEmpty()) {
                Toast.makeText(getContext(), "Please, enter some morse code", Toast.LENGTH_SHORT).show();
                return;
            }
            int pos = Morse.checkMorse(code);
            if (pos > - 1) {
                Toast.makeText(getContext(), "Invalid symbol at position " + pos, Toast.LENGTH_SHORT).show();
                return;
            }
            vibrationAdapter.setCode(code);
            vibrationAdapter.setDotDur(dotDur);
            vibrationAdapter.doVibrator();
        }
    }

    private void doSound() {
        if (soundAdapter.isRunStatus()) {
            soundAdapter.stopSound();
        } else {
            String code = binding.et.getText().toString();
            if (code.equals("Enter morse code hear")) {
                Toast.makeText(getContext(), "Please, enter some morse code", Toast.LENGTH_SHORT).show();
                return;
            }
            int pos = Morse.checkMorse(code);
            if (pos > -1) {
                Toast.makeText(getContext(), "Invalid symbol at position " + pos, Toast.LENGTH_SHORT).show();
                return;
            }
            soundAdapter.setCode(code);
            soundAdapter.setDotDur(dotDur);
            soundAdapter.doSound();
        }
    }

    public void setDo() {
        binding.menuLanguage.setEnabled(true);
        binding.sliderDotDur.setEnabled(true);
        binding.et.setEnabled(true);
        binding.btnDo.setBackgroundColor(getResources().getColor(R.color.btn_color));
        binding.btnDo.setText("Do!");
    }

    public void setStop() {
        binding.menuLanguage.setEnabled(false);
        binding.sliderDotDur.setEnabled(false);
        binding.et.setEnabled(false);
        binding.btnDo.setBackgroundColor(Color.RED);
        binding.btnDo.setText("Stop");
    }
}