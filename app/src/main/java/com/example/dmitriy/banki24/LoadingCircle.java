package com.example.dmitriy.banki24;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.math.BigDecimal;
import java.util.zip.Inflater;

/**
 * Created by Dmitry on 15.09.2016.
 */
public class LoadingCircle {


    public ListView mList;
    boolean mListShown;
    View mProgressContainer;
    View mListContainer;
    Activity context;

    public LoadingCircle(View rootView, Activity context){
        this.context = context;

        mList = (ListView) rootView.findViewById(android.R.id.list);
        mListContainer =  rootView.findViewById(R.id.listContainer);
        mProgressContainer = rootView.findViewById(R.id.progressContainer);

        mListShown = true;

    }

    public void setListShown(boolean shown, boolean animate){
        if (mListShown == shown) {
            return;
        }
        mListShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        context, android.R.anim.fade_out));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        context, android.R.anim.fade_in));
            }
            mProgressContainer.setVisibility(View.GONE);
            mListContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(
                        context, android.R.anim.fade_in));
                mListContainer.startAnimation(AnimationUtils.loadAnimation(
                        context, android.R.anim.fade_out));
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
