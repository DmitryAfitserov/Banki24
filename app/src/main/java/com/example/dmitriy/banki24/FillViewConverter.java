package com.example.dmitriy.banki24;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import model.BelKursLab;
import model.KursModelRub;

/**
 * Created by Dmitry on 13.09.2016.
 */
public class FillViewConverter {

    private Converter converter;
    private TextView titleTextView;
    private TextView fromName;
    private TextView toName;
    private TextView fromCode;
    private TextView toCode;
    private EditText fromValue;
    private EditText toValue;


    FillViewConverter(Converter con){
        converter = con;
        titleTextView = (TextView)converter.findViewById(R.id.titleTextView);
        fromName = (TextView)converter.findViewById(R.id.fromname);
        toName = (TextView)converter.findViewById(R.id.toname);
        fromCode = (TextView)converter.findViewById(R.id.fromcode);
        toCode = (TextView)converter.findViewById(R.id.tocode);
        fromValue = (EditText)converter.findViewById(R.id.editText_from);
        toValue = (EditText)converter.findViewById(R.id.editText_to);

    }

    public void fillBelConverter(int position){
        titleTextView.setText("Конвертер валют");
        KursModelRub kmb = BelKursLab.get().getItem(position);
        fromName.setText(kmb.getmName() + "(" + kmb.getmCharCode() + ")");
        toName.setText("Белорусский рубль(BYN)");
        fromCode.setText(kmb.getmCharCode());
        toCode.setText("BYN");
        fromValue.setText(kmb.getmNominale());
        toValue.setText(kmb.getmRate());




    }

    public void fillRusConverter(int position){

    }
    public void fillMetalConverter(int position){

    }
}
