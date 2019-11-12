package com.kaiser.binder.skinchange;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;

import com.kaiser.binder.skin_libs.base.SkinActivity;
import com.kaiser.binder.skin_libs.utils.PreferencesUtils;

import java.io.File;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

public class MainActivity extends SkinActivity {
    private static final String CURRENT_SKIN = "currentSkin";
    private static final String DEFAULT_SKIN_NAME = "default_package";
    private static final String DYNAMIC_SKIN_NAME = "skin_package-debug";
    private String skinPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skinPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"skin_package-debug.skin";
        Log.e("Skin_path >>> ",skinPath);

        // 运行时权限申请（6.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }

        if(DYNAMIC_SKIN_NAME.equals(PreferencesUtils.getString(this,CURRENT_SKIN))){
            dynamicSkin(skinPath, R.color.skin_item_color);
        }else{
            defaultSkin(R.color.colorPrimary);
        }
    }

    @Override
    public boolean openChangeSkin() {
        return true;
    }

    public void skinDynamic(View view) {
        if(!DYNAMIC_SKIN_NAME.equals(PreferencesUtils.getString(this,CURRENT_SKIN))){
            dynamicSkin(skinPath,R.color.skin_item_color);
            PreferencesUtils.putString(this,CURRENT_SKIN,DYNAMIC_SKIN_NAME);
        }
    }

    public void skinDefault(View view) {
        if(!DEFAULT_SKIN_NAME.equals(PreferencesUtils.getString(this,CURRENT_SKIN))){
            defaultSkin(R.color.colorPrimary);
            PreferencesUtils.putString(this,CURRENT_SKIN,DEFAULT_SKIN_NAME);
        }
    }
}
