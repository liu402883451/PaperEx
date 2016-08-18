package com.example.liu.paperex.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.CommonAdapter;
import com.example.liu.paperex.adapter.MyTopicsPagerAdapter;
import com.example.liu.paperex.adapter.ViewHolder;
import com.example.liu.paperex.bean.HotSearchBean;
import com.example.liu.paperex.bean.TopicsBean;
import com.example.liu.paperex.bean.TypeListBean;
import com.example.liu.paperex.task.MyImageTask;
import com.example.liu.paperex.task.MyTextTask;
import com.example.liu.paperex.ui.BigPicActivity;
import com.example.liu.paperex.ui.PagerSecondActivity;
import com.example.liu.paperex.ui.SearchHotSecondActivity;
import com.example.liu.paperex.ui.SearchListSecondActivity;
import com.example.liu.paperex.ui.SeeMoreActivity;
import com.example.liu.paperex.url.URL;
import com.example.liu.paperex.utils.BitmapUtils;
import com.example.liu.paperex.utils.ImageCacheUtils;
import com.example.liu.paperex.view.MyListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

/**
 * Created by liu on 16/8/8.
 * 搜索界面
 */
public class SearchFragment extends Fragment {
    //滚动广告ViewPager
    private ViewPager mPager;
    //主要列表内容MyListView
    private MyListView mLv;
    //热门搜索GridView
    private GridView mGv;
    //图像处理工具类，此处用于圆角处理
    private BitmapUtils bitmapUtils = new BitmapUtils();
    //存放滚动广告的图片的ImageView集合
    private ArrayList<ImageView> pagerList = new ArrayList<>();
    //横向的线性布局，用于viewpager滚动广告时存放两张图片，即2个ImageView
    private ArrayList<LinearLayout> pagerLayoutList = new ArrayList<>();
    //滚动广告适配器
    private MyTopicsPagerAdapter pagerAdapter;
    //热门搜索适配器
    private CommonAdapter adapter;
    //主要列表内容适配器
    private CommonAdapter typeAdapter;
    //热门搜索数据源
    private ArrayList<HotSearchBean.DataBean> list = new ArrayList<>();
    //滚动广告数据源
    private ArrayList<TopicsBean.DataBean.TopicBean> topicList = new ArrayList<>();
    //主要列表内容数据源
    private ArrayList<TypeListBean.DataBean> typeList = new ArrayList<>();
    //滚动广告切换小点
    private RadioGroup group;
    //滚动广告自动切换Handler
    private android.os.Handler handler = new android.os.Handler();
    //滚动广告自动切换Runnable
    private Runnable r;
    //滚动广告自动切换旗帜变量:判断是否启动Handler
    private boolean flag;
    //ViewPager主题的id
    private String topicID;
    //ViewPager主题的标题
    private String subject;
    //ViewPager查看更多
    private TextView see_more;

