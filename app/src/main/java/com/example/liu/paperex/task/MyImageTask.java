package com.example.liu.paperex.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.example.liu.paperex.utils.BitmapUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by liu on 2016/8/8.
 */
public class MyImageTask extends AsyncTask<String, Void, Bitmap> {

    private BitmapUtils bitmapUtils = new BitmapUtils();
    private BitmapFactory.Options options = new BitmapFactory.Options();

    public interface BitmapCallback {
        void getBitmap(Bitmap result, String url);
    }

    private BitmapCallback callback;
    private String url;
    private boolean isRound;

    public MyImageTask(BitmapCallback callback, boolean isRound) {
        this.callback = callback;
        this.isRound = isRound;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            url = params[0];
            HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
            conn.setConnectTimeout(5000000);
            conn.setRequestMethod("GET");

            conn.connect();
            if (conn.getResponseCode() == 200) {
                if (params.length == 1) {
                    return BitmapFactory.decodeStream(conn.getInputStream());
                } else {
                    options.inJustDecodeBounds = false;
                    options.inSampleSize = 2;   //width，hight设为原来的2分一
                    return BitmapFactory.decodeStream(conn.getInputStream(), null, options);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (callback != null && bitmap != null) {
            if (isRound) {
                Bitmap roundBitmap = bitmapUtils.getRoundBitmap(bitmap, 20);
                callback.getBitmap(roundBitmap, url);
            } else {
                callback.getBitmap(bitmap, url);
            }
        }
    }
}
