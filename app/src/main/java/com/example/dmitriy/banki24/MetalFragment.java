package com.example.dmitriy.banki24;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.KursModelRub;
import model.MetalLab;
import model.ModelforMetal;
import model.RusKursLab;

/**
 * Created by Dmitry on 16.07.2016.
 */
public class MetalFragment extends ListFragment implements AsyncDelegate {

    private AsyncTaskGold async;
    private ArrayList<ModelforMetal> listMetal;
    private ArrayList<String> listwithName = new ArrayList<>();
    private Adapter adapter;
    private String nameCurrency;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      //  listMetal = MetalLab.get().getmListGold();
        if (async == null) {
            async = new AsyncTaskGold(this);
            async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
            ;
           // Log.d("EEE", "запуск асинс");
        }
        View rootView = inflater.inflate(R.layout.list, container, false);
        listwithName = MetalLab.get().getListwithNames();


        return rootView;
    }


    @Override
    public void asynccompleteBel(boolean success) {
        async.cancel(true);
        if (success) {

           // listMetal = MetalLab.get().getmListGold();

            listwithName = MetalLab.get().getListwithNames();
            listMetal = MetalLab.get().getmListGold();
            ((Adapter) getListAdapter()).clear();
            ((Adapter) getListAdapter()).addAll(listwithName);
            ((Adapter) getListAdapter()).notifyDataSetChanged();
            nameCurrency = MetalLab.get().getNameCurrency();


        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Ошибка интернет соединения", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void clean() {
        if (!listwithName.isEmpty()) {
            listwithName.clear();
            listMetal.clear();
        }
        if (getListAdapter() == null) {
            adapter = new Adapter((ArrayList) listwithName);
        }
        setListAdapter(adapter);
        ((Adapter) getListAdapter()).clear();
        ((Adapter) getListAdapter()).addAll(listwithName);
        ((Adapter) getListAdapter()).notifyDataSetChanged();
    }


    private class Adapter extends ArrayAdapter<String> {
        public Adapter(ArrayList<String> list) {
            super(getActivity(), 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_metal, null);
            TextView nameTextview = (TextView) convertView.findViewById(R.id.name_item);
            nameTextview.setText(getItem(position));




            for(int item = 0; item < listMetal.size(); item ++){
                if(listMetal.get(item).getmName().equals(getItem(position))){
                    if(listMetal.get(item).getmNominal().equals("1")){
                        TextView current_1gr_TextView = (TextView) convertView.findViewById(R.id.rate_1gr);

                         current_1gr_TextView.setText(listMetal.get(item).getmNominal() + " гр." + " - " +
                                 listMetal.get(item).getmRate()+ " " + nameCurrency);

                        TextView change_rate_1gr_TextView = (TextView)convertView.findViewById(R.id.change_rate_1gr);

                        if (listMetal.get(item).getGhangeRate().startsWith("+")) {
                            change_rate_1gr_TextView.setTextColor(Color.RED);
                            } else change_rate_1gr_TextView.setTextColor(Color.rgb(5, 179, 17));
                        change_rate_1gr_TextView.setText(listMetal.get(item).getGhangeRate());
                    }

                    if(listMetal.get(item).getmNominal().equals("10")){
                        TextView current_10gr_TextView = (TextView) convertView.findViewById(R.id.rate_10gr);

                        current_10gr_TextView.setText(listMetal.get(item).getmNominal() + " гр." + " - " +
                                listMetal.get(item).getmRate()+ " " + nameCurrency);

                        TextView change_rate_10gr_TextView = (TextView)convertView.findViewById(R.id.change_rate_10gr);

                        if (listMetal.get(item).getGhangeRate().startsWith("+")) {
                            change_rate_10gr_TextView.setTextColor(Color.RED);
                        } else change_rate_10gr_TextView.setTextColor(Color.rgb(5, 179, 17));
                        change_rate_10gr_TextView.setText(listMetal.get(item).getGhangeRate());

                    }
                    if(listMetal.get(item).getmNominal().equals("50")){
                        TextView current_50gr_TextView = (TextView) convertView.findViewById(R.id.rate_50gr);

                        current_50gr_TextView.setText(listMetal.get(item).getmNominal() + " гр." + " - " +
                                listMetal.get(item).getmRate()+ " " + nameCurrency);

                        TextView change_rate_50gr_TextView = (TextView)convertView.findViewById(R.id.change_rate_50gr);

                        if (listMetal.get(item).getGhangeRate().startsWith("+")) {
                            change_rate_50gr_TextView.setTextColor(Color.RED);
                        } else change_rate_50gr_TextView.setTextColor(Color.rgb(5, 179, 17));
                        change_rate_50gr_TextView.setText(listMetal.get(item).getGhangeRate());
                    }
                    if(listMetal.get(item).getmNominal().equals("100")){
                        TextView current_100gr_TextView = (TextView) convertView.findViewById(R.id.rate_100gr);

                        current_100gr_TextView.setText(listMetal.get(item).getmNominal() + " гр." + " - " +
                                listMetal.get(item).getmRate()+ " " + nameCurrency);

                        TextView change_rate_100gr_TextView = (TextView)convertView.findViewById(R.id.change_rate_100gr);

                        if (listMetal.get(item).getGhangeRate().startsWith("+")) {
                            change_rate_100gr_TextView.setTextColor(Color.RED);
                        } else change_rate_100gr_TextView.setTextColor(Color.rgb(5, 179, 17));
                        change_rate_100gr_TextView.setText(listMetal.get(item).getGhangeRate());
                    }
                    if(listMetal.get(item).getmNominal().equals("500")){
                        TextView current_500gr_TextView = (TextView) convertView.findViewById(R.id.rate_500gr);

                        current_500gr_TextView.setText(listMetal.get(item).getmNominal() + " гр." + " - " +
                                listMetal.get(item).getmRate()+ " " + nameCurrency);
                        TextView change_rate_500gr_TextView = (TextView)convertView.findViewById(R.id.change_rate_500gr);

                        if (listMetal.get(item).getGhangeRate().startsWith("+")) {
                            change_rate_500gr_TextView.setTextColor(Color.RED);
                        } else change_rate_500gr_TextView.setTextColor(Color.rgb(5, 179, 17));
                        change_rate_500gr_TextView.setText(listMetal.get(item).getGhangeRate());

                    }
                    if(listMetal.get(item).getmNominal().equals("1000")){
                        TextView current_1000gr_TextView = (TextView) convertView.findViewById(R.id.rate_1000gr);

                        current_1000gr_TextView.setText(listMetal.get(item).getmNominal() + " гр." + " - " +
                                listMetal.get(item).getmRate()+ " " + nameCurrency);

                        TextView change_rate_1000gr_TextView = (TextView)convertView.findViewById(R.id.change_rate_1000gr);

                        if (listMetal.get(item).getGhangeRate().startsWith("+")) {
                            change_rate_1000gr_TextView.setTextColor(Color.RED);
                        } else change_rate_1000gr_TextView.setTextColor(Color.rgb(5, 179, 17));
                        change_rate_1000gr_TextView.setText(listMetal.get(item).getGhangeRate());
                    }
                }
            }


            //if (kRus.getShow()) {
                //convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bel_big, null);

                //TextView nameTextview = (TextView) convertView.findViewById(R.id.name_item);
                //nameTextview.setText(kRus.getmName());

               // TextView currentTextView = (TextView) convertView.findViewById(R.id.rate);

               // currentTextView.setText(kRus.getmNominale() + " " + kRus.getmCharCode() + " = " +
                //        String.valueOf(kRus.getmRate()) + " руб");

                //TextView changeRateTextView = (TextView) convertView.findViewById(R.id.change_rate);


                //if (kRus.getGhangeRate().startsWith("+")) {
                //    changeRateTextView.setTextColor(Color.RED);
                //} else changeRateTextView.setTextColor(Color.GREEN);

                //changeRateTextView.setText(kRus.getGhangeRate());
           // }

            if (position % 2 == 1) {
                convertView.setBackgroundColor(Color.argb(190, 235, 240, 240));
            } else convertView.setBackgroundColor(Color.argb(255, 243, 241, 241));

            return convertView;
        }


    }
}

