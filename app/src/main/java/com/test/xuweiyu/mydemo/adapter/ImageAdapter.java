package com.test.xuweiyu.mydemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.test.xuweiyu.mydemo.R;
import com.test.xuweiyu.mydemo.bean.ImageBean;

import java.util.List;

/**
 * Created by xuweiyu on 19-2-12.
 * Email:xuweiyu@xiaomi.com
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<ImageBean> bitmaps;

    public ImageAdapter(Context context, List<ImageBean> bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
    }

    public void refreshData(List<ImageBean> bitmaps) {
        this.bitmaps = bitmaps;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return bitmaps == null ? 0 : bitmaps.size();
    }

    @Override
    public ImageBean getItem(int position) {
        return bitmaps == null ? null : bitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image_list, null);
            holder.imageView = convertView.findViewById(R.id.item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageBitmap(getItem(position).bitmap);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
    }
}
