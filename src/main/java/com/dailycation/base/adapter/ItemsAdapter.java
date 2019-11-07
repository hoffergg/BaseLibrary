package com.dailycation.base.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.dailycation.base.entity.InfoItem;

import java.util.List;

/**
 * 普通显示栏适配器
 * Created by hoffer on 17/1/21.
 */

public abstract class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder>{
    List<InfoItem> items;

    public void setItems(List<InfoItem> items) {
        this.items = items;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InfoItem item = items.get(position);
        if(item.getIconRes()!=0)
            holder.icon.setImageResource(item.getIconRes());
        if(item.getNextIconRes()!=0)
            holder.nextIcon.setImageResource(item.getNextIconRes());
        if(item.getTitle()!=null)
            holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items == null?0:items.size();
    }

    public abstract class MyViewHolder extends RecyclerView.ViewHolder{
        protected ImageView icon,nextIcon;
        protected TextView title;
        public  MyViewHolder(View itemView) {
            super(itemView);
            initView();
        }

        public abstract void initView();
    }
}
