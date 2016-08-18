package com.example.liu.paperex.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.liu.paperex.ui.CollectionActivity;
import com.example.liu.paperex.R;
import com.example.liu.paperex.ui.MyDownloadActivity;

/**
 * Created by liu on 16/8/8.
 * 首页中更多的界面
 */
public class MoreFragment extends Fragment{
    private Button mMyCollection;
    private Button mMyDownload;
    private Button mShareToFriends;
    private Button mGood;
    private Button mSetting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mMyCollection = (Button) view.findViewById(R.id.my_collection);
        mMyDownload = (Button) view.findViewById(R.id.my_download);
        mShareToFriends = (Button) view.findViewById(R.id.share_to_friends);
        mGood = (Button) view.findViewById(R.id.good);
        mSetting = (Button) view.findViewById(R.id.setting);
        mMyCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CollectionActivity.class);
                startActivity(intent);
            }
        });
        mMyDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), MyDownloadActivity.class);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

}