    /**
     * 查看更多所有专题属性数组
     */
    private String[] topicImgs;
    private String[] topicSubject;
    private String[] topicIDs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater);

        initGvData();
        initGvAdapter();
        setGridViewListener();
        initPagerData();
        initLvData();
        initLvAdapter();
        setListListener();
        mLv.setFocusable(false);//列表失去焦点

        /**
         * 查看更多的点击事件
         */
        see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SeeMoreActivity.class);
                intent.putExtra("topicImgs",topicImgs);
                intent.putExtra("topicSubject",topicSubject);
                intent.putExtra("topicIDs",topicIDs);
                startActivity(intent);
            }
        });
        return view;
    }

    private void setGridViewListener() {
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyWord = list.get(i).getKeyword();
                String url = URL.getListURL(keyWord);
                Intent intent = new Intent(getActivity(), SearchHotSecondActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("keyWord",keyWord);

                startActivity(intent);
            }
        });
    }

    private void setListListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String keyWord = typeList.get(i).getKeyword();
                String url = URL.getListURL(keyWord);
                Intent intent = new Intent(getActivity(), SearchListSecondActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("keyWord",keyWord);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化控件
     */
    @NonNull
    private View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        mGv = (GridView) view.findViewById(R.id.gv);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mLv = (MyListView) view.findViewById(R.id.lv);
        group = (RadioGroup) view.findViewById(R.id.group);
        see_more = (TextView)view.findViewById(R.id.see_more);
        return view;
    }

    /**
     * 初始化热门搜索适配器
     */
    private void initGvAdapter() {
        adapter = new CommonAdapter(getActivity().getApplicationContext(), list, R.layout.hot_search_item, new CommonAdapter.SetDataShow<HotSearchBean.DataBean>() {

            @Override
            public void setData(ViewHolder holder, HotSearchBean.DataBean dataBean, View convertView) {
                TextView tv = holder.getKongJian(convertView, R.id.hot_search_tv);
                tv.setText(dataBean.getKeyword());
                //使用ImageCacheUtils
                ImageCacheUtils utils = ImageCacheUtils.getInstance();

                //从缓存获取图片
                Bitmap bitmap = utils.getBitmapFromCache(dataBean.getImgs().get(0),false);
                if (bitmap != null) {
                    //让imageview显示图片
                    Bitmap roundBitmap = bitmapUtils.getRoundBitmap(bitmap, 20);
                    tv.setBackground(new BitmapDrawable(roundBitmap));
                } else {
                    /**
                     * 联网下载
                     * String url, 下载地址
                     * final TextView tv, 需要显示到的控件
                     * boolean isRound,是否圆角
                     * final boolean isToLocal,是否存到本地
                     */
                    utils.loadImage(dataBean.getImgs().get(0), tv, true, true);
                }
            }
        });
        mGv.setAdapter(adapter);
    }

    /**
     * 初始化热门搜索数据源
     */
    private void initGvData() {
        new MyTextTask<>(new MyTextTask.TextDataCallback<HotSearchBean>() {
            @Override
            public void getTextData(HotSearchBean bean) {
                list.addAll(bean.getData());
                adapter.notifyDataSetChanged();
            }
        }, HotSearchBean.class).execute(URL.SEARCH_GV_URL);
    }

    /**
     * 初始滚动广告数据源及其适配器
     */
    private void initPagerData() {
        new MyTextTask<>(new MyTextTask.TextDataCallback<TopicsBean>() {
            @Override
            public void getTextData(TopicsBean bean) {
                pagerAdapter = new MyTopicsPagerAdapter(pagerLayoutList);

                topicList.addAll(bean.getData().getTopic());
                pagerAdapter.notifyDataSetChanged();
                /**
                 * 创建保存专题属性的数组
                 */
                topicImgs = new String[topicList.size()];
                topicSubject = new String[topicList.size()];
                topicIDs = new String[topicList.size()];
                /**
                 * 下载ViewPager需要显示的图片
                 */
                for (int i = 0; i < topicList.size(); i++) {
                    /**
                     * 保存专题的属性，用于查看更多
                     */
                    topicImgs[i] = topicList.get(i).getCover_path();//图片地址
                    topicSubject[i] = topicList.get(i).getSubject();//大标题
                    topicIDs[i] = topicList.get(i).getId();//标题类型ID

                    final ImageView iv = new ImageView(getActivity().getApplicationContext());
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    iv.setImageResource(R.mipmap.loading);
                    iv.setTag(i);
                    //设置imageview点击事件
                    iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            topicID = topicList.get(Integer.parseInt(iv.getTag().toString())).getId();
                            subject = topicList.get(Integer.parseInt(iv.getTag().toString())).getSubject();
                            String url = URL.getPagerURL(topicID);
                            Intent intent = new Intent(getActivity(), PagerSecondActivity.class);
                            intent.putExtra("url",url);
                            intent.putExtra("subject",subject);
                            startActivity(intent);
                        }
                    });
                    //设置图片宽高：其中LayoutParams必须是imageView的父布局的LayoutParams
                    iv.setLayoutParams(new LinearLayout.LayoutParams(
                            getActivity().getApplicationContext().getResources().getDisplayMetrics().widthPixels / 2 - 30, LinearLayout.LayoutParams.WRAP_CONTENT));
                    pagerList.add(iv);
                    final int finalI = i;
                    new MyImageTask(new MyImageTask.BitmapCallback() {
                        @Override
                        public void getBitmap(Bitmap result, String url) {
                            pagerList.get(finalI).setImageBitmap(result);
                        }
                    }, true).execute(topicList.get(i).getCover_path());
                }

                /**
                 * 2张图片存入一个LinearLayout，使其每次滚动显示2张图片
                 * ViewPager滚动时使用LinearLayout滚动
                 * 并初始化RadioGroup
                 */
                for (int i = 0; i < pagerList.size() / 2; i++) {
                    LinearLayout layout = new LinearLayout(getActivity().getApplicationContext());
                    RadioButton rb = new RadioButton(getActivity().getApplicationContext());
                    rb.setClickable(false);
                    group.addView(rb);
                    pagerLayoutList.add(layout);
                    pagerLayoutList.get(i).addView(pagerList.get(i * 2), 0);
                    pagerLayoutList.get(i).addView(pagerList.get(i * 2 + 1), 1);
                }
                //默认选中第一个单选按钮
                group.check(group.getChildAt(0).getId());
                mPager.setAdapter(pagerAdapter);

                //为了一开始就支持向右滑动，选中position为2000的Item
                mPager.setCurrentItem(2000);

                r = new Runnable() {
                    @Override
                    public void run() {
                        //ViewPager选中下一项
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                        handler.postDelayed(this, 3000);
                    }
                };
                handler.postDelayed(r, 3000);

                //设置监听
                setPagerListener();

            }
        }, TopicsBean.class).execute(URL.SEARCH_VIEWPAGER_URL);
    }

    /**
     * 设置ViewPager滑动监听
     */
    private void setPagerListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                RadioButton rb = (RadioButton) group.getChildAt(position % pagerLayoutList.size());
                group.check(rb.getId());//选中position位置的RadioButton
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING://手拖动不放开时触发
                        handler.removeCallbacks(r);
                        flag = true;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE://停止滑动时触发
                        if (flag) {
                            handler.postDelayed(r, 2000);
                            flag = false;
                        }
                        break;
                }
            }
        });
    }


    /**
     * 初始化列表内容适配器
     */
    private void initLvAdapter() {
        typeAdapter = new CommonAdapter(getActivity().getApplicationContext(), typeList, R.layout.type_list_item, new CommonAdapter.SetDataShow<TypeListBean.DataBean>() {
            @Override
            public void setData(ViewHolder holder, final TypeListBean.DataBean dataBean, View convertView) {
                TextView tv_title = holder.getKongJian(convertView, R.id.tv_title);
                tv_title.setText(dataBean.getKeyword());

                ImageView iv1 = holder.getKongJian(convertView, R.id.type1);
                ImageView iv2 = holder.getKongJian(convertView, R.id.type2);
                ImageView iv3 = holder.getKongJian(convertView, R.id.type3);
                //使用ImageCacheUtils
                ImageCacheUtils utils = ImageCacheUtils.getInstance();

                //从缓存或者本地取出图片
                Bitmap bitmap1 = utils.getBitmapFromCache(dataBean.getImgs().get(0),false);
                Bitmap bitmap2 = utils.getBitmapFromCache(dataBean.getImgs().get(1),false);
                Bitmap bitmap3 = utils.getBitmapFromCache(dataBean.getImgs().get(2),false);
                if (bitmap1 != null && bitmap2 != null && bitmap3 != null) {
                    //如果缓存或者本地有，就直接设置图片
                    iv1.setImageBitmap(bitmap1);
                    iv2.setImageBitmap(bitmap2);
                    iv3.setImageBitmap(bitmap3);
                } else {
                    /**
                     * 联网下载
                     * String url, 下载地址
                     * final TextView tv, 需要显示到的控件
                     * boolean isRound,是否圆角
                     * final boolean isToLocal,是否存到本地
                     */
                    utils.loadImage(dataBean.getImgs().get(0), iv1, false, true);
                    utils.loadImage(dataBean.getImgs().get(1), iv2, false, true);
                    utils.loadImage(dataBean.getImgs().get(2), iv3, false, true);
                }
            }
        });
        mLv.setAdapter(typeAdapter);
    }

    /**
     * 初始化列表内容数据源
     */
    private void initLvData() {
        new MyTextTask<>(new MyTextTask.TextDataCallback<TypeListBean>() {
            @Override
            public void getTextData(TypeListBean bean) {
                typeList.addAll(bean.getData());
                typeAdapter.notifyDataSetChanged();
            }
        }, TypeListBean.class).execute(URL.SEARCH_LIST_URL);
    }

}
