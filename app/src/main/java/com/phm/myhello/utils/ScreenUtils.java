package com.phm.myhello.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.phm.myhello.manager.Contextor;


public class ScreenUtils {

    private static ScreenUtils instance;

    public static ScreenUtils getInstance() {
        if (instance == null)
            instance = new ScreenUtils();
        return instance;
    }

    private Context mContext;

    private ScreenUtils() {
        mContext = Contextor.getInstance().getContext();
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}
