package com.example.liu.paperex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.liu.paperex.fragments.SortFragment;

import java.util.ArrayList;

/**
 * Created by liu on 2016/8/8.
 * 万能适配器
 * <p>
 * 处理步骤：
 * 1. 在类名后方添加自定义泛型的声明
 * 2. 把所有与数据源的类型相关的位置通通换成泛型T
 * 3. 在getView方法中通过getInstance方法初始化WanViewHolder对象
 * 4. 创建接口回调套路
 * 5. 在getView中，通过接口对象调用抽象方法
 * 6. 在Activity或者Fragment中，在进行适配器的初始化的时候，
 * 传递接口对象，并且在接口对象重写的方法中，获取控件对象，设置显示内容即可
 */
public class CommonAdapter<T> extends BaseAdapter {

    private Context context;
    private ArrayList<T> list;

    private LayoutInflater inflater;
    private int layoutId;

    public interface SetDataShow<T> {
        void setData(ViewHolder holder, T t, View convertView);
    }

    private SetDataShow show;

    public CommonAdapter(Context context, ArrayList list, int layoutId, SetDataShow show) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.show = show;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getInstance(inflater, convertView, layoutId, parent);

        if (show != null) {
            show.setData(holder, list.get(position), holder.getConvertView());
        }

        return holder.getConvertView();
    }


}
