package com.example.morse_code.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.morse_code.R;
import com.example.morse_code.databinding.FragmentFromMorseBinding;
import com.example.morse_code.domain.Morse;

import java.lang.reflect.Array;

import kotlin.collections.UArraySortingKt;

public class FromMorseFragment extends Fragment {

    private FragmentFromMorseBinding binding;
    public static final String TAG = "myLogs";

    private final String[] items = {"English", "Russian"};
    private String mode;

    public FromMorseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFromMorseBinding.inflate(inflater, container, false);
        binding.listLang.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.list_item, items));
        binding.listLang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mode = items[position].toLowerCase();
            }
        });
        binding.btnTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == null) {
                    Toast.makeText(getContext(), "Please, select language to translate", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String text = Morse.morseToText(binding.etTranslate.getText().toString(), mode);
                    if (text.isEmpty()) {
                        Toast.makeText(getContext(), "Please, enter some morse code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.d(TAG, text);
                    binding.twTranslate.setText(text);
                } catch (Exception e) {
                    String[] arr = e.getMessage().split(" ");
                    binding.twTranslate.setText(String.format("Invalid morse code in word %s " +
                            "at symbol %s", arr[0], arr[1]));
                }
            }
        });
        return binding.getRoot();
    }
}