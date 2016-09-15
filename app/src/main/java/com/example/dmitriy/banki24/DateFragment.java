package com.example.dmitriy.banki24;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Dmitriy on 13.04.2016.
 */
public class DateFragment extends android.support.v4.app.DialogFragment{

    public static final String Extra_Date = "key_data";
    private Date mDate;


    public static DateFragment newIntance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(Extra_Date, date);
        DateFragment fragment = new DateFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.date_picker, null);
        mDate =(Date)getArguments().getSerializable(Extra_Date);
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(mDate);
        int year = mCalendar.get(mCalendar.YEAR);
        int month = mCalendar.get(mCalendar.MONTH);
        int day = mCalendar.get(mCalendar.DAY_OF_MONTH);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.date_picker);

        Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(1998, 1, 15);
        datePicker.setMinDate(minCalendar.getTimeInMillis());

        Calendar maxCalendar = Calendar.getInstance();
        datePicker.setMaxDate(maxCalendar.getTimeInMillis() + 86400);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDate = new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime();
                getArguments().putSerializable(Extra_Date, mDate);
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.choose_date)
                .setPositiveButton(R.string.button_ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(getActivity().RESULT_OK);
                            }
                        }).setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }
    private void sendResult(int resultCode){
        Intent i = new Intent();
        i.putExtra(Extra_Date, mDate);
        Log.d("KKK", mDate.toString() + "   start");
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);


    }


}
