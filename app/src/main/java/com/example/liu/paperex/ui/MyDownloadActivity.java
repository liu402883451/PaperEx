package com.example.liu.paperex.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.SimpleCommonAdapter;
import com.example.liu.paperex.bean.CollectionBean;
import com.example.liu.paperex.bean.DbBean;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDownloadActivity extends AppCompatActivity {

    private GridView mCollectionGv;
    private BaseAdapter adapter;
    private DbManager manager;
    private List<String> urls = new ArrayList<>();
    private void assignViews() {
        mCollectionGv = (GridView) findViewById(R.id.download_gv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        assignViews();
        initDb();
        initData();

        initAdapter();

        mCollectionGv.setAdapter(adapter);
    }

    private void initDb() {
        //初始化DbManager对象
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/picks"))
                .setDbName("paper.db")
                .setDbVersion(1);
        manager = x.getDb(config);
    }

    private void initAdapter() {
        adapter = new MyAdapter();
    }

    private void initData() {
        try {
            List<DbBean> list = manager.selector(DbBean.class).findAll();
            for (int i = 0; i < list.size(); i++) {
                urls.add(list.get(i).getUrl());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int i) {
            return urls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder holder;
            if (convertView == null){
                convertView = LayoutInflater.from(MyDownloadActivity.this).inflate(R.layout.mostnew_item,viewGroup,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            x.image().bind(holder.iv,urls.get(i));
            return convertView;
        }

        class ViewHolder {
            ImageView iv;
            public ViewHolder(View view){
                iv = (ImageView) view.findViewById(R.id.imageView);
            }
        }
    }
}
