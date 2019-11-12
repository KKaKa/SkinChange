package com.kaiser.binder.skin_libs.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.kaiser.binder.skin_libs.R;
import com.kaiser.binder.skin_libs.SkinManager;
import com.kaiser.binder.skin_libs.bean.AttrsBean;
import com.kaiser.binder.skin_libs.core.ViewMatch;

/**
 * @author Laizexin on 2019/11/6
 * @description
 */
public class SkinableRelativeLayout extends RelativeLayout implements ViewMatch {
    private AttrsBean attrsBean;

    public SkinableRelativeLayout(Context context) {
        this(context,null);
    }

    public SkinableRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SkinableRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableRelativeLayout, defStyleAttr, 0);
        attrsBean.put(typedArray,R.styleable.SkinnableRelativeLayout);
        typedArray.recycle();
    }

    /**
     * 1.根据自定义属性，获取styleable中的background属性
     * 2.根据styleable获取控件某属性的resourceId
     * 3.设置颜色等 使用兼容包方法
     */
    @Override
    public void skinnableView() {
        int key = R.styleable.SkinnableRelativeLayout[R.styleable.SkinnableRelativeLayout_android_background];
        int backgroundResourceId = attrsBean.get(key);
        if(backgroundResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                Drawable drawable = ContextCompat.getDrawable(getContext(),backgroundResourceId);
                setBackground(drawable);
            }else{
                Object skinResourceId = SkinManager.getInstance().getBackgroundOrSrc(backgroundResourceId);
                if(skinResourceId instanceof Integer){
                    int color = (int) skinResourceId;
                    setBackgroundColor(color);
                }else{
                    Drawable drawable = (Drawable) skinResourceId;
                    setBackground(drawable);
                }
            }
        }
    }
}
