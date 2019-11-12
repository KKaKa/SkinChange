package com.kaiser.binder.skin_libs.core;

import android.content.Context;
import android.support.v7.app.AppCompatViewInflater;
import android.util.AttributeSet;
import android.view.View;

import com.kaiser.binder.skin_libs.views.SkinableButton;
import com.kaiser.binder.skin_libs.views.SkinableImageView;
import com.kaiser.binder.skin_libs.views.SkinableLinearLayout;
import com.kaiser.binder.skin_libs.views.SkinableRelativeLayout;
import com.kaiser.binder.skin_libs.views.SkinableTextView;

/**
 * @author Laizexin on 2019/11/6
 * @description
 */
public class CustomAppCompatViewInflater {

    private String name;
    private AttributeSet attrs;
    private Context context;

    public CustomAppCompatViewInflater(Context context) {
        this.context = context;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttrs(AttributeSet attrs) {
        this.attrs = attrs;
    }

    public View match(){
        View view = null;
        switch (name){
            case "LinearLayout":
                view = new SkinableLinearLayout(context,attrs);
                this.verifyNotNull(view,name);
                break;

            case "Button":
                view = new SkinableButton(context,attrs);
                this.verifyNotNull(view,name);
                break;

            case "TextView":
                view = new SkinableTextView(context,attrs);
                this.verifyNotNull(view,name);
                break;

            case "ImageView":
                view = new SkinableImageView(context,attrs);
                this.verifyNotNull(view,name);
                break;

            case "RelativeLayout":
                view = new SkinableRelativeLayout(context,attrs);
                this.verifyNotNull(view,name);
                break;
        }
        return view;
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(this.getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }
}
