package com.kaiser.binder.skin_libs.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.kaiser.binder.skin_libs.R;
import com.kaiser.binder.skin_libs.SkinManager;
import com.kaiser.binder.skin_libs.bean.AttrsBean;
import com.kaiser.binder.skin_libs.core.ViewMatch;

/**
 * @author Laizexin on 2019/11/6
 * @description
 */
public class SkinableImageView extends AppCompatImageView implements ViewMatch {
    private AttrsBean attrsBean;

    public SkinableImageView(Context context) {
        this(context,null);
    }

    public SkinableImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SkinableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableImageView, defStyleAttr, 0);
        attrsBean.put(typedArray,R.styleable.SkinnableImageView);
        typedArray.recycle();
    }

    /**
     * 1.根据自定义属性，获取styleable中的background属性
     * 2.根据styleable获取控件某属性的resourceId
     * 3.设置颜色等 使用兼容包方法
     */
    @Override
    public void skinnableView() {
        int key = R.styleable.SkinnableImageView[R.styleable.SkinnableImageView_android_src];
        int backgroundResourceId = attrsBean.get(key);
        if(backgroundResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                Drawable drawable = ContextCompat.getDrawable(getContext(),backgroundResourceId);
                setImageDrawable(drawable);
            }else{
                Object skinResourceId = SkinManager.getInstance().getBackgroundOrSrc(backgroundResourceId);
                if(skinResourceId instanceof Integer){
                    int color = (int) skinResourceId;
                    setImageResource(color);
                }else{
                    Drawable drawable = (Drawable) skinResourceId;
                    setImageDrawable(drawable);
                }
            }
        }
    }
}
