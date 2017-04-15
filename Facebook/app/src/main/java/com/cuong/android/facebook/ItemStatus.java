package com.cuong.android.facebook;

/**
 * Created by Admin on 10/4/2017.
 */

public class ItemStatus {
    private int mResId;
    private String mStatus;

    public ItemStatus(int resId, String status) {
        mResId = resId;
        mStatus = status;
    }

    public int getResId() {
        return mResId;
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
