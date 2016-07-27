package cn.lemon.view;

import android.app.Activity;

/**
 * Created by linlongxin on 2016/7/27.
 */

public class Util {

    private static Activity mContext;

    public static void initialize(Activity context){
        mContext = context;
    }

    public static int getScreenWidth(){
        return mContext.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(){
        return mContext.getWindowManager().getDefaultDisplay().getHeight();
    }
}
