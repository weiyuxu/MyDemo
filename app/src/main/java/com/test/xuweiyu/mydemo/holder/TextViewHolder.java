package com.test.xuweiyu.mydemo.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.xuweiyu.mydemo.R;

/**
 * Created by xuweiyu on 19-2-13.
 * Email:xuweiyu@xiaomi.com
 */
public class TextViewHolder extends BaseImageViewHolder {
    public TextView mImgNameTv;
    public ImageView mImg;

    public TextViewHolder(View itemView) {
        super(itemView);
        mImgNameTv = itemView.findViewById(R.id.recycler_item_tv);
        mImg = itemView.findViewById(R.id.recycler_item_img);
    }
}
