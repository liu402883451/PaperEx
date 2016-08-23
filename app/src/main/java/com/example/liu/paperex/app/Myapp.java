package com.example.liu.paperex.app;

import android.app.Application;
import org.xutils.x;
/**
 * Created by liu on 16/8/23.
 */
public class Myapp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
