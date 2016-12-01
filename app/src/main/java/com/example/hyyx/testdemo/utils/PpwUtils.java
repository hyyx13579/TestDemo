package com.example.hyyx.testdemo.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.hyyx.testdemo.R;


/**
 * Created by zhangqun on 16/6/16.
 */
public abstract class PpwUtils extends PopupWindow{
    /**
     * view:popupWindow布局文件
     * ppwBody:可替换View+取消View的父控件View
     * bodyLayout:可替换View的父控件
     * btnCancelPic:取消按钮
     */
    private View view;
    private LinearLayout ppwBody;
    private LinearLayout bodyLayout;
    private LinearLayout bottomLayout;

    /**
     * popupWindow构造方法
     * @param context 上下文
     * @param contentView popup上部分View
     * @param bottomView popup底层View
     */
    public PpwUtils(Context context, View contentView, View bottomView) {
        view = View.inflate(context, R.layout.ppw_utils,null);
       // view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_ins));
        ppwBody = ((LinearLayout) view.findViewById(R.id.ppw_choose_pic));
       // ppwBody.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_outs));
        bodyLayout = ((LinearLayout) view.findViewById(R.id.ppw_content_view));
        bodyLayout.addView(contentView);
        bottomLayout = ((LinearLayout) view.findViewById(R.id.ppw_bottom_view));
        bodyLayout.addView(bottomView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        update();
        initView(contentView,bottomView);
        createParameter(contentView,bottomView);
    }

    /**
     * pop显示方法
     * @param parent 父控件View
     */
    public void show(View parent){
        showAtLocation(parent, Gravity.BOTTOM,0,0);
    }

    /**
     * 初始化pop
     * @param contentView 构造方法传入的contentView
     * @param bottomView 构造方法传入的bottomView
     */
    protected abstract void initView(View contentView, View bottomView);

    /**
     * 添加控件方法,参数
     * @param contentView 构造方法传入的contentView
     * @param bottomView 构造方法传入的bottomView
     */
    protected abstract void createParameter(View contentView, View bottomView);
}
