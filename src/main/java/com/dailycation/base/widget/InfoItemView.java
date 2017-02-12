package com.dailycation.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
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

public class InfoItemView extends LinearLayout {
    Drawable icon,rightIcon;
    String title;

    public InfoItemView(Context context) {
        super(context);
    }

    public InfoItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.InfoItemView);
        icon = a.getDrawable(R.styleable.InfoItemView_itemIcon);
        rightIcon = a.getDrawable(R.styleable.InfoItemView_itemNextIcon);
        title = a.getString(R.styleable.InfoItemView_itemTitle);

        View view = inflate(context,R.layout.info_item,null);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        ImageView nextIconView = (ImageView) view.findViewById(R.id.next_icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);

        if(icon!=null)
            iconView.setImageDrawable(icon);
        if(title!=null)
            titleView.setText(title);
        if(rightIcon!=null)
            nextIconView.setImageDrawable(rightIcon);

        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(view);
    }

    public InfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
