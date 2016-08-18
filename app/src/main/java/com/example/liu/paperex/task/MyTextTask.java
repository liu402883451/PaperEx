package com.example.liu.paperex.task;

import android.os.AsyncTask;

import com.example.liu.paperex.utils.HttpUtils;
import com.google.gson.Gson;
/**
 * Created by liu on 2016/8/8.
 * 处理步骤：
 * 1. 正常写一个获取NewBean类型数据的异步任务类
 * （注：json解析的时候使用Gson进行解析）
 * 2. 在异步任务类和接口类的后方添加<T> ，自定义泛型，可以在后期用于指代任意数据类型
 * 即，用于指代后期new 此异步任务对象时设置的数据类型
 * 3. 将AsynTask中的泛型三修改为T类型，
 * 将原有NewBean的位置通通替换为T
 *
 * 4. 创建一个Class<T>类型的变量，并通过构造方法进行赋值
 * 5. 将此Class<T>类型的变量对象作为fromJson方法的参数2即可
 *
 */
public class MyTextTask<T> extends AsyncTask<String,Void,T>{

    public interface TextDataCallback<T> {
        void getTextData(T bean);
    }

    private TextDataCallback callback;


    private Class<T> cls;
    public MyTextTask(TextDataCallback callback,Class<T> cls) {
        this.callback = callback;
        this.cls = cls;
    }

    @Override
    protected T doInBackground(String... params) {
        //得到下载后的字符串
        String json = HttpUtils.doGetForString(params[0]);
        /**
         * fromJson方法的作用：
         * 1. 解析参数1中指定的json字符串
         * 2. 根据参数2指定的类中的属性名称解析字符串
         * 3. 将解析好的数据封装到参数2所指定的类型的对象中
         * 4. 该方法的返回值就是解析好的数据
         */
        T bean = new Gson().fromJson(json, cls);
        return bean;
    }

    @Override
    protected void onPostExecute(T bean) {
        super.onPostExecute(bean);

        if (callback != null && bean !=null) {
            callback.getTextData(bean);
        }
    }
}
