package com.stepsotf.soundsbook_struct;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
/*
dependencies {
    compile 'com.android.support:support-v13:24.0.+'
 */
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import static com.stepsotf.soundsbook_struct.MainActivity.uiOptions;

public class Nums_slider extends FragmentActivity {
    private static final int NUM_PAGES = 2;

    ViewPager mPager;//view type

    PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        mPager = (ViewPager) findViewById(R.id.sl_pager);
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);//in import
        findViewById(R.id.alph_s_prev).setPressed(true);


        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());//создание адаптера для фрагмента

        mPager.setAdapter(mPagerAdapter);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    findViewById(R.id.alph_s_prev).setPressed(true);
                    findViewById(R.id.alph_s_next).setPressed(false);
                    Log.d("SLIDER","0");
                }
                if(position==1)
                {
                    findViewById(R.id.alph_s_prev).setPressed(false);
                    findViewById(R.id.alph_s_next).setPressed(true);
                    Log.d("SLIDER","1");

                }
                invalidateOptionsMenu();
            }
        });
        findViewById(R.id.alph_s_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go_back_act = new Intent(Nums_slider.this,Menu.class);
                startActivity(go_back_act);
                finish();
            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return Nums_slider_fragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //SaveAll();
        Log.d("SLIDER","pause");
        BackgroundSoundService.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //SaveAll();
        Log.d("SLIDER","resume");
            findViewById(R.id.alph_s_prev).setPressed(true);
            findViewById(R.id.alph_s_next).setPressed(false);
        BackgroundSoundService.start(this, R.raw.wordapp);
    }
}