package com.cuong.android.facebook;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

public class FacebookActivity extends AppCompatActivity {

    private ViewPager mViewPagerFacebook;
    private TabLayout mTabLayoutFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        mViewPagerFacebook = (ViewPager) findViewById(R.id.view_pager_facebook);
        mTabLayoutFacebook = (TabLayout) findViewById(R.id.tab_layout_facebook);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerFacebook.setAdapter(adapter);
        mTabLayoutFacebook.setupWithViewPager(mViewPagerFacebook);
        for (int i=0; i<mTabLayoutFacebook.getTabCount(); i++) {
            mTabLayoutFacebook.getTabAt(i).setIcon(R.mipmap.ic_launcher);
        }
    }
}
