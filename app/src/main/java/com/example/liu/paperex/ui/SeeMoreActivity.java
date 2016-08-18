package com.example.liu.paperex.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.SimpleCommonAdapter;
import com.example.liu.paperex.task.MyImageTask;
import java.util.ArrayList;

public class SeeMoreActivity extends AppCompatActivity {

    private ListView mListviewTopic;
    private ArrayList<Bitmap> list = new ArrayList<>();
    private SimpleCommonAdapter adapter;
    private String[] topicImgs;//主题图片的网址
    private String[] topicSubject;//主题的标题
    private String[] topicIDs;//主题的类型id

    private void assignViews() {
        mListviewTopic = (ListView) findViewById(R.id.listview_topic);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        topicImgs = getIntent().getStringArrayExtra("topicImgs");
        topicSubject = getIntent().getStringArrayExtra("topicSubject");
        topicIDs = getIntent().getStringArrayExtra("topicIDs");

        assignViews();

        initData();

        initAdapter();

        mListviewTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SeeMoreActivity.this, PagerSecondActivity.class);
                intent.putExtra("topicSubject", topicSubject[i]);
                intent.putExtra("topicIDs", topicIDs[i]);
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        adapter = new SimpleCommonAdapter<Bitmap>(this, list, R.layout.see_more_item) {
            @Override
            public void setItemContent(ViewHolder holder, Bitmap bitmap) {
                holder.setImageBitmap(R.id.see_more_imageView, bitmap);
            }
        };
        mListviewTopic.setAdapter(adapter);
    }

    private void initData() {
        for (int i = 0; i < topicImgs.length; i++) {
            //下载图片并添加到list集合中
            new MyImageTask(new MyImageTask.BitmapCallback() {
                @Override
                public void getBitmap(Bitmap result, String url) {
                    list.add(result);
                    adapter.notifyDataSetChanged();
                }
            }, false).execute(topicImgs[i]);
        }
    }

}
