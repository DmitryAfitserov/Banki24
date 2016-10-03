package com.example.dmitriy.banki24;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import model.BelKursLab;
import model.MetalLab;
import model.RusKursLab;

public class MainActivity extends FragmentActivity {


    private ViewPager viewPager;
    private PagerTabStrip pagerTabStrip;
    private FragmentManager fm ;
    private FragmentTransaction fragmentTransaction;
    private Toolbar mActionBarToolbar;
    private  FragmentStatePagerAdapter pagerAdapter;
    private CreatorAlertDialogs creatorDialogs;
    UpdatePanel panel;




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
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColor(Color.BLUE);
        pagerTabStrip.setTextColor(Color.BLUE);

        ControlDatabases controlDatabases = new ControlDatabases(getApplicationContext());
        controlDatabases.open();
        Cursor cursorBel = controlDatabases.queryCharcodeSelected("BEL_TABLE");
        Cursor cursorRus = controlDatabases.queryCharcodeSelected("RUS_TABLE");
        BelKursLab.get().setListisshow(cursorBel);
        RusKursLab.get().setListisshow(cursorRus);
        MetalLab.get().setNameCurrency(creatorDialogs.loadString());

        panel = new UpdatePanel();

        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        if(savedInstanceState != null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("updatePanel");
            if(fragment!=null) {
                fragmentTransaction.detach(fragment);
            }
        }

        fragmentTransaction.add(R.id.FragmentConteiner, panel, "updatePanel").commit();



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
                    return new MetalFragment();
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
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }



    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null) {
            switch (item.getItemId()) {
                case R.id.startpage:
                    creatorDialogs.createAlertDialogStartPage(this);
                    return true;
                case R.id.belrub:
                    creatorDialogs.createAlertDialogPageBel(this, pagerAdapter);
                    return true;
                case R.id.rusrub:
                    creatorDialogs.createAlertDialogPageRus(this, pagerAdapter);
                    return true;
                case R.id.metal:
                    creatorDialogs.createAlertDialogPageMetal(this, pagerAdapter);
                    return true;
                case R.id.update:
                    pagerAdapter.notifyDataSetChanged();
                    return true;
                case R.id.calendar:
                    panel.createCalendar(pagerAdapter);
                    return true;
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Необходимо интернет соединение",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onMenuItemSelected(featureId, item);

    }
}