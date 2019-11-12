package com.kaiser.binder.skin_libs;

import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.kaiser.binder.skin_libs.bean.SkinCache;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Laizexin on 2019/11/11
 * @description
 */
public class SkinManager {
    private static final String TAG = "SkinManager >>>";
    private static SkinManager instance;
    private Application application;
    // 是否使用app内置配置
    private boolean isDefaultSkin;
    // skinCache的缓存 用于减少反射次数
    private Map<String, SkinCache> cache;
    // 用于加载皮肤包资源
    private Resources skinResources;
    // 用于加载app内置资源
    private Resources appResources;
    // 皮肤包资源所在包名
    private String skinPackageName;

    private SkinManager(Application application) {
        this.application = application;
        cache = new HashMap<>();
        appResources = application.getResources();
    }

    public boolean isDefaultSkin() {
        return isDefaultSkin;
    }

    /**
     * 越早越好 尽可能放application的onCreate()
     * @param application
     */
    public static void init(Application application){
        if(instance == null){
            synchronized (SkinManager.class){
                if(instance == null){
                    instance = new SkinManager(application);
                }
            }
        }
    }

    public static SkinManager getInstance() {
        return instance;
    }

    /**
     * 加载皮肤包
     * @param skinPackagePath 皮肤包路径 空则加载app内资源
     */
    public void loadSkinSources(String skinPackagePath){
        if(TextUtils.isEmpty(skinPackagePath)){
            isDefaultSkin = true;
            return;
        }
        if(cache.containsKey(skinPackagePath)){
            isDefaultSkin = false;
            SkinCache skinCache = cache.get(skinPackagePath);
            if(skinCache != null){
                skinResources = skinCache.getSkinResources();
                skinPackageName = skinCache.getSkinPackageName();
                return;
            }
        }
        //创建资源管理器
        try {
            //执行addAssetPath方法 加载皮肤包
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager,skinPackagePath);
            // 创建加载外部的皮肤包文件Resources（注：依然是本应用加载）
            skinResources = new Resources(assetManager,appResources.getDisplayMetrics(),appResources.getConfiguration());
            // 根据apk皮肤包文件路径（皮肤包也是apk文件），获取该应用的包名。兼容5.0 - 9.0（亲测）
            skinPackageName = application.getPackageManager().getPackageArchiveInfo(skinPackagePath, PackageManager.GET_ACTIVITIES).packageName;
            isDefaultSkin = TextUtils.isEmpty(skinPackageName);
            if(!isDefaultSkin){
                cache.put(skinPackagePath,new SkinCache(skinResources,skinPackageName));
            }
            Log.d(TAG, skinPackageName);
        } catch (Exception e) {
            e.printStackTrace();
            isDefaultSkin = true;
        }
    }

    /**
     * 参考：resources.arsc资源映射表
     * 通过ID值获取资源 Name 和 Type 检查是否存在id
     * 如果没有皮肤包则加载app内置资源ID，反之加载皮肤包指定资源ID
     *
     * @param resourceId
     * @return
     */
    private int getSkinResourceIds(int resourceId){
        if(isDefaultSkin){
            return resourceId;
        }
        String resourceName = appResources.getResourceEntryName(resourceId);
        String resourceType = appResources.getResourceTypeName(resourceId);

        //通过name type检查皮肤包中是否存在该资源ID
        int skinResourceId = skinResources.getIdentifier(resourceName,resourceType,skinPackageName);
        //如果skinResourceID == 0 说明不存在
        isDefaultSkin = skinResourceId == 0;
        return isDefaultSkin? resourceId :skinResourceId;
    }

    public int getColor(int resourceId){
        int id = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getColor(id) : skinResources.getColor(id);
    }

    public ColorStateList getColorStateList(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getColorStateList(ids) : skinResources.getColorStateList(ids);
    }

    public Drawable getDrawableOrMipMap(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getDrawable(ids) : skinResources.getDrawable(ids);
    }

    public String getString(int resourceId) {
        int ids = getSkinResourceIds(resourceId);
        return isDefaultSkin ? appResources.getString(ids) : skinResources.getString(ids);
    }

    public Object getBackgroundOrSrc(int resourceId){
        String resourceTypeName = appResources.getResourceTypeName(resourceId);
        switch (resourceTypeName) {
            case "color":
                return getColor(resourceId);

            case "mipmap":
            case "drawable":
                return getDrawableOrMipMap(resourceId);
        }
        return null;
    }

    public Typeface getTypeface(int resourceId){
        // 通过资源ID获取资源path
        String skinTypefacePath = getString(resourceId);
        if(TextUtils.isEmpty(skinTypefacePath)){
            return Typeface.DEFAULT;
        }
        return isDefaultSkin ? Typeface.createFromAsset(appResources.getAssets(), skinTypefacePath)
                : Typeface.createFromAsset(skinResources.getAssets(), skinTypefacePath);
    }
}
