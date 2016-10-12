package com.example.xushao.pagerslidingtabstriparrow.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.util.DisplayMetrics;

/**
 * 工具类
 *
 * @author xushao
 */
public class Utils {

    /**
     * 屏幕宽度
     */
    public static int getWidth(Context context) {
        if (context == null) {
            return 0;
        } else {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            return dm.widthPixels;
        }
    }

    /**
     * 获取9patch图
     */
    public static Bitmap getNinePatchBitmap(int id, int w, int h, Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);

        byte[] chunk = bitmap.getNinePatchChunk();
        NinePatchDrawable npDrawable = new NinePatchDrawable(context.getResources(), bitmap, chunk, new Rect(), null);
        npDrawable.setBounds(0, 0, w, h);

        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        npDrawable.draw(canvas);

        return output;
    }

}