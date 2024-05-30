package com.example.morse_code.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.morse_code.R;
import com.example.morse_code.databinding.FragmentTranslateBinding;

public class TranslateFragment extends Fragment {

    private FragmentTranslateBinding binding;
    private FragmentManager fragmentManager;

    private boolean fromMorse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fromMorse = true;
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTranslateBinding.inflate(inflater, container, false);
        setTranslationFragment();
        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromMorse = !fromMorse;
                if (fromMorse) {
                    binding.twFrom.setText("Morse Code");
                    binding.twTo.setText("Language");
                } else {
                    binding.twFrom.setText("Language");
                    binding.twTo.setText("Morse Code");
                }
                setTranslationFragment();
            }
        });
        return binding.getRoot();
    }

    void setTranslationFragment() {
        if (fromMorse) {
            fragmentManager.beginTransaction()
                    .replace(binding.frTranslate.getId(), FromMorseFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(binding.frTranslate.getId(), ToMorseFragment.class, null)
                    .addToBackStack(null)
                    .commit();
        }
    }
}