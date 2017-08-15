package com.yzd.androidutil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yzd.androidutil.util.ActivityCollectorUtil;
import com.yzd.androidutil.util.AnimatorUtil;
import com.yzd.androidutil.util.AppCenterUtil;
import com.yzd.androidutil.util.BlurBehind;
import com.yzd.androidutil.util.PopupWindowSelectUtil;
import com.yzd.androidutil.util.SelectImageUtil;
import com.yzd.androidutil.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通过对工具类方法的调用来说明对工具类的使用
 * 为了方便使用（复制粘贴）与说明理解，初始化都放在了各个方法中
 */
public class MainActivity extends AppCompatActivity {

    SelectImageUtil selectImageUtilResult;
    SelectImageUtil selectImageUtil;
    AnimatorUtil animatorUtil;
    Intent intent;

    @BindView(R.id.iv_cemare)
    ImageView ivCemare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * 对ToastUtil的使用
     */
    public void tastUtil() {
        ToastUtil.showLong(this, "短时间显示Toast");
        ToastUtil.showLong(this, "长时间显示Toast");
    }

    /**
     * 对PopupWindowSelectUtil的使用，PopupWindowSelectUtil可以与SelectImageUtil结合使用，并且PopupWindowSelectUtil中
     * 也封装了对SelectImageUtil的使用
     */
    public void popupWindowSelectUtil() {
        PopupWindowSelectUtil popupWindowSelectUtil = new PopupWindowSelectUtil(this, MainActivity.this, R.layout.activity_main, ivCemare);
        popupWindowSelectUtil.show();
    }

    /**
     * 对SelectImageUtil的使用
     */
    public void selectImageUtil() {
        selectImageUtil = new SelectImageUtil(MainActivity.this, ivCemare);
        selectImageUtil.takePicture();      //调用拍照功能
        selectImageUtil.choosePicture();    //调用选择本地相册功能
    }

    /**
     * 当使用SelectImageUtil时，需要对onActivityResult方法重写，但是重写的具体内容已经封装在了SelectImageUtil中，
     * 只需要调用SelectImageUtil中的onActivityResult即可
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectImageUtilResult = new SelectImageUtil(MainActivity.this, ivCemare);
        selectImageUtilResult.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 对动画工具类的使用 直接调用方法即可
     */
    public void animatorUtil() {
        animatorUtil = new AnimatorUtil();
        animatorUtil.scaleAndTranslationAnimator(ivCemare, 0.3f, 1F, 0.3f, 1F, 0, -230F, 0, -180, 400, null);
    }

    /**
     * 对ActivityCollectorUtil的使用，将需要管理的活动继承ActivityCollectorUtil，每个被创建的活动都会被加入栈中
     * 当需要双击退出程序时可以直接调用finish()方法移除所有的活动
     */
    public void activityCollectorUtil() {
        ActivityCollectorUtil.finishAll();
    }

    /**
     * AppCenterUtil的使用，通过调用getContextObject()方法可以在全局取得应用的context参数
     */
    public void appCenterUtil() {
        AppCenterUtil.getContextObject();
    }

    /**
     * 设置弹出某个类似dialog的页面时的背景模糊  需要新建一个页面，需要弹出的控件就写在新的页面中
     * 此方法写在原页面中 在原页面中设计点击事件跳转到新页面即可
     */
    public void BlurBehind() {
        BlurBehind.getInstance().setBackground(this);
        BlurBehind.getInstance()
                .withAlpha(80)
                .withFilterColor(Color.parseColor("#0075c0"))
                .setBackground(this);
    }

    /**
     * 设置背景模糊时的跳转
     */
    @OnClick(R.id.iv_cemare)
    public void onClick() {
        intent = new Intent(MainActivity.this, NewActivity.class);
        startActivity(intent);
        finish();
    }
}
