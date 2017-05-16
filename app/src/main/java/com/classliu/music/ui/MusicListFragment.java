package com.classliu.music.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.classliu.music.R;
import com.classliu.music.adapter.AppInfoRecylerAdapter;

/**
 * Created by ji_cheng on 2017/5/15.
 */

public class MusicListFragment extends Fragment {

    public final static String TAG = "MusicListFragment";

    public MusicListFragment() {

    }

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static MusicListFragment getInstance() {
        MusicListFragment fragment = new MusicListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initUiAndListener(view);
        super.onViewCreated(view, savedInstanceState);
    }




    private TextView tvStayScroll;
    private RecyclerView recyclerView;

    private AppInfoRecylerAdapter adapter;
    public void initUiAndListener(View v) {

        tvStayScroll = (TextView) v.findViewById(R.id.tv_stay_scroll);


        recyclerView = (RecyclerView) v.findViewById(R.id.recyler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new AppInfoRecylerAdapter(context);
        recyclerView.setAdapter(adapter);

    }
}
