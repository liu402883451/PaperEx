package com.example.liu.paperex.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.liu.paperex.R;
import com.example.liu.paperex.adapter.CommonAdapter;
import com.example.liu.paperex.adapter.ViewHolder;
import com.example.liu.paperex.bean.PagerBean;
import com.example.liu.paperex.task.MyTextTask;
import com.example.liu.paperex.url.URL;
import com.example.liu.paperex.utils.ImageCacheUtils;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

public class PagerSecondActivity extends AppCompatActivity {

    private String url;
    private String subject;
    private TextView mTvPagerTitle;
    private ArrayList<PagerBean.DataBean.PicListBean> list = new ArrayList<>();
    private CommonAdapter adapter;
    PullToRefreshGridView mPullGv;
    private String topicSubject;
    private String topicIDs;
    private String[] strs;

    private void assignViews() {
        mTvPagerTitle = (TextView) findViewById(R.id.tv_pager_title);
        mTvPagerTitle.setText(subject);
        mPullGv = (PullToRefreshGridView) findViewById(R.id.pager_gl_mostnew);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_second);
        url = getIntent().getStringExtra("url");
        subject = getIntent().getStringExtra("subject");
        try{
            topicSubject = getIntent().getStringExtra("topicSubject");
            topicIDs = getIntent().getStringExtra("topicIDs");
            if (topicSubject!=null && topicIDs != null) {
                subject = topicSubject;
                url = URL.getPagerURL(topicIDs);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        assignViews();

        initData();
        initAdapter();

        mPullGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(PagerSecondActivity.this, BigPicActivity.class);
                strs = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    strs[i] = list.get(i).getWallPaperBig();
                }
                intent.putExtra("strs",strs);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    private void initAdapter() {
        adapter = new CommonAdapter(this, list, R.layout.mostnew_item, new CommonAdapter.SetDataShow<PagerBean.DataBean.PicListBean>() {

            @Override
            public void setData(ViewHolder holder, final PagerBean.DataBean.PicListBean info, View convertView) {
                ImageView iv = holder.getKongJian(convertView, R.id.imageView);
                //使用ImageCacheUtils
                ImageCacheUtils utils = ImageCacheUtils.getInstance();
                //从缓存获取图片
                Bitmap bitmap = utils.getBitmapFromCache(info.getWallPaperMiddle(),false);

                if (bitmap != null) {
                    //让imageview显示图片
                    iv.setImageBitmap(bitmap);
                } else {
                    /**
                     * 联网下载
                     * String url, 下载地址
                     * final TextView tv, 需要显示到的控件
                     * boolean isRound,是否圆角
                     * final boolean isToLocal,是否存到本地
                     */
                    utils.loadImage(info.getWallPaperMiddle(), iv, false, true);
                }
            }
        });
        mPullGv.setAdapter(adapter);
    }

    private void initData() {
        new MyTextTask<>(new MyTextTask.TextDataCallback<PagerBean>() {
            @Override
            public void getTextData(PagerBean bean) {
                List<PagerBean.DataBean.PicListBean> mList = bean.getData().getPic_List();
                list.addAll(mList);
                adapter.notifyDataSetChanged();
            }
        }, PagerBean.class).execute(url);
    }
}
