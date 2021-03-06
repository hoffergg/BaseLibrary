package com.dailycation.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailycation.base.R;

/**
 * 普通消息栏
 * Created by hoffer on 17/2/11.
 */

public class SettingItemView extends LinearLayout {
    Drawable icon,image,rightIcon, contentLeftDrawable;
    String title,content,contentHint;
    int unReadCount;
    LinearLayout llBorder;
    int layoutRes;

    /**
     * Image content
     */
    ImageView imageView;

    TextView contentView;

    TextView tvUnread;

    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        show(context,attrs);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        show(context,attrs);
    }

    public void show(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.SettingItemView);
        icon = a.getDrawable(R.styleable.SettingItemView_settingIcon);
        rightIcon = a.getDrawable(R.styleable.SettingItemView_settingNextIcon);
        image = a.getDrawable(R.styleable.SettingItemView_settingImage);
        contentLeftDrawable = a.getDrawable(R.styleable.SettingItemView_settingContentLeftDrawable);
        title = a.getString(R.styleable.SettingItemView_settingTitle);
        content = a.getString(R.styleable.SettingItemView_settingText);
        contentHint = a.getString(R.styleable.SettingItemView_settingHint);
        layoutRes = a.getResourceId(R.styleable.SettingItemView_settingLayout,R.layout.setting_item);

        View view = inflate(context,layoutRes,null);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        ImageView nextIconView = (ImageView) view.findViewById(R.id.next_icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        contentView = (TextView) view.findViewById(R.id.content);
        imageView = (ImageView) view.findViewById(R.id.image);
        tvUnread = (TextView) view.findViewById(R.id.tv_unread);
        llBorder = (LinearLayout) view.findViewById(R.id.border);

        if(icon!=null)
            iconView.setImageDrawable(icon);
        if(title!=null)
            titleView.setText(title);
        if(rightIcon!=null)
            nextIconView.setImageDrawable(rightIcon);
        if(contentHint!=null)
            contentView.setText(contentHint);
        if(content!=null)
            contentView.setText(content);
        if(contentLeftDrawable !=null)
            contentView.setCompoundDrawablesWithIntrinsicBounds(contentLeftDrawable,null,null,null);
        if(image!=null)
            imageView.setImageDrawable(image);
        if(unReadCount!=0) {
            if(tvUnread!=null) {
                tvUnread.setText(String.valueOf(tvUnread));
                tvUnread.setVisibility(VISIBLE);
            }
        }else {
            if(tvUnread!=null) {
                tvUnread.setVisibility(GONE);
            }
        }
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(view);
    }

    /**
     * show image content.
     * @param image
     */
    public void setImage(Drawable image){
        imageView.setImageDrawable(image);
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
        if(unReadCount!=0) {
            tvUnread.setText(String.valueOf(unReadCount));
            tvUnread.setVisibility(VISIBLE);
        }else {
            tvUnread.setVisibility(GONE);
        }
    }

    /**
     * show image content.
     */
    public void setImageBitmap(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setContent(String text){
        contentView.setText(text);
    }

    public String getContent(){
        return contentView.getText().toString();
    }
}
