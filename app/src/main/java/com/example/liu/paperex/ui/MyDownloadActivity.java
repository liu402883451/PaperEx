package com.example.liu.paperex.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.SimpleCommonAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MyDownloadActivity extends AppCompatActivity {

    private GridView mCollectionGv;
    private ArrayList<Bitmap> list = new ArrayList<>();
    private SimpleCommonAdapter adapter;
    String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picks/download";

    private void assignViews() {
        mCollectionGv = (GridView) findViewById(R.id.download_gv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        assignViews();
        initData();

        initAdapter();

        mCollectionGv.setAdapter(adapter);
    }

    private void initAdapter() {
        adapter = new SimpleCommonAdapter<Bitmap>(this,list,R.layout.mostnew_item) {
            @Override
            public void setItemContent(ViewHolder holder, Bitmap bitmap) {
                holder.setImageBitmap(R.id.imageView,bitmap);
            }
        };
    }

    private void initData() {
        File fileDir = new File(DOWNLOAD_PATH);
        File[] files = fileDir.listFiles();
        if (files!=null) {
            InputStream is = null;
            ByteArrayOutputStream bos = null;
            for (int i = 0; i < files.length; i++) {
                try {
                    is = new FileInputStream(files[i].getAbsolutePath());
                    bos = new ByteArrayOutputStream();
                    byte[] array = new byte[1024];
                    int len = -1;
                    while ((len = is.read(array)) != -1) {
                        bos.write(array, 0, len);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bos.toByteArray(),0,bos.size());
                    list.add(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bos.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {

        }
    }


}
