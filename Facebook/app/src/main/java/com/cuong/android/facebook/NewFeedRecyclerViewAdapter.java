package com.cuong.android.facebook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 10/4/2017.
 */

public class NewFeedRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ItemStatus> listItemStatus;

    public NewFeedRecyclerViewAdapter(List<ItemStatus> listItemStatus) {
        this.listItemStatus = listItemStatus;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newfeed_item_status, parent, false);
        return new ViewHolderStatus(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemStatus itemStatus = listItemStatus.get(position);
        ViewHolderStatus viewHolderStatus = (ViewHolderStatus) holder;
        viewHolderStatus.bindData(itemStatus);
    }

    @Override
    public int getItemCount() {
        return listItemStatus.size();
    }

    // VIEW HOLDER
    class ViewHolderStatus extends RecyclerView.ViewHolder {

        private ImageView mImageViewAvatar;
        private TextView mTextViewStatus;

        public ViewHolderStatus(View itemView) {
            super(itemView);
            mImageViewAvatar = (ImageView) itemView.findViewById(R.id.image_view_avatar);
            mTextViewStatus = (TextView) itemView.findViewById(R.id.text_view_status);
        }

        public void bindData(ItemStatus itemStatus) {
            mImageViewAvatar.setImageResource(itemStatus.getResId());
            mTextViewStatus.setText(itemStatus.getStatus());
        }
    }
}
