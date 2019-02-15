package com.test.xuweiyu.mydemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.test.xuweiyu.mydemo.R;
import com.test.xuweiyu.mydemo.bean.ImageBean;
import com.test.xuweiyu.mydemo.holder.BaseImageViewHolder;
import com.test.xuweiyu.mydemo.holder.ImageViewHolder;
import com.test.xuweiyu.mydemo.holder.TextViewHolder;
import com.test.xuweiyu.mydemo.utils.ImageUtils;

import java.util.List;

/**
 * Created by xuweiyu on 19-2-13.
 * Email:xuweiyu@xiaomi.com
 */
public class RecycleImageAdapter extends RecyclerView.Adapter<BaseImageViewHolder> {
    public static final int TYPE_SINGLE_IMAGE = 0x0000;
    public static final int TYPE_IMAGE_AND_TEXT = 0x0001;
    private List<ImageBean> mData;
    private ItemClickListener mItemClickListener;
    private Context mContext;

    public RecycleImageAdapter(Context context, List<ImageBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BaseImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseImageViewHolder holder;
        if (viewType == TYPE_SINGLE_IMAGE) {
            holder = new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycler_image_list, null));
        } else {
            holder = new TextViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recycle_text_list, null));
        }
//        if (viewType == TYPE_SINGLE_IMAGE) {
//            holder = new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_image_list, null));
//        } else {
//            holder = new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_text_list, null));
//        }
        return holder;
    }

    private void bindClickListener(final BaseImageViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_one);
                holder.itemView.startAnimation(animation);
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull BaseImageViewHolder holder, int position) {
        bindClickListener(holder, position);
        ImageBean bean = mData.get(position);
        switch (bean.type) {
            case ImageBean.IMAGE:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
//                imageViewHolder.mImageView.setImageBitmap(bean.bitmap);
//                Glide.with(imageViewHolder.itemView.getContext())
//                        .load(bean.url)
//                        .into(imageViewHolder.mImageView);
                ImageUtils.showImage(mContext,bean.url,imageViewHolder.mImageView,false);
                break;
            case ImageBean.TEXT:
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.mImgNameTv.setText(bean.name);
//                textViewHolder.mImg.setImageBitmap(bean.bitmap);
//                Glide.with(textViewHolder.itemView.getContext())
//                        .load(bean.url)
//                        .into(textViewHolder.mImg);
                ImageUtils.showImage(mContext,bean.url,textViewHolder.mImg,false);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        ImageBean bean = mData.get(position);
        switch (bean.type) {
            case ImageBean.IMAGE:
                return TYPE_SINGLE_IMAGE;
            case ImageBean.TEXT:
                return TYPE_IMAGE_AND_TEXT;
        }
        return super.getItemViewType(position);
    }

    public void refresh(List<ImageBean> bitmaps) {
        this.mData = bitmaps;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
