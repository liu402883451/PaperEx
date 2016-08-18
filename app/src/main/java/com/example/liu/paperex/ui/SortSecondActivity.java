package com.example.liu.paperex.ui;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.liu.paperex.R;
import com.example.liu.paperex.fragments.HotFragment;
import com.example.liu.paperex.fragments.MostNewFragment;
import com.example.liu.paperex.fragments.RandomFragment;

import java.util.ArrayList;

public class SortSecondActivity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mPager;
    private ArrayList<Fragment> list = new ArrayList<>();
    private TextView tv_sort_title;
    private FragmentManager manager;
    String url;
    private String picCategoryName;
    private String hotUrl;
    private String ranUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_second);
        url = getIntent().getStringExtra("url");
        hotUrl = getIntent().getStringExtra("hotUrl");
        ranUrl = getIntent().getStringExtra("ranUrl");
        picCategoryName = getIntent().getStringExtra("picCategoryName");
        initView();

        /*
        * 1. 设置没有被选中的标签上的文本颜色
        * 2. 设置选中的标签上的文本颜色
        * */
        mTablayout.setTabTextColors(Color.BLACK, Color.rgb(0x10,0xC0,0x51));
        //设置下划线的颜色
        mTablayout.setSelectedTabIndicatorColor(Color.rgb(0x10,0xC0,0x51));
        /**
         * 设置TabLayout的模式
         * 2种可选：
         * TabLayout.MODE_FIXED  默认效果，所有标签都显示在屏幕内
         * TabLayout.MODE_SCROLLABLE  当标签个数比较多时，可以提供横向滑动功能
         */
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
        /**
         * 设置标签的位置
         * 此方法只有在TabMode为Fixed时有效
         * 2种可选：
         * TabLayout.GRAVITY_FILL  默认效果，标签撑满屏幕宽，
         * TabLayout.GRAVITY_CENTER  标签的总宽度为自适应宽度，所有标签横向居中显示
         */
        mTablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        initViewPager();

        //将TabLayout与Viewpager绑定起来
        //注意:此方法必须在ViewPager对象设置了适配器之后调用
        mTablayout.setupWithViewPager(mPager);
    }

    private void initView() {
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mPager = (ViewPager) findViewById(R.id.pager);
        tv_sort_title = (TextView) findViewById(R.id.tv_sort_title);
        tv_sort_title.setText(picCategoryName);
    }

    private void initViewPager() {
        list.add(new MostNewFragment(true,url));
        list.add(new HotFragment(true,hotUrl));
        list.add(new RandomFragment(true,ranUrl));
        mPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        //此方法的返回值可以决定TabLayout中每个标签上显示的文字
        @Override
        public CharSequence getPageTitle(int position) {
            String s = "";
            switch(position){
                case 0:
                    s = "最新";
                    break;
                case 1:
                    s = "热门";
                    break;
                case 2:
                    s = "随机";
                    break;
            }
            return s;
        }
    }

}
