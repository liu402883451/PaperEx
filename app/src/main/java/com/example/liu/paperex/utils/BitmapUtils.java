package com.example.liu.paperex.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * Created by liu on 2016/5/23.
 * 图片处理的工具类
 */
public class BitmapUtils {

    //缩放方法
    public Bitmap getScaleBit (Bitmap source,int dstWidth,int dstHeight){
         /*
                * 1. 原始图片
                * 2,3. 缩放后图片的目标宽高
                * 4. true尽可能保持图片清晰度，缩放效果会比较好
                * */
        return Bitmap.createScaledBitmap(source,dstWidth,dstHeight,true);
    }

    //压缩图片
    public Bitmap getCompressBit(Context context,int resId, int dstW, int dstH){

        BitmapFactory.Options opts = new BitmapFactory.Options();
        //指定在稍后通过decode方法加载图片时，不加载图片具体内容，只图片的原始宽高信息
        opts.inJustDecodeBounds = true;
        //此处的作用，是为了将图片的宽高存入opts对象
        BitmapFactory.decodeResource(context.getResources(),resId,opts);
        //获取图片原始宽高
        int bitW = opts.outWidth;
        int bitH = opts.outHeight;

        if (bitW >= dstW && bitH >= dstH) {
            //可以去压缩图片
            opts.inJustDecodeBounds = false;

            int scaleW = bitW / dstW;
            int scaleH = bitH / dstH;
            //指定图片的压缩倍数
            opts.inSampleSize = scaleW > scaleH ? scaleW:scaleH;

            return BitmapFactory.decodeResource(context.getResources(),resId,opts);
        }

        return null;
    }

    //给图片加圆角
    /*
    * 处理思路：
    * 1. 先绘制一个圆角矩形，
    * 2. 在绘制原始的直角图片
    * 3. 让图片和矩形重叠显示
    * 4. 设置图片的相交保留原则，
    * 设置只保留矩形区域内的图片内容
    * 此处的内容就是圆角图片
    * */
    public Bitmap getRoundBitmap(Bitmap source,int angle){

        Bitmap result = Bitmap.createBitmap(source.getWidth(),source.getHeight(), Bitmap.Config.ARGB_8888);
        //当result作为参数，作用：稍后在canvas上绘制的内容，都会被存储到result对象上
        //即最后canvas上是一个圆角图片,那么result上也会留一个圆角图片
        Canvas canvas = new Canvas(result);

        Paint paint = new Paint();
        paint.setAntiAlias(true); //抗锯齿效果
        //绘制圆角矩形
        /*
        * 1,2 确定直角矩形左上角的点
        * 3,4 确定矩形右下角的点
        * 5. x方向的圆角角度
        * 6. y方向的圆角角度
        * */
        canvas.drawRoundRect(new RectF(0,0,source.getWidth(),source.getHeight()),angle,angle,paint);

        //设置相交保留原则
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        //绘制原始图片
        canvas.drawBitmap(source,0,0,paint);

        return result;
    }

    // 图片的剪切
    public Bitmap getCutBit(Bitmap source,int x,int y,int w,int h){

        return Bitmap.createBitmap(source,x,y,w,h);
    }

}
