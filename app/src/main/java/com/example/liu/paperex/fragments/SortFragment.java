package com.example.liu.paperex.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.CommonAdapter;
import com.example.liu.paperex.adapter.ViewHolder;
import com.example.liu.paperex.bean.Category;
import com.example.liu.paperex.task.MyTextTask;
import com.example.liu.paperex.ui.SortSecondActivity;
import com.example.liu.paperex.url.URL;
import com.example.liu.paperex.utils.BitmapUtils;
import com.example.liu.paperex.utils.ImageCacheUtils;

import java.util.ArrayList;

/**
 * Created by liu on 16/8/8.
 * 分类界面
 */

public class SortFragment extends Fragment {

    private ListView mListview;
    private ArrayList<Category.DataBean> list = new ArrayList<>();
    private CommonAdapter adapter;
    private BitmapUtils bitmapUtils = new BitmapUtils();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sort, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        assignViews(view);
        super.onViewCreated(view, savedInstanceState);

        initData();

        initAdapter();

        setListener();
    }

    private void setListener() {
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = list.get(i).getSecondCategoryList().get(0).getBigCategoryId();
                String picCategoryName = list.get(i).getPicCategoryName();
                String url = URL.getMostnewSort(id);//分类的最新网址
                String hotUrl = URL.getHotSort(id);//分类的热门网址
                String ranUrl = URL.getRandSort(id);//分类的随机网址
                Intent intent = new Intent();
                intent.setClass(getActivity(),SortSecondActivity.class);
                intent.putExtra("url" ,url);
                intent.putExtra("hotUrl" ,hotUrl);
                intent.putExtra("ranUrl" ,ranUrl);
                intent.putExtra("picCategoryName" ,picCategoryName);
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        adapter = new CommonAdapter(getActivity().getApplicationContext(), list, R.layout.categray_item, new CommonAdapter.SetDataShow<Category.DataBean>() {

            @Override
            public void setData(ViewHolder holder, Category.DataBean dataBean, View convertView) {
                TextView tv_top = holder.getKongJian(convertView, R.id.tv_top);
                tv_top.setText(dataBean.getPicCategoryName());

                TextView tv_bottom1 = holder.getKongJian(convertView, R.id.tv_bottom1);
                tv_bottom1.setText(dataBean.getDescWords());

                ImageView iv = holder.getKongJian(convertView, R.id.imageView);

                //使用ImageCacheUtils
                ImageCacheUtils utils = ImageCacheUtils.getInstance();
                //从缓存获取图片
                Bitmap bitmap = utils.getBitmapFromCache(dataBean.getCategoryPic(),false);

                if (bitmap != null) {
                    //让imageview显示图片
                    Bitmap roundBitmap = bitmapUtils.getRoundBitmap(bitmap, 20);
                    iv.setImageBitmap(roundBitmap);
                } else {
                    /**
                     * 联网下载
                     * String url, 下载地址
                     * final TextView tv, 需要显示到的控件
                     * boolean isRound,是否圆角
                     * final boolean isToLocal,是否存到本地
                     */
                    utils.loadImage(dataBean.getCategoryPic(), iv, true ,true);
                }
            }
        });
        mListview.setAdapter(adapter);
    }

    private void initData() {
        new MyTextTask<>(new MyTextTask.TextDataCallback<Category>() {
            @Override
            public void getTextData(Category bean) {
                list.addAll(bean.getData());
                adapter.notifyDataSetChanged();
            }
        }, Category.class).execute(URL.SORT_URL);
    }

    private void assignViews(View view) {
        mListview = (ListView) view.findViewById(R.id.listview);
    }
}
