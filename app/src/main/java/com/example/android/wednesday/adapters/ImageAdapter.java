package com.example.android.wednesday.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.android.wednesday.R;

/**
 * Created by hp pc on 2/19/2017.
 */

public class ImageAdapter extends PagerAdapter {
    /**
     * Variables
     */
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int[] mResources;

    /**
     * *************************************************************************
     * ***
     * <p/>
     * Constructor to initialize the variables
     * <p/>
     * *************************************************************************
     * ***
     */
    public ImageAdapter(Context context) {
        mContext = context;

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    /**
     * Method to initialize the View
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = mLayoutInflater.inflate(
                R.layout.featured_card, container, false);

        final ImageView imageView = (ImageView) itemView
                .findViewById(R.id.event_picture);
//        imageView.setBackgroundResource(mResources[position]);

        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    /**
     * Method to destroy the Child View
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    public void setList(int[] images) {
        mResources = images;
    }

}
