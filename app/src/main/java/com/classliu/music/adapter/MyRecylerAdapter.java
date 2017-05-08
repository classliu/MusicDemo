package com.classliu.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classliu.music.R;
import com.classliu.music.bean.Modle;
import com.classliu.music.holder.ItemHodler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ji_cheng on 2017/5/5.
 */

public class MyRecylerAdapter extends RecyclerView.Adapter<ItemHodler> {

    private Context context;
    private List<Modle> dataLsit =  new ArrayList<>();

    public MyRecylerAdapter(Context context, List<Modle> dataLsit) {
        this.context = context;
        this.dataLsit = dataLsit;
    }

    @Override
    public ItemHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.itemview_re,null);
        return new ItemHodler(rootView);
    }

    @Override
    public void onBindViewHolder(ItemHodler holder, int position) {

        Modle modle = dataLsit.get(position);
        holder.textView.setText(modle.text);

    }

    @Override
    public int getItemCount() {
        return dataLsit.size();
    }
}
