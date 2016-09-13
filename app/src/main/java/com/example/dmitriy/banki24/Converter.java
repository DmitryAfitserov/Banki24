package com.example.dmitriy.banki24;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Converter extends AppCompatActivity {

    private static final String keyClass = "opa";
    private static final String keyPosition = "naka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);
        String keyclass = (String)getIntent().getSerializableExtra(keyClass);
        int keyposition = (Integer) getIntent().getSerializableExtra(keyPosition);
        FillViewConverter fillViewConverter = new FillViewConverter(Converter.this);

        if(keyclass.equals("Bel")){
            fillViewConverter.fillBelConverter(keyposition);
        }
        if(keyclass.equals("Rus")){
            fillViewConverter.fillRusConverter(keyposition);
        }
        if(keyclass.equals("Metal")){
            fillViewConverter.fillMetalConverter(keyposition);
        }
    }


}
