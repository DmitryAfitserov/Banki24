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
import android.view.animation.AnimationUtils;
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

    boolean mListShown;
    View mProgressContainer;
    View mListContainer;
    public ListView mList;




@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        listKursBel = BelKursLab.get().getListBel();

        async = new AsyncTaskBel(this);
        async.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[])null);

        int INTERNAL_EMPTY_ID = 0x00ff0001;
        View rootView = inflater.inflate(R.layout.list, container, false);
        //(rootView.findViewById(R.id.internalEmpty)).setId(INTERNAL_EMPTY_ID);
        mList = (ListView) rootView.findViewById(android.R.id.list);
        mListContainer =  rootView.findViewById(R.id.listContainer);
        mProgressContainer = rootView.findViewById(R.id.progressContainer);
        mListShown = true;

       // View rootView = inflater.inflate(R.layout.list, container, false);

        listKursBel = BelKursLab.get().getListBel();
        ((Adapter) getListAdapter()).clear();
        ((Adapter) getListAdapter()).addAll(listKursBel);
        ((Adapter) getListAdapter()).notifyDataSetChanged();
            setListShown(false);

        return rootView;
    }



    @Override
    public void asynccompleteBel(boolean success) {

       if(success) {

           listKursBel = BelKursLab.get().getListBel();

           ((Adapter) getListAdapter()).clear();
           ((Adapter) getListAdapter()).addAll(listKursBel);
           ((Adapter) getListAdapter()).notifyDataSetChanged();
           setListShown(true);

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
            Log.d("EEE", String.valueOf(BelKursLab.get().getListBel().size()) + "BelFragment");
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

    public void setListShown(boolean shown, boolean animate){
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        getActivity(), android.R.anim.fade_out));
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mListContainer.setVisibility(View.INVISIBLE);
        }
    }
    public void setListShown(boolean shown){
        setListShown(shown, true);
    }
    public void setListShownNoAnimation(boolean shown) {
        setListShown(shown, false);
    }


}
