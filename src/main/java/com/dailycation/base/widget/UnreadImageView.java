package com.dailycation.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ImageView which can set with unread red dot.
 * Created by hoffer on 17/3/5.
 */

public class UnreadImageView extends ImageView {
    /**
     * unread count.
     */
    int unreadCount = 0;

    /**
     * default red dot radius, in pixels.
     */
    int radius = 10;

    /**
     * default red color.
     */
    String redColor = "#fc291c";

    public UnreadImageView(Context context) {
        super(context);
    }

    public UnreadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(unreadCount>0)
            drawRedDot(canvas);
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
        invalidate();
    }

    public void drawRedDot(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(redColor));
        canvas.drawCircle(getWidth()-radius,radius,radius,paint);
    }
}
