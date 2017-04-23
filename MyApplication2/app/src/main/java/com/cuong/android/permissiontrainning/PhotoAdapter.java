package com.cuong.android.permissiontrainning;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Admin on 23/4/2017.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder>{

    private List<Picture> mPictureList;
    private Activity mActivity;

    public PhotoAdapter(List<Picture> pictureList, Activity activity) {
        mPictureList = pictureList;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_photo, parent, false);
        return new ViewHolder(v, mActivity);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picture picture = mPictureList.get(position);
        holder.bindData(picture);
    }

    @Override
    public int getItemCount() {
        return mPictureList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewPhoto;
        private TextView mTextViewPhotoName;

        private Activity mActivity;

        public ViewHolder(View itemView, Activity activity) {
            super(itemView);
            mActivity = activity;
            mImageViewPhoto = (ImageView) itemView.findViewById(R.id.image_view_photo);
            mTextViewPhotoName = (TextView) itemView.findViewById(R.id.text_view_photo_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mActivity, mTextViewPhotoName.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bindData(Picture picture) {
            Glide.with(mActivity)
                    .loadFromMediaStore(picture.getUri())
                    .fitCenter()
                    .into(mImageViewPhoto);
            mTextViewPhotoName.setText(picture.getName());
        }
    }
}
