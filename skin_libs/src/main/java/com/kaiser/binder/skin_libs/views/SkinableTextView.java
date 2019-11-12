package com.kaiser.binder.skin_libs.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
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
public class SkinableTextView extends AppCompatTextView implements ViewMatch {
    private AttrsBean attrsBean;

    public SkinableTextView(Context context) {
        this(context,null);
    }

    public SkinableTextView(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.textViewStyle);
    }

    public SkinableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attrsBean = new AttrsBean();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SkinnableTextView, defStyleAttr, 0);
        attrsBean.put(typedArray,R.styleable.SkinnableTextView);
        typedArray.recycle();
    }

    /**
     * 1.根据自定义属性，获取styleable中的background属性
     * 2.根据styleable获取控件某属性的resourceId
     * 3.设置颜色等 使用兼容包方法
     */
    @Override
    public void skinnableView() {
        int key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_background];
        int backgroundResourceId = attrsBean.get(key);
        if(backgroundResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                Drawable drawable = ContextCompat.getDrawable(getContext(),backgroundResourceId);
                setBackgroundDrawable(drawable);
            }else{
                Object skinResourceId = SkinManager.getInstance().getBackgroundOrSrc(backgroundResourceId);
                if(skinResourceId instanceof Integer){
                    int color = (int) skinResourceId;
                    setBackgroundColor(color);
                }else{
                    Drawable drawable = (Drawable) skinResourceId;
                    setBackgroundDrawable(drawable);
                }
            }
        }

        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_android_textColor];
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

        key = R.styleable.SkinnableTextView[R.styleable.SkinnableTextView_custom_typeface];
        int textTypefaceResourceId = attrsBean.get(key);
        if(textColorResourceId > 0){
            if(SkinManager.getInstance().isDefaultSkin()){
                setTypeface(Typeface.DEFAULT);
            }else{
                setTypeface(SkinManager.getInstance().getTypeface(textTypefaceResourceId));
            }
        }
    }
}
