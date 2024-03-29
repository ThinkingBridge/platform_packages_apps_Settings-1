package com.android.settings;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.util.Log;

public class MainSetting extends Activity {
    private ViewPager mPager;
    private List<View> listViews; // Tab List
    private ImageView cursor;// Animation Image
    private TextView used_tab, personal_tab, system_tab;// tab title
    private int offset = 0;// Animation Image move px
    private int currIndex = 0;// Animation Image Index
    private int bmpW;// Animation Image Width
    private LocalActivityManager localManager;
    private LayoutInflater mInflater;
    private ViewPagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainsetting);
        localManager = new LocalActivityManager(this, true);
        localManager.dispatchCreate(savedInstanceState);

        InitImageView();
        InitTextView();
        InitViewPager();

    }

    // tab title
    private void InitTextView() {
        used_tab = (TextView) findViewById(R.id.used_tab);
        personal_tab = (TextView) findViewById(R.id.personal_tab);
        system_tab = (TextView) findViewById(R.id.system_tab);

        used_tab.setOnClickListener(new MyOnClickListener(0));
        personal_tab.setOnClickListener(new MyOnClickListener(1));
        system_tab.setOnClickListener(new MyOnClickListener(2));
    }

    // tab viewpager
    private void InitViewPager() {
        mInflater = getLayoutInflater();
        mPager = (ViewPager) findViewById(R.id.vPager);
        listViews = new ArrayList<View>();
        Intent UsedSettingsIntent = new Intent(this, UsedSettings.class);
        listViews.add(localManager.startActivity("UsedSettings",
                UsedSettingsIntent).getDecorView());
        Intent PersonalizedSettingsIntent = new Intent(this,
                PersonalizedSettings.class);
        listViews.add(localManager.startActivity("PersonalizedSettings",
                PersonalizedSettingsIntent).getDecorView());
        Intent SystemSettingsIntent = new Intent(this, SystemSettings.class);
        listViews.add(localManager.startActivity("SystemSettings",
                SystemSettingsIntent).getDecorView());
        mPagerAdapter = new ViewPagerAdapter(listViews);
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    // cursor animation
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        // get Image width
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.top_slider)
                .getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // get screen width
        int screenW = dm.widthPixels;
        // get offset move px
        offset = (screenW / 3 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        // init Animation ImageView px
        cursor.setImageMatrix(matrix);
    }

    // viewpager adapter
    public class ViewPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public ViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    public class MyOnPageChangeListener implements OnPageChangeListener {

        // tab1 -> tab2,move px
        int one = offset * 2 + bmpW;
        // tab1 -> tab3,move px
        int two = one * 2;

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:Image stop at Animation end
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}