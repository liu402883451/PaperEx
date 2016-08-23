package com.example.liu.paperex.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liu.paperex.R;
import com.example.liu.paperex.bean.DbBean;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MyDownloadActivity extends AppCompatActivity {

    private GridView mCollectionGv;
    private BaseAdapter adapter;
    private DbManager manager;
    private List<String> urls = new ArrayList<>();
    private ImageOptions opts;
    private String[] strs;

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
        //长按item项时删除下载图片的监听事件
        deleteDownload();
        //点击item项跳转到大图界面的监听事件
        toBigActivity();
    }

    private void toBigActivity() {
        mCollectionGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), BigPicActivity.class);
                strs = new String[urls.size()];
                for (int i = 0; i < urls.size(); i++) {
                    strs[i] = urls.get(i);
                }
                intent.putExtra("strs",strs);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    private void deleteDownload() {
        mCollectionGv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder dialogButton = new AlertDialog.Builder(MyDownloadActivity.this);
                dialogButton.setTitle("删除图片");
                dialogButton.setMessage("确定要删除吗？");
                dialogButton.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String url = urls.get(i);
                            urls.remove(url);
                            adapter.notifyDataSetChanged();
                            manager.delete(DbBean.class, WhereBuilder.b("url", "=", url));
                            Toast.makeText(MyDownloadActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialogButton.setNegativeButton("取消", new DialogInterface.OnClickListener() {// 添加取消按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialogButton.show();
                return false;
            }
        });
    }

    private void initDb() {
        //初始化DbManager对象
        DbManager.DaoConfig config = new DbManager.DaoConfig()
                .setDbDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/picks"))
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
            if(list != null && list.size() > 0){
                for (int i = 0; i < list.size(); i++) {
                    urls.add(list.get(i).getUrl());
                }
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
            if (convertView == null) {
                convertView = LayoutInflater.from(MyDownloadActivity.this).inflate(R.layout.mostnew_item, viewGroup, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            opts = new ImageOptions.Builder()
                    .build();
            x.image().bind(holder.iv,urls.get(i),opts);
            return convertView;
        }

        class ViewHolder {
            ImageView iv;

            public ViewHolder(View view) {
                iv = (ImageView) view.findViewById(R.id.imageView);
            }
        }
    }
}
