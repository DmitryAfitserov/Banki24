package com.example.dmitriy.banki24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Converter extends AppCompatActivity {

    private static final String keyClass = "opa";
    private static final String keyPosition = "naka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);
        String keyclass = (String)getIntent().getSerializableExtra(keyClass);
    }
}
