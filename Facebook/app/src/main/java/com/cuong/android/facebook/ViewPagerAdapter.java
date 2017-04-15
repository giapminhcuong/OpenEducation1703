package com.cuong.android.facebook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/**
 * Created by Admin on 10/4/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private static final String[] titles = {"New Feed", "Contact", "Video", "Notification", "More"};
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new NewFeedFragment();
            case 1: return new ContactFragment();
            case 2: return new VideoFragment();
            case 3: return new NotificationFragment();
            case 4: return new MoreFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}
