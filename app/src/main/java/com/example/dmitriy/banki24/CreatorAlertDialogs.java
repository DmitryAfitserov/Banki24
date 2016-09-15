package com.example.dmitriy.banki24;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;

import model.BelKursLab;

/**
 * Created by Dmitry on 16.09.2016.
 */
public class CreatorAlertDialogs {

    private SharedPreferences sharedPreferences;
    private Context context;
    private int startpage;
    ArrayList mSelectedItems;
    CharSequence[] ar;

    public CreatorAlertDialogs(Context context){
        this.context = context;
        mSelectedItems = new ArrayList();
    }

    public void createAlertDialogStartPage(MainActivity mainActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);

        builder.setTitle("Стартовая страница").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveInt(startpage);
            }
        });
        builder.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setSingleChoiceItems(R.array.array_name_page, loadInt(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startpage = which;

            }
        });
        builder.create().show();
    }


    public void createAlertDialogPage(MainActivity mainActivity){
        ar = new String[BelKursLab.get().getListBel().size()];
        for(int i  = 0; i < ar.length; i++){
            ar[i] = BelKursLab.get().getListBel().get(i).getmName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Выберите крупное отображение").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMultiChoiceItems(ar, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    mSelectedItems.add(which);
                } else if (mSelectedItems.contains(which)) {
                    // Else, if the item is already in the array, remove it
                    mSelectedItems.remove(Integer.valueOf(which));
                }
            }
        }).create().show();
    }


    public void saveInt(int value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key", value);
        editor.commit();
    }
    public int loadInt(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int savedValue = sharedPreferences.getInt("key", 0);
        return savedValue;
    }
}
