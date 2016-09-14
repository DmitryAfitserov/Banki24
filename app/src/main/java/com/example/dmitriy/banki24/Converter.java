package com.example.dmitriy.banki24;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Converter extends AppCompatActivity {

    private static final String keyClass = "opa";
    private static final String keyPosition = "naka";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.converter);
        String keyclass = (String)getIntent().getSerializableExtra(keyClass);
        int keyposition = (Integer) getIntent().getSerializableExtra(keyPosition);
        final FillViewConverter fillViewConverter = new FillViewConverter(Converter.this);

        if(keyclass.equals("Bel")){
            fillViewConverter.fillBelConverter(keyposition);
        }
        if(keyclass.equals("Rus")){
            fillViewConverter.fillRusConverter(keyposition);
        }

        ImageButton backButton = fillViewConverter.getBack();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
