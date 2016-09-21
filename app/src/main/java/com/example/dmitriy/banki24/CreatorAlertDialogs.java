package com.example.dmitriy.banki24;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.BelKursLab;
import model.KursModelRub;
import model.MetalLab;
import model.ModelforMetal;
import model.RusKursLab;

/**
 * Created by Dmitry on 16.09.2016.
 */
public class CreatorAlertDialogs {

    private SharedPreferences sharedPreferences;
    private Context context;
    private int startpage;
    private ArrayList mSelectedItems;
    private CharSequence[] listwithname;
    private boolean[] listBoolean;
    private List<KursModelRub> listrates;
    private List<String> listshow;
    private int numberSelected = 0;

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


    public void createAlertDialogPageBel(final MainActivity mainActivity, final FragmentStatePagerAdapter pagerAdapter){

        listwithname = new String[BelKursLab.get().getListBel().size()];
        listBoolean = new boolean[BelKursLab.get().getListBel().size()];
        listrates = BelKursLab.get().getListBel();
        listshow = BelKursLab.get().getListisshow();
        mSelectedItems.clear();

        for(int itemrate = 0; itemrate < listrates.size(); itemrate++){
            if(itemrate < listshow.size()){
                listwithname[itemrate] = listrates.get(itemrate).getmName();
                listBoolean[itemrate] = true;
                mSelectedItems.add(itemrate);
                Log.d("EEE", "true");
            }
            if(itemrate >= listshow.size()){
                listwithname[itemrate] = listrates.get(itemrate).getmName();
                listBoolean[itemrate] = false;
                Log.d("EEE", "false");
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Выберите крупное отображение").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ControlDatabases controlDatabases = new ControlDatabases(mainActivity);
                controlDatabases.open();
                controlDatabases.clearBelTable();

                for (int i =0; i < listrates.size(); i++){
                    if(mSelectedItems.contains(i)){
                        listrates.get(i).setShow(true);
                        controlDatabases.insert(listrates.get(i).getmCharCode(), true, "BEL_TABLE");
                    }
                    else{
                        listrates.get(i).setShow(false);
                    }

                }
                BelKursLab.get().sortListBel();
                BelKursLab.get().setListisshow(controlDatabases.queryCharcodeSelected("BEL_TABLE"));
                controlDatabases.close();
                mSelectedItems.clear();
                pagerAdapter.notifyDataSetChanged();

            }
        });
        builder.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMultiChoiceItems(listwithname, listBoolean, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    mSelectedItems.add(which);
                } else if (mSelectedItems.contains(which)) {
                    mSelectedItems.remove(Integer.valueOf(which));
                }
            }
        }).create().show();
    }

    public void createAlertDialogPageRus(final MainActivity mainActivity, final FragmentStatePagerAdapter pagerAdapter){
        mSelectedItems.clear();
        listwithname = new String[RusKursLab.get().getmListRusRub().size()];
        listBoolean = new boolean[RusKursLab.get().getmListRusRub().size()];
        listrates = RusKursLab.get().getmListRusRub();
        listshow = RusKursLab.get().getListisshow();

        Log.d("EEE", String.valueOf(RusKursLab.get().getmListRusRub().size()));

        for(int itemrate = 0; itemrate < listrates.size(); itemrate++){
            if(itemrate < listshow.size()){
                listwithname[itemrate] = listrates.get(itemrate).getmName();
                listBoolean[itemrate] = true;
                mSelectedItems.add(itemrate);
                Log.d("EEE", "true");
            }
            if(itemrate >= listshow.size()){
                listwithname[itemrate] = listrates.get(itemrate).getmName();
                listBoolean[itemrate] = false;
                Log.d("EEE", "false");
            }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Выберите крупное отображение").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ControlDatabases controlDatabases = new ControlDatabases(mainActivity);
                controlDatabases.open();
                controlDatabases.clearRusTable();

                for (int i =0; i < listrates.size(); i++){
                    if(mSelectedItems.contains(i)){
                        listrates.get(i).setShow(true);
                        controlDatabases.insert(listrates.get(i).getmCharCode(), true, "RUS_TABLE");
                    }
                    else{
                        listrates.get(i).setShow(false);
                    }

                }
                RusKursLab.get().sortListRus();
                RusKursLab.get().setListisshow(controlDatabases.queryCharcodeSelected("RUS_TABLE"));
                controlDatabases.close();
                mSelectedItems.clear();
                pagerAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Назад", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setMultiChoiceItems(listwithname, listBoolean, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    mSelectedItems.add(which);
                } else if (mSelectedItems.contains(which)) {
                    mSelectedItems.remove(Integer.valueOf(which));
                }
            }
        }).create().show();
    }

    public void createAlertDialogPageMetal(MainActivity mainActivity, final FragmentStatePagerAdapter pagerAdapter){
        listrates = MetalLab.get().getListForChange();
        listwithname = new String[listrates.size()];
        for(int i = 0; i < listrates.size(); i++){
            listwithname[i] = listrates.get(i).getmName() +"(" +
                    listrates.get(i).getmCharCode() + ")";
        }
        String charcodeisselected = loadString();
        numberSelected = 0;
        for(int i = 0; i < listrates.size(); i++){
            if(charcodeisselected.equals(listrates.get(i).getmCharCode())){
                numberSelected = i;
                break;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Выберите валюту для металлов");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveSrting(listrates.get(numberSelected).getmCharCode());
                MetalLab.get().setNameCurrency(listrates.get(numberSelected).getmCharCode());
                pagerAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setSingleChoiceItems(listwithname, numberSelected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numberSelected = which;
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
        int savedValue = sharedPreferences.getInt("key", 1);
        return savedValue;
    }

    public void saveSrting(String charcode){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("string", charcode);
        editor.commit();
    }
    public String loadString(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String charcode = sharedPreferences.getString("string", "USD");
        return charcode;
    }
}
