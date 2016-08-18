package com.example.liu.paperex.ui;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.liu.paperex.R;
import com.example.liu.paperex.fragments.MoreFragment;
import com.example.liu.paperex.fragments.RecommendFragment;
import com.example.liu.paperex.fragments.SearchFragment;
import com.example.liu.paperex.fragments.SortFragment;
import java.util.ArrayList;

/**
 * 系统首页
 * 主要实现radiogroup点击切换fragment页面
 */
public class IndexActivity extends AppCompatActivity {
    /*
     * 通过show和hide方法实现
     * 主页面切换效果
     */
    FragmentManager manager;
    RadioGroup group;
    ArrayList<Fragment> frags = new ArrayList<>();
    RadioButton lastRadio;//记录上一次点击的RadioButton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        initView();
        initFrags();

        manager = getSupportFragmentManager();

        //添加默认显示的Fragment对象
        manager.beginTransaction().add(R.id.index_content, frags.get(0)).commit();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton select = (RadioButton) group.findViewById(checkedId);
                int num = Integer.parseInt(select.getTag().toString());
                /*
                 * isAdded方法的作用：
				 * 判断当前的fragment对象是否已经被添加到屏幕上
				 * */
                if (!frags.get(num).isAdded()) {
                    manager.beginTransaction().add(R.id.index_content, frags.get(num)).commit();
                }
                for (int i = 0; i < frags.size(); i++) {
                    if (i == num) {
                        manager.beginTransaction().show(frags.get(i)).commit();
                    } else {
                        manager.beginTransaction().hide(frags.get(i)).commit();
                    }
                }

                //上一个选中项回到初始效果
                if (lastRadio != null) {
                    lastRadio.setTextColor(Color.rgb(0x82,0x82,0x82));
                }

                lastRadio = select;//记录上次选择的RadioButton赋值为本次选中的RadioButton
            }
        });
    }

    private void initFrags() {
        frags.add(new RecommendFragment());//推荐页面
        frags.add(new SortFragment());//分类页面
        frags.add(new SearchFragment());//搜索页面
        frags.add(new MoreFragment());//更多页面
    }

    private void initView() {
        group = (RadioGroup) findViewById(R.id.radioGroup1);
    }

    /**
     * 双击退出程序
     */
    private long mPressedTime = 0;
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            Toast.makeText(this, "再按一次退出壁纸精选", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }
}
