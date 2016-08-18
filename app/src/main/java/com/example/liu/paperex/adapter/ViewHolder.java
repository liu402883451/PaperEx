package com.example.liu.paperex.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liu on 2016/8/8.
 * 万能ViewHolder
 * <p/>
 * 创建了一个通用的ViewHolder类
 * 处理步骤：
 * 1. 先创建一个SparseArray集合，用于存储该类中稍后先显示的控件对象
 * 2. 创建一个getInstance方法，用于获取一个WanViewHolder对象
 * 3. 创建可以通过控件id获取控件对象的方法
 * 4. 创建一个方法用于返回当前行的显示的View对象
 */
public class ViewHolder {


    /**
     * 创建一个变量专门用于存储layoutId的布局中对应的控件对象
     * 可以使用一个HashMap进行存储
     * 其中以控件id作为map的key，以控件对象作为map的value
     * 同时，当key是int类型的时候，可以使用Android中的类： 替代HashMap
     * 优点：效率快，占用内存少
     */
    SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(LayoutInflater inflater, int layoutId, ViewGroup parent) {
        this.mViews = new SparseArray<>();

        mConvertView = inflater.inflate(layoutId, parent, false);

        mConvertView.setTag(this);
    }

    //用于获取当前的ViewHolder对象
    public static ViewHolder getInstance(LayoutInflater inflater, View convertView, int layoutId, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            //没有可重用的，那么就进行初始化操作
            holder = new ViewHolder(inflater,layoutId,parent);
        } else {
            //如果有可重用的，那么就直接进行取出可重用的对象
            holder = (ViewHolder) convertView.getTag();
        }
        return holder;
    }

    //创建一个方法，用于通过指定的id获取到当前ViewHolder中存储的控件对象
    public <T extends View> T getKongJian(View convertView, int viewId) {
        //先看SparseArray中是否有存好控件对象，如果有，直接从集合中取出使用
        T view = (T) mViews.get(viewId);
        //如果没有，那么，就初始化该对象，并且将该对象存入集合中
        //创建集合的目的，为了减少控件的初始化操作
        if (view == null) {
            view = (T) convertView.findViewById(viewId);
            mViews.put(viewId, view);
        }


        return view;
    }

    //用于返回当前行的显示的View对象
    public View getConvertView() {
        return mConvertView;
    }
}
