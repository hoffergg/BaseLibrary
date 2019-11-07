package com.dailycation.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class UnreadRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {
    public UnreadRadioButton(Context context) {
        super(context);
    }

    public UnreadRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnreadRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

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
