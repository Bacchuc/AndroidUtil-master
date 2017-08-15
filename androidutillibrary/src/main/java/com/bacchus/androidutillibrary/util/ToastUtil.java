package com.bacchus.androidutillibrary.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Laiyin on 2017/3/29.
 *
 * Toast工具类
 */

public class ToastUtil {

    /**
     * 短时间显示
     * @param context
     * @param str 显示信息
     */
    public static void showShort(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示
     * @param context
     * @param str 显示信息
     */
    public static void showLong(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_LONG).show();
    }

}
