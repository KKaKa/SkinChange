package com.kaiser.binder.skin_libs.bean;

import android.content.res.Resources;

/**
 * @author Laizexin on 2019/11/11
 * @description
 */
public class SkinCache {

    // 要加载的皮肤资源
    private Resources skinResources;

    // 皮肤包所在包名
    private String skinPackageName;

    public SkinCache(Resources skinResources, String skinPackageName) {
        this.skinResources = skinResources;
        this.skinPackageName = skinPackageName;
    }

    public Resources getSkinResources() {
        return skinResources;
    }

    public void setSkinResources(Resources skinResources) {
        this.skinResources = skinResources;
    }

    public String getSkinPackageName() {
        return skinPackageName;
    }

    public void setSkinPackageName(String skinPackageName) {
        this.skinPackageName = skinPackageName;
    }
}
