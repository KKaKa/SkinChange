package com.kaiser.binder.skinchange;

import android.app.Application;

import com.kaiser.binder.skin_libs.SkinManager;

/**
 * @author Laizexin on 2019/11/11
 * @description
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
