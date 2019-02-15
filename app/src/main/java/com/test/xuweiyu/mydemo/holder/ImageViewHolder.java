package com.test.xuweiyu.mydemo.holder;

import android.view.View;
import android.widget.ImageView;

import com.test.xuweiyu.mydemo.R;

/**
 * Created by xuweiyu on 19-2-13.
 * Email:xuweiyu@xiaomi.com
 */
public class ImageViewHolder extends BaseImageViewHolder {
    public ImageView mImageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.recycler_item_img);
    }
}
