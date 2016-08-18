package com.example.liu.paperex.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by samsung on 2016/8/9.
 * 自定义的ListView,继承控件,用于搜索界面的列表内容部分
 * 因为用到ScrollView，ListView需要自定义高度
 */
public class MyListView extends ListView{

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * MeasureSpec.AT_MOST 通过此模式获取到的高度值是这是高度最大值
         * 即，内容有多高，当前控件就有多高，但是最高不会超过参数1指定的值
         */
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
