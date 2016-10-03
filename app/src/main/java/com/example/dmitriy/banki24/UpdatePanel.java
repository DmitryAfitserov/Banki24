package com.example.dmitriy.banki24;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import model.Date;

/**
 * Created by Dmitriy on 24.03.2016.
 */
public class UpdatePanel extends Fragment {

    private TextView text;
    private TextView date;

    private static final int REQUEST_CODE_DATE = 0;
    public static final String Extra_Date =  "key_data";
    private FragmentStatePagerAdapter pagerAdapter;




  //  public UpdatePanel(ReloadViewPager reloadViewPager){
       // this.reloadViewPager = reloadViewPager;
  //  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_panel, container, false);
        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linearlayout);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setMinimumWidth(2500);
        text = (TextView)v.findViewById(R.id.textbeforeDate);
        date = (TextView)v.findViewById(R.id.dateText);
        upDateButtun(Date.get().getDate());

        return v;
    }

    public void createCalendar(FragmentStatePagerAdapter pagerAdapter){
        this.pagerAdapter = pagerAdapter;
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DateFragment dialogDate = DateFragment.newIntance(Date.get().getDate());

        dialogDate.setTargetFragment(UpdatePanel.this, REQUEST_CODE_DATE);


        dialogDate.show(fm, "tag");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)  return;
        if(requestCode == REQUEST_CODE_DATE){
            java.util.Date dateNew = (java.util.Date)data.getSerializableExtra(Extra_Date);
            Date.get().setDate(dateNew);

            upDateButtun(dateNew);
            pagerAdapter.notifyDataSetChanged();


        }

    }
    private void upDateButtun(java.util.Date data){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String dataString = format.format(data);
        date.setText(dataString);
    }

}
