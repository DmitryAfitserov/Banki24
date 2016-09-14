package com.example.dmitriy.banki24;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

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
    private Button exchange;
    private KursModelRub kmb;
    private BigDecimal coef;
    private BigDecimal one = new BigDecimal("1");
    private Boolean isStraight = true;
    private Boolean isright = true;



    FillViewConverter(Converter con){
        converter = con;
        titleTextView = (TextView)converter.findViewById(R.id.titleTextView);
        fromName = (TextView)converter.findViewById(R.id.fromname);
        toName = (TextView)converter.findViewById(R.id.toname);
        fromCode = (TextView)converter.findViewById(R.id.fromcode);
        toCode = (TextView)converter.findViewById(R.id.tocode);
        fromValue = (EditText)converter.findViewById(R.id.editText_from);
        toValue = (EditText)converter.findViewById(R.id.editText_to);
        exchange = (Button)converter.findViewById(R.id.exchange_button);

        fromValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                BigDecimal nominal = new BigDecimal(kmb.getmNominale());
                BigDecimal end = new BigDecimal(kmb.getmRate());
                coef = end.divide(nominal, 5,  BigDecimal.ROUND_HALF_UP);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    fromValue.setText("0");
                    fromValue.setSelection(1);
                }
                if(s.length()==2 && s.charAt(0)=='0' && s.charAt(1)!='.'){
                    fromValue.setText(new StringBuilder().append("").append(s.charAt(1)).toString());
                    fromValue.setSelection(1);
                }
                int countpoits = 0;
                for(int i = 0; i < s.length(); i++){
                    if(s.charAt(i)=='.'){
                        countpoits++;
                    }
                    if(countpoits>=2){
                        isright = false;
                    }
                    else isright = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(isright) {
                    String fromr = fromValue.getText().toString();
                    BigDecimal fromrate = new BigDecimal(fromr);
                    if (isStraight) {

                        BigDecimal backcoef = one.divide(coef, 5, BigDecimal.ROUND_HALF_UP);
                        BigDecimal result = fromrate.divide(backcoef, 2, BigDecimal.ROUND_HALF_UP);
                        toValue.setText(result.toString());
                    } else {
                        BigDecimal result = fromrate.divide(coef, 2, BigDecimal.ROUND_HALF_UP);
                        toValue.setText(result.toString());
                    }

                } else toValue.setText("ошибка");
            }
        });
        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buffer;
                buffer = fromName.getText().toString();
                fromName.setText(toName.getText().toString());
                toName.setText(buffer);

                buffer = fromCode.getText().toString();
                fromCode.setText(toCode.getText().toString());
                toCode.setText(buffer);

                buffer = fromValue.getText().toString();
                fromValue.setText(toValue.getText().toString());
                toValue.setText(buffer);
            }
        });


    }

    public void fillBelConverter(int position){
        titleTextView.setText("Конвертер валют");
        kmb = BelKursLab.get().getItem(position);
        fromName.setText(kmb.getmName() + "(" + kmb.getmCharCode() + ")");
        toName.setText("Белорусский рубль(BYN)");
        fromCode.setText(kmb.getmCharCode());
        toCode.setText("BYN");
        fromValue.setText(kmb.getmNominale());
        toValue.setText(kmb.getmRate());
    }

    public void fillRusConverter(int position){

    }

    public Button getBack() {
        return (Button) converter.findViewById(R.id.backbutton);
    }


}
