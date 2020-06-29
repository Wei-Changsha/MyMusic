package com.example.musicandroid.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.musicandroid.R;

public class BaseHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_home);
    }
}
