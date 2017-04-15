package com.cuong.android.facebook;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFeedFragment extends Fragment {

    private RecyclerView mRecyclerViewNewFeed;

    public NewFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mRecyclerViewNewFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ItemStatus> list = new ArrayList<>();
        list.add(new ItemStatus(R.mipmap.ic_launcher, "Bạn đang làm gì đấy?"));
        NewFeedRecyclerViewAdapter adapter = new NewFeedRecyclerViewAdapter(list);
        mRecyclerViewNewFeed.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_feed, container, false);
        mRecyclerViewNewFeed = (RecyclerView) v.findViewById(R.id.recycler_view_new_feed);
        return v;
    }

}
