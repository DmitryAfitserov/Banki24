package com.example.dmitriy.banki24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;

import model.Date;

/**
 * Created by Dmitriy on 24.03.2016.
 */
public class UpdatePanel extends Fragment {

    private Button date;
    private Button upDate;
    private static final int REQUEST_CODE_DATE = 0;
    public static final String Extra_Date =  "key_data";
    private ReloadViewPager reloadViewPager;




  //  public UpdatePanel(ReloadViewPager reloadViewPager){
       // this.reloadViewPager = reloadViewPager;
  //  }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_panel, container, false);
        date = (Button)v.findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DateFragment dialogDate = DateFragment.newIntance(Date.get().getDate());

                dialogDate.setTargetFragment(UpdatePanel.this, REQUEST_CODE_DATE);


                dialogDate.show(fm, "tag");
            }
        });

        upDateButtun(Date.get().getDate());



        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)  return;
        if(requestCode == REQUEST_CODE_DATE){
            java.util.Date dateNew = (java.util.Date)data.getSerializableExtra(Extra_Date);
            Date.get().setDate(dateNew);
          //  Log.d("RRR", "вот оно"  +  date.toString());

            upDateButtun(dateNew);
            reloadViewPager.reload();

        }

    }
    private void upDateButtun(java.util.Date data){
        SimpleDateFormat format = new SimpleDateFormat("dd  MMM  yyyy");
        String dataString = format.format(data);
        date.setText(dataString);
    }

}
