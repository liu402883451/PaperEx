package com.example.liu.paperex.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.SimpleCommonAdapter;
import com.example.liu.paperex.task.MyImageTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by liu on 2016/8/8.
 * 图片下载及缓存处理类
 * <p/>
 * 目标：
 * 实现所有的图片的下载，缓存及取出操作
 * 即到所有的适配器中只需调用取出图片的方法判断缓存中是否有图片
 * 有直接显示，没有调用当前工具类中的下载方法进行下载操作
 */
public class ImageCacheUtils {

    //创建缓存图片的加强版强引用对象
    private LruCache<String, Bitmap> lruCache;
    //创建缓存图片的软引用对象
    private HashMap<String, SoftReference<Bitmap>> map = new HashMap<>();
    private String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picks";
    private BitmapFactory.Options options = new BitmapFactory.Options();
    private Bitmap result;
    //实现目标1： 实现该类的单例模式
    private ImageCacheUtils() {
        initLruCache();
    }

    private static ImageCacheUtils utils;

    public static ImageCacheUtils getInstance() {
        if (utils == null) {
            synchronized (ImageCacheUtils.class) {
                if (utils == null) {
                    utils = new ImageCacheUtils();
                }
            }
        }
        return utils;
    }


    //初始化LruCache对象
    public void initLruCache() {

        lruCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                super.entryRemoved(evicted, key, oldValue, newValue);
                if (evicted) {
                    //当图片被系统自动删除时，将删除的图片存入软引用中
                    map.put(key, new SoftReference<Bitmap>(oldValue));
                }
            }
        };
    }

    //提供下载图片的方法
    public void loadImage(String url, final ImageView iv, boolean isRound, final boolean isToLocal) {
        //根据指定的网址调用异步任务下载图片，并且在图片下载完成之后将图片显示到iv上
        //设置tag标识
        iv.setTag(url);
        //设置下载过程中显示的图片
        iv.setImageResource(R.mipmap.loading);
        new MyImageTask(new MyImageTask.BitmapCallback() {
            @Override
            public void getBitmap(Bitmap result, String url) {
                //图片下载完成后判断当前多次复用的iv上否用要显示本张图片
                if (iv.getTag() != null && iv.getTag().equals(url)) {
                    iv.setImageBitmap(result);
                }
                //将下载好的图片存入缓存中
                lruCache.put(url, result);
                if (isToLocal) {
                    //存本地
                    try {
                        File file = new File(CACHE_PATH, url);
                        //通过得到文件的父文件,判断父文件是否存在
                        File parentFile = file.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }
                        result.compress(Bitmap.CompressFormat.JPEG, 100,
                                new FileOutputStream(file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, isRound).execute(url);
    }

    //提供TextView下载图片的方法
    public void loadImage(String url, final TextView tv, boolean isRound, final boolean isToLocal) {
        //根据指定的网址调用异步任务下载图片，并且在图片下载完成之后将图片显示到iv上
        //设置tag标识
        tv.setTag(url);
        //设置下载过程中显示的图片
        tv.setBackgroundResource(R.mipmap.loading);
        new MyImageTask(new MyImageTask.BitmapCallback() {
            @Override
            public void getBitmap(Bitmap result, String url) {
                //图片下载完成后判断当前多次复用的iv上否用要显示本张图片
                if (tv.getTag() != null && tv.getTag().equals(url)) {
                    tv.setBackground(new BitmapDrawable(result));
                }
                //将下载好的图片存入缓存中
                lruCache.put(url, result);
                if (isToLocal) {
                    //存本地
                    try {
                        File file = new File(CACHE_PATH, url);
                        //通过得到文件的父文件,判断父文件是否存在
                        File parentFile = file.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        }
                        result.compress(Bitmap.CompressFormat.JPEG, 100,
                                new FileOutputStream(file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, isRound).execute(url);
    }

    //提供取出图片的方法
    public Bitmap getBitmapFromCache(String url, boolean isCompress) {
        //从强引用中取出图片
        result = lruCache.get(url);
        if (result == null) {
            //从map中取出软引用对象
            SoftReference<Bitmap> soft = map.get(url);
            if (soft == null) {
                //从本地获取图片
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                result = BitmapFactory.decodeFile(CACHE_PATH + "/" + url , opt);
            } else {
                //从软引用中取出图片
                result = soft.get();
            }
        }
        return result;
    }
}
