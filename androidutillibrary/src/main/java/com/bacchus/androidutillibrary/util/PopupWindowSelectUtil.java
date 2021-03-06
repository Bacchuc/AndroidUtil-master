package com.bacchus.androidutillibrary.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * Created by Laiyin on 2017/4/4.
 */

public class PopupWindowSelectUtil extends PopupWindow{

    private TextView tvTake;
    private TextView tvSelect;
    private ImageView mImage;
    private int viewLayout;
    private View view;
    private Context mContext;
    private Activity activity;
    private PopupWindow popupWindow;
    private LayoutInflater inflater;

    private int animationStyle;
    private @LayoutRes int resource;
    private @IdRes int tv_takePictureId;
    private @IdRes int tv_choosePictureId;

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    SelectImageUtil selectImageUtil;
    SelectImageUtil selectImageUtilResult;

    public PopupWindowSelectUtil(Context context, Activity activity, int viewLayout, ImageView mImage,int animationStyle,@LayoutRes int resource,@IdRes int tv_takePictureId,@IdRes int tv_choosePictureId) {
        this.mContext = context;
        this.activity = activity;
        this.viewLayout= viewLayout;
        this.mImage=mImage;
        this.animationStyle=animationStyle;
        this.resource=resource;
        this.tv_choosePictureId=tv_choosePictureId;
        this.tv_takePictureId=tv_takePictureId;
        initPopupWindow();
    }

    private void initPopupWindow() {
        inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(resource, null);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(animationStyle);
        popupWindow.setContentView(view);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        WindowManager.LayoutParams params = activity.getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.8f;
        activity.getWindow().setAttributes(params);//把该参数对象设置进当前界面中

        selectImageUtil=new SelectImageUtil(activity, mImage);

        show();

        myDismiss();

        tvTake= (TextView) view.findViewById(tv_takePictureId);
        tvTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageUtil.takePicture();
                popupWindow.dismiss();
            }
        });

        tvSelect= (TextView) view.findViewById(tv_choosePictureId);
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageUtil.choosePicture();
                popupWindow.dismiss();
            }
        });
    }

    /**
     * popupwindow弹出时设置原Activity背景透明
     */
    private void myDismiss() {
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
                activity.getWindow().setAttributes(params);
            }
        });
    }

    /**
     * Activity调用show()弹出popupwindow
     */
    public void show() {
        //第一个参数为父View对象，即PopupWindow所在的父控件对象，第二个参数为它的重心，后面两个分别为x轴和y轴的偏移量
        popupWindow.showAtLocation(inflater.inflate(viewLayout, null), Gravity.CENTER, 0, 0);
        //设置显示PopupWindow的位置位于View的左下方，x,y表示坐标偏移量
        //popupWindow.showAsDropDown(view,100,100);
    }
}
