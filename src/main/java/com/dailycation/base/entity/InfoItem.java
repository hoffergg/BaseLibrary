package com.dailycation.base.entity;

/**
 * 普通显示栏
 * Created by hoffer on 17/1/21.
 */

public class InfoItem {
    int iconRes;
    int nextIconRes;
    String title;

    public int getIconRes() {
        return iconRes;
    }

    public int getNextIconRes() {
        return nextIconRes;
    }

    public String getTitle() {
        return title;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public void setNextIconRes(int nextIconRes) {
        this.nextIconRes = nextIconRes;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
