package com.example.dmitriy.banki24;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.util.ArrayList;

import model.BelKursLab;

public class MainActivity extends FragmentActivity implements ReloadViewPager {


    private ViewPager viewPager;
    private FragmentManager fm ;
    private FragmentTransaction fragmentTransaction;
    private Toolbar mActionBarToolbar;
    private  FragmentStatePagerAdapter pagerAdapter;
    private CreatorAlertDialogs creatorDialogs;




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_conteiner);
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(mActionBarToolbar);

        creatorDialogs = new CreatorAlertDialogs(getApplicationContext());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);




        fm = getSupportFragmentManager();
       // if(fragmentTransaction == null) {
        fragmentTransaction = null;
            fragmentTransaction = fm.beginTransaction();
       // }

        if(fragmentTransaction.isEmpty()) {
            fragmentTransaction.add(R.id.FragmentConteiner, new UpdatePanel(this)).commit();
        }

        pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                if (position == 2) {
                    return new BelFragment();
                }
                if (position == 1) {
                    return new RusFragment();
                }
                if (position == 0) {
                    return new MetalFragment();
                }
                return new RusFragment();
            }


            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 1) {
                    return "Российский рубль";
                }
                if (position == 2) {
                    return "Белорусский рубль";
                }
                if (position == 0) {
                    return "Драгоценные металы";
                }

                return "Title ";
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };

        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(creatorDialogs.loadInt());

    }

    @Override
    public void reload() {
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()){
            case R.id.startpage:
                creatorDialogs.createAlertDialogStartPage(this);
                return true;
            case R.id.belrub:
                creatorDialogs.createAlertDialogPage(this);
        }

        return super.onMenuItemSelected(featureId, item);

    }

}