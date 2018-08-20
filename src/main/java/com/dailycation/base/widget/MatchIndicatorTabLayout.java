package com.dailycation.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dailycation.base.R;

import java.lang.reflect.Field;

/**
 * 选中的下划线和文字长度保持一致的TabLayout
 */
public class MatchIndicatorTabLayout extends TabLayout {
    public MatchIndicatorTabLayout(Context context) {
        super(context);
    }

    public MatchIndicatorTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchIndicatorTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            //拿到tabLayout的mTabStrip属性
            LinearLayout mTabStrip = (LinearLayout) getChildAt(0);

            int dp10 = getContext().getResources().getDimensionPixelSize(R.dimen.dp10);

            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);

                //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);

                TextView mTextView = (TextView) mTextViewField.get(tabView);

                tabView.setPadding(0, 0, 0, 0);

                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }

                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width ;
                params.leftMargin = dp10;
                params.rightMargin = dp10;
                tabView.setLayoutParams(params);

                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
