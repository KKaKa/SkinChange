package com.kaiser.binder.skin_libs.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.kaiser.binder.skin_libs.R;
import com.kaiser.binder.skin_libs.SkinManager;
import com.kaiser.binder.skin_libs.bean.AttrsBean;
import com.kaiser.binder.skin_libs.core.ViewMatch;

/**
 * @author Laizexin on 2019/11/6
 * @description
 */
public class SkinableButton extends AppCompatButton implements ViewMatch {
    private AttrsBean attrsBean;

    public SkinableButton(Context context) {
        this(context,null);
    }

    public SkinableButton(Context context, AttributeSet attrs) {
        this(context, attrs,android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public SkinableButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableButton, defStyleAttr, 0);
        attrsBean.put(typedArray,R.styleable.SkinnableButton);
        typedArray.recycle();
    }

    /**
     * 1.根据自定义属性，获取styleable中的background属性
     * 2.根据styleable获取控件某属性的resourceId
     * 3.设置颜色等 使用兼容包方法
     */
    @Override
    public void skinnableView() {
        int key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_background];
        int backgroundResourceId = attrsBean.get(key);
        if(backgroundResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                Drawable drawable = ContextCompat.getDrawable(getContext(),backgroundResourceId);
                setBackgroundDrawable(drawable);
            }else{
                Object skinResources = SkinManager.getInstance().getBackgroundOrSrc(backgroundResourceId);
                if(skinResources instanceof Integer){
                    int color = (int) skinResources;
                    setBackgroundColor(color);
                }else{
                    Drawable drawable = (Drawable) skinResources;
                    setBackgroundDrawable(drawable);
                }
            }
        }

        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_android_textColor];
        int textColorResourceId = attrsBean.get(key);
        if(textColorResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                ColorStateList colorStateList = ContextCompat.getColorStateList(getContext(), textColorResourceId);
                setTextColor(colorStateList);
            }else{
                ColorStateList colorStateList = SkinManager.getInstance().getColorStateList(textColorResourceId);
                setTextColor(colorStateList);
            }
        }

        key = R.styleable.SkinnableButton[R.styleable.SkinnableButton_custom_typeface];
        int textTypefaceResourceId = attrsBean.get(key);
        if(textTypefaceResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                setTypeface(Typeface.DEFAULT);
            }else{
                setTypeface(SkinManager.getInstance().getTypeface(textTypefaceResourceId));
            }
        }
    }
}
