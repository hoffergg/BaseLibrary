package com.dailycation.base.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView utils
 * Created by hoffer on 17/7/29.
 */

public class RecyclerViewUtil {

    /**
     * check is linear RecyclerView scrolled to bottom.
     * @param linearLayoutManager
     * @return
     */
    public static boolean isLinearScrolledBottom(LinearLayoutManager linearLayoutManager){
        int pastVisibleItems, visibleItemCount, totalItemCount;
        visibleItemCount = linearLayoutManager.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

        return (visibleItemCount + pastVisibleItems) >= totalItemCount;
    }
}
