package com.kaiser.binder.skin_libs.bean;

import android.content.res.TypedArray;
import android.util.SparseIntArray;

/**
 * @author Laizexin on 2019/11/6
 * @description
 */
public class AttrsBean {

    private SparseIntArray sparseIntArray;
    private static final int DEFAULT_VALUE = -1;

    public AttrsBean() {
        sparseIntArray = new SparseIntArray();
    }

    /**
     * 储控件的key、value
     *
     * @param typedArray 控件属性的类型集合，如：background / textColor
     * @param styleable  自定义属性，参考value/attrs.xml
     */
    public void put(TypedArray typedArray,int[] styleable){
        for (int i = 0; i < typedArray.length(); i++) {
            int key = styleable[i];
            int resourceId = typedArray.getResourceId(i,DEFAULT_VALUE);
            sparseIntArray.put(key,resourceId);
        }
    }

    /**
     * 获取控件某属性的resourceId
     *
     * @param styleable 自定义属性，参考value/attrs.xml
     * @return 某控件某属性的resourceId
     */
    public int get(int styleable){
        return sparseIntArray.get(styleable);
    }
}
