package com.example.liu.paperex.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 滚动广告适配器
 */
public class MyTopicsPagerAdapter extends PagerAdapter {

    private ArrayList<LinearLayout> pagerLayoutList = new ArrayList<>();

    public MyTopicsPagerAdapter(ArrayList<LinearLayout> pagerLayoutList) {
        this.pagerLayoutList = pagerLayoutList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pagerLayoutList.get(position % pagerLayoutList.size()));
        return pagerLayoutList.get(position % pagerLayoutList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagerLayoutList.get(position % pagerLayoutList.size()));
    }
}
