package com.example.dmitriy.banki24;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.KursModelRub;
import model.RusKursLab;

/**
 * Created by Dmitriy on 24.03.2016.
 */
public class RusFragment extends ListFragment implements AsyncDelegate {

    private ArrayList<KursModelRub> listKursRus = new ArrayList<>();
    private AsyncTaskRus async;
    Adapter adapter;
    private static final String keyClass = "opa";
    private static final String keyPosition = "naka";
    LoadingCircle loadingCircle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listKursRus =  RusKursLab.get().getmListRusRub();
        if(async == null){
            async = new AsyncTaskRus(this);
            async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);
        }

        View rootView = inflater.inflate(R.layout.list, container, false);
        loadingCircle = new LoadingCircle(rootView, getActivity());
        loadingCircle.setListShown(false);
        listKursRus = RusKursLab.get().getmListRusRub();



        return rootView;
    }



    @Override
    public void asynccompleteBel(boolean success) {
        loadingCircle.setListShown(true);
        async.cancel(true);
        if(success) {

            listKursRus = RusKursLab.get().getmListRusRub();
         //   getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);

            ((Adapter) getListAdapter()).clear();
            ((Adapter) getListAdapter()).addAll(listKursRus);
            ((Adapter) getListAdapter()).notifyDataSetChanged();

        } else{
            Toast.makeText(getActivity().getApplicationContext(), "Ошибка интернет соединения", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void clean() {
        if(!listKursRus.isEmpty()) {
            listKursRus.clear();

        }
        if(getListAdapter()==null) {
            adapter = new Adapter((ArrayList) listKursRus);
        }
        setListAdapter(adapter);
        ((Adapter) getListAdapter()).clear();
        ((Adapter) getListAdapter()).addAll(listKursRus);
        ((Adapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), Converter.class);
        intent.putExtra(keyClass, "Rus");
        intent.putExtra(keyPosition, position);
        startActivity(intent);
    }

    private class Adapter extends ArrayAdapter<KursModelRub> {
        public Adapter(ArrayList<KursModelRub> list){
            super(getActivity(), 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            KursModelRub kRus = getItem(position);


            if(kRus.getShow()){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bel_big, null);

                TextView nameTextview = (TextView)convertView.findViewById(R.id.name_item);
                nameTextview.setText(kRus.getmName());

                TextView currentTextView = (TextView)convertView.findViewById(R.id.rate);

                currentTextView.setText(kRus.getmNominale() + " " + kRus.getmCharCode() + " = " +
                        String.valueOf(kRus.getmRate()) + " руб");

                TextView changeRateTextView = (TextView)convertView.findViewById(R.id.change_rate);


                if(kRus.getGhangeRate().startsWith("+")){
                    changeRateTextView.setTextColor(Color.RED);
                } else changeRateTextView.setTextColor(Color.rgb(5, 179, 17));

                changeRateTextView.setText(kRus.getGhangeRate());

            }

            if(!kRus.getShow()){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bel_smol, null);

                TextView nameTextview = (TextView)convertView.findViewById(R.id.name_item);
                nameTextview.setText(kRus.getmName());

                TextView currentTextView = (TextView)convertView.findViewById(R.id.rate);

                currentTextView.setText(kRus.getmNominale() + " " + kRus.getmCharCode() + " = " +
                        String.valueOf(kRus.getmRate()) + " руб");

                TextView changeRateTextView = (TextView)convertView.findViewById(R.id.change_rate);

                if(kRus.getGhangeRate().startsWith("+")){
                    changeRateTextView.setTextColor(Color.RED);
                } else changeRateTextView.setTextColor(Color.rgb(5, 179, 17));




                changeRateTextView.setText(kRus.getGhangeRate());
            }
            if(position%2==1){
                convertView.setBackgroundColor(Color.argb(190, 235, 240, 240));
            } else convertView.setBackgroundColor(Color.argb(255, 243, 241, 241));




            return convertView;
        }
    }
}

