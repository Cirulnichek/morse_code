package com.example.morse_code.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.morse_code.R;
import com.example.morse_code.databinding.ActivityMainBinding;
import com.example.morse_code.fragments.DoFragment;
import com.example.morse_code.fragments.TranslateFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(Color.WHITE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fragmentManager = getSupportFragmentManager();


        binding.nvgMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.item_translate) {
                    fragmentManager.beginTransaction()
                            .replace(binding.frMain.getId(), TranslateFragment.class, null)
                            .addToBackStack(null)
                            .commit();
                }
                if (id == R.id.item_do) {
                    fragmentManager.beginTransaction()
                            .replace(binding.frMain.getId(), DoFragment.class, null)
                            .addToBackStack(null)
                            .commit();
                }
                return true;
            }
        });
    }

    public void changeFragment(Bundle args) {
        fragmentManager.beginTransaction()
                .replace(binding.frMain.getId(), DoFragment.class, args)
                .addToBackStack(null)
                .commit();
    }
}