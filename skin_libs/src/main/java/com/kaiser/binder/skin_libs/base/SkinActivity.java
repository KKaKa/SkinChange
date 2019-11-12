package com.kaiser.binder.skin_libs.base;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaiser.binder.skin_libs.SkinManager;
import com.kaiser.binder.skin_libs.core.CustomAppCompatViewInflater;
import com.kaiser.binder.skin_libs.core.ViewMatch;
import com.kaiser.binder.skin_libs.utils.ActionBarUtils;
import com.kaiser.binder.skin_libs.utils.NavigationUtils;
import com.kaiser.binder.skin_libs.utils.StatusBarUtils;

public class SkinActivity extends AppCompatActivity {
    private CustomAppCompatViewInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory2(layoutInflater, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if(openChangeSkin()){
            if(inflater == null){
                inflater = new CustomAppCompatViewInflater(context);
            }
            inflater.setName(name);
            inflater.setAttrs(attrs);
            return inflater.match();
        }
        return super.onCreateView(parent, name, context, attrs);
    }

    public boolean openChangeSkin(){
        return false;
    }

    public void setDayNightMode(@AppCompatDelegate.NightMode int nightMode){
        boolean isHighter21 = Build.VERSION.SDK_INT >= 21;
        getDelegate().setLocalNightMode(nightMode);
        if(isHighter21){
            StatusBarUtils.forStatusBar(this);
            NavigationUtils.forNavigation(this);
            ActionBarUtils.forActionBar(this);
        }
        View decorView = getWindow().getDecorView();
        applyDayNightForView(decorView);
    }

    private void applyDayNightForView(View view){
        if(view instanceof ViewMatch){
            ViewMatch viewMatch = (ViewMatch) view;
            viewMatch.skinnableView();
        }

        if(view instanceof ViewGroup){
            ViewGroup parent = (ViewGroup) view;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applyDayNightForView(parent.getChildAt(i));
            }
        }
    }

    public void dynamicSkin(String skinPath, int themeColorId){
        SkinManager.getInstance().loadSkinSources(skinPath);
        if(themeColorId != 0){
            int themeColor = SkinManager.getInstance().getColor(themeColorId);
            StatusBarUtils.forStatusBar(this,themeColor);
            NavigationUtils.forNavigation(this,themeColor);
            ActionBarUtils.forActionBar(this,themeColor);
            applyChageViews(getWindow().getDecorView());
        }
    }

    public void defaultSkin(int themeColorId){
        this.dynamicSkin(null,themeColorId);
    }

    private void applyChageViews(View decorView) {
        if(decorView instanceof ViewMatch){
            ViewMatch viewMatch = (ViewMatch) decorView;
            viewMatch.skinnableView();
        }

        if(decorView instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) decorView;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                applyChageViews(viewGroup.getChildAt(i));
            }
        }
    }
}
