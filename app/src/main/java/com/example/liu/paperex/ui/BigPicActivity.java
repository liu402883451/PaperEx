package com.example.liu.paperex.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.liu.paperex.R;
import com.example.liu.paperex.bean.CollectionBean;
import com.example.liu.paperex.bean.DbBean;
import com.example.liu.paperex.utils.BitmapUtils;
import com.example.liu.paperex.utils.ImageCacheUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BigPicActivity extends AppCompatActivity {
    private ViewPager pager;
    private ImageView mBack;//回退键
    private ImageView mShareSearch;//第三方分享
    private Bitmap bitmap;
    private LinearLayout mLayoutTop;
    private LinearLayout mLayoutBottom;
    private int count = 0;//记录点击次数，初始值为0
    private boolean isShow = (count % 2 == 0) ? true : false;//偶数时显示，奇数不显示
    Handler handler = new Handler();
    Runnable r;
    private ArrayList<ImageView> list = new ArrayList<>();//ViewPager中ImageView的集合
    final ImageCacheUtils utils = ImageCacheUtils.getInstance();//图片缓存及下载类
    private MyAdapter adapter;
    private int position;//上个页面传过来的pager的position位置
    private static int pagerPosition;//本页面选中pager的position位置，滑动时改变
    private String[] strs;//上个页面传过来的所有图片的url数组
    private BitmapUtils bu = new BitmapUtils();//图像处理类
    private DbManager manager;

    private void assignViews() {
        pager = (ViewPager) findViewById(R.id.iv_big_pic);
        mBack = (ImageView) findViewById(R.id.back);
        mShareSearch = (ImageView) findViewById(R.id.share_search);
        mLayoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        mLayoutTop = (LinearLayout) findViewById(R.id.layout_top);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_pic);

        assignViews();

        strs = getIntent().getStringArrayExtra("strs");
        position = getIntent().getIntExtra("position", 0);
        pagerPosition = position;//pagerPosition初始位置为position

        initData(strs);
        initDb();
        adapter = new MyAdapter();
        pager.setAdapter(adapter);
        setListener(strs);

        pager.setCurrentItem(position);
        //从缓存中取图片
        bitmap = utils.getBitmapFromCache(strs[position], false);
        if (bitmap != null) {
            list.get(position).setImageBitmap(bitmap);
        } else {
            //缓存中没有联网下载
            utils.loadImage(strs[position], list.get(position), false, true);
        }

        /**
         * 十秒后上下的悬窗消失
         */
        r = new Runnable() {
            @Override
            public void run() {
                mLayoutBottom.setVisibility(View.GONE);
                mLayoutTop.setVisibility(View.GONE);
                count++;
                if (isShow) {
                    handler.postDelayed(this, 10000);
                }
            }
        };
        /**
         * 如果当前上下按钮是可见状态，10秒后消失
         */
        if (isShow) {
            handler.postDelayed(r, 10000);
        }

        /**
         * 回退键监听
         */
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initDb() {
            //初始化DbManager对象
            DbManager.DaoConfig config = new DbManager.DaoConfig()
                    .setDbDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/picks"))
                    .setDbName("paper.db")
                    .setDbVersion(1);
            manager = x.getDb(config);
    }

    /**
     * viewPager的监听事件
     * @param strs  所有图片网址的数组
     */
    private void setListener(final String[] strs) {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pagerPosition, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * Page选中时触发的事件：主要是设置图片显示
             * @param pagerPosition 当前viewPager的位置
             */
            @Override
            public void onPageSelected(int pagerPosition) {
                bitmap = utils.getBitmapFromCache(strs[pagerPosition], false);
                if (bitmap != null) {
                    //ViewPager中ImageView设置bitmap图片
                    list.get(pagerPosition).setImageBitmap(bitmap);
                } else {
                    /**
                     * 联网下载图片并在imageView控件显示
                     * 1,url网址
                     * 2,imageView对象
                     * 3,是否圆角
                     * 4,是否存本地
                     */
                    utils.loadImage(strs[pagerPosition], list.get(pagerPosition), false, true);
                }
                //把当前选中pager的position位置赋值为当前位置
                BigPicActivity.pagerPosition = pagerPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch(state){
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (bitmap != null && !bitmap.isRecycled()) {
                            bitmap.recycle();
                        }
                        break;
                }
            }
        });
    }

    /**
     * 初始化数据源
     * @param strs  页面所有的图片的网址
     */
    private void initData(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            final ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(R.mipmap.loading);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count % 2 == 0) {
                        /**
                         * 设置上下的功能按钮不可见
                         */
                        mLayoutBottom.setVisibility(View.GONE);
                        mLayoutTop.setVisibility(View.GONE);
                    } else {
                        /**
                         * 设置上下的功能按钮可见
                         */
                        mLayoutBottom.setVisibility(View.VISIBLE);
                        mLayoutTop.setVisibility(View.VISIBLE);
                    }
                    count++;
                }
            });
            list.add(iv);//把imageView对象添加到集合中
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            //文件下载：图片存入本地sd卡/picks/download文件夹下
            case R.id.btn_download:
                String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picks/download";
                try {
                    String fileName = strs[pagerPosition].substring(strs[pagerPosition].lastIndexOf('/'));
//                    Log.i("liu---", "click: "+fileName);
                    File file=  new File(CACHE_PATH,fileName);
                    //通过得到文件的父文件,判断父文件是否存在
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()){
                        parentFile.mkdirs();
                    }
                    bitmap = utils.getBitmapFromCache(strs[pagerPosition], false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG , 100 ,
                            new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //将图片网址存储到数据库，用于“我的下载”的大图功能
                String url = strs[pagerPosition];
                try {
                    List<DbBean> beans = manager.selector(DbBean.class).where("url","=",url).findAll();
                    if (beans == null || beans.size() ==0){
                        manager.save(new DbBean(url));
                        SystemClock.sleep(500);
                        Toast.makeText(BigPicActivity.this, "下载成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BigPicActivity.this, "您已经下载过,请不要重复下载", Toast.LENGTH_SHORT).show();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            //设为壁纸
            case R.id.btn_set_wallpager:
                try {
                    bitmap = utils.getBitmapFromCache(strs[pagerPosition], false);
                    Bitmap b = bu.getScaleBit(bitmap,1080,1920);
                    setWallpaper(b);
                    SystemClock.sleep(500);
                    Toast.makeText(BigPicActivity.this, "设置壁纸成功！", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_collection:
                //收藏功能：图片存入本地sd卡/picks/colection文件夹下
                String COLECTION_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picks/colection";
                try {
                    String fileName = strs[pagerPosition].substring(strs[pagerPosition].lastIndexOf('/'));
                    File file=  new File(COLECTION_PATH,fileName);
                    //通过得到文件的父文件,判断父文件是否存在
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()){
                        parentFile.mkdirs();
                    }
                    bitmap = utils.getBitmapFromCache(strs[pagerPosition], false);
                    bitmap.compress(Bitmap.CompressFormat.JPEG , 100 ,
                            new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                // 将图片网址存储到数据库，用于“我的收藏”的大图功能
                String url1 = strs[pagerPosition];
                try {
                    List<CollectionBean> beans = manager.selector(CollectionBean.class).where("url","=",url1).findAll();
                    if (beans == null || beans.size() ==0){
                        manager.save(new CollectionBean(url1));
                        Toast.makeText(BigPicActivity.this, "收藏成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(BigPicActivity.this, "您已经收藏过,请不要重复收藏", Toast.LENGTH_SHORT).show();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.share_search:
                // TODO: 16/8/15 分享
                break;
        }
    }

    /**
     * viewPager适配器
     */
    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            container.removeView((View) object);
        }
    }
}
