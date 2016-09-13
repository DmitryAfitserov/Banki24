package com.example.dmitriy.banki24;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.BelKursLab;
import model.KursModelRub;

/**
 * Created by Dmitriy on 20.03.2016.
 */
public class BelFragment extends ListFragment implements AsyncDelegate {

    private List<KursModelRub> listKursBel;

    private AsyncTaskBel async;
    private Adapter adapter;
    private static final String keyClass = "opa";
    private static final String keyPosition = "naka";




@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listKursBel = BelKursLab.get().getListBel();

        async = new AsyncTaskBel(this);
        async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);

        View rootView = inflater.inflate(R.layout.list, container, false);

        listKursBel = BelKursLab.get().getListBel();
        ((Adapter) getListAdapter()).clear();
        ((Adapter) getListAdapter()).addAll(listKursBel);
        ((Adapter) getListAdapter()).notifyDataSetChanged();

        return rootView;
    }



    @Override
    public void asynccompleteBel(boolean success) {

       if(success) {

           listKursBel = BelKursLab.get().getListBel();
           ((Adapter) getListAdapter()).clear();
           ((Adapter) getListAdapter()).addAll(listKursBel);
           ((Adapter) getListAdapter()).notifyDataSetChanged();

       } else{
           Toast.makeText(getActivity().getApplicationContext(), "Ошибка интернет соединения", Toast.LENGTH_SHORT).show();
       }

    }

    @Override
    public void clean() {

        if(!listKursBel.isEmpty()) {
            listKursBel.clear();
            BelKursLab.get().cleanListBel();

        }
        if(getListAdapter() == null) {
            adapter = new Adapter((ArrayList) listKursBel);
        }

        setListAdapter(adapter);

        ((Adapter) getListAdapter()).clear();
        ((Adapter) getListAdapter()).addAll(listKursBel);
        ((Adapter) getListAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), Converter.class);
        intent.putExtra(keyClass, "Bel");
        intent.putExtra(keyPosition, position);
        startActivity(intent);
    }

    private class Adapter extends ArrayAdapter<KursModelRub> {
        public Adapter(ArrayList<KursModelRub> list){
            super(getActivity(), 0, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            KursModelRub kBel = getItem(position);
            if(kBel.getShow()){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bel_big, null);

                TextView nameTextview = (TextView)convertView.findViewById(R.id.name_item);
                nameTextview.setText(kBel.getmName());

                TextView currentTextView = (TextView)convertView.findViewById(R.id.rate);

                currentTextView.setText(kBel.getmNominale() + " " + kBel.getmCharCode() + " = " +
                        String.valueOf(kBel.getmRate()) + " руб");

                TextView changeRateTextView = (TextView)convertView.findViewById(R.id.change_rate);

                if(kBel.getGhangeRate().startsWith("+")){
                    changeRateTextView.setTextColor(Color.RED);
                } else changeRateTextView.setTextColor(Color.rgb(5, 179, 17));

                changeRateTextView.setText(kBel.getGhangeRate());

            }

            if(!kBel.getShow()){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bel_smol, null);

                TextView nameTextview = (TextView)convertView.findViewById(R.id.name_item);
                nameTextview.setText(kBel.getmName());

                TextView currentTextView = (TextView)convertView.findViewById(R.id.rate);

                currentTextView.setText(kBel.getmNominale() + " " + kBel.getmCharCode() + " = " +
                        String.valueOf(kBel.getmRate()) + " руб");

                TextView changeRateTextView = (TextView)convertView.findViewById(R.id.change_rate);

                if(kBel.getGhangeRate().startsWith("+")){
                    changeRateTextView.setTextColor(Color.RED);
                } else changeRateTextView.setTextColor(Color.rgb(5, 179, 17));




                changeRateTextView.setText(kBel.getGhangeRate());
            }
            if(position%2==1){
                convertView.setBackgroundColor(Color.argb(190, 235, 240, 240));
            } else convertView.setBackgroundColor(Color.argb(255, 243, 241, 241));





            return convertView;
        }
    }


}
