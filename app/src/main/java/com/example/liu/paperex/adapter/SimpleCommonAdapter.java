package com.example.liu.paperex.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liu on 16/8/12.
 */
public abstract class SimpleCommonAdapter<E> extends BaseAdapter{
    private List<E> data; // 数据源
    private Context mContext;
    private int layoutId;

    public SimpleCommonAdapter(Context context, List<E> data, int layoutId) {
        this.mContext = context;
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public E getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getViewHolder(mContext, convertView, layoutId);

        setItemContent(holder, getItem(position));

        return holder.getConvertView();
    }

    /**
     * 设置item中各控件显示的内容
     * @param holder
     * @param e
     */
    public abstract void setItemContent(ViewHolder holder, E e);

    /**
     * 持有者类（内部类）
     * @author Administrator
     *
     */
    public static class ViewHolder {

        private View mConvertView;
        private SparseArray<View> mViews;

        public ViewHolder(Context context, int layoutId){
            this.mConvertView = View.inflate(context, layoutId, null);
            mConvertView.setTag(this);

            mViews = new SparseArray<View>();
        }

        public static ViewHolder getViewHolder(Context context, View convertView, int layoutId){
            if(convertView == null) {
                return new ViewHolder(context, layoutId);
            } else {
                return (ViewHolder)convertView.getTag();
            }
        }

        public <T extends View> T getViewById(int resId) {
            View view = mViews.get(resId);
            if(view == null) {
                view = mConvertView.findViewById(resId);
                mViews.put(resId, view);
            }
            return (T)view;
        }

        public ViewHolder setText(int viewId, CharSequence text) {
            ((TextView)getViewById(viewId)).setText(text);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int imgId) {
            ((ImageView)getViewById(viewId)).setImageResource(imgId);
            return this;
        }

        public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
            ((ImageView)getViewById(viewId)).setImageBitmap(bm);
            return this;
        }

        public View getConvertView(){
            return mConvertView;
        }
    }

}