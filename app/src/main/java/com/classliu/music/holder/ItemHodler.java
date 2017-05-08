package com.classliu.music.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classliu.music.R;

/**
 * Created by ji_cheng on 2017/5/5.
 */

public class ItemHodler extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView textView;


    public ItemHodler(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageview);
        textView = (TextView) itemView.findViewById(R.id.textview);
    }
}
