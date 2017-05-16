package com.classliu.music.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.classliu.music.R;

/**
 * Created by ji_cheng on 2017/5/16.
 */

public class AppInfoHolder extends RecyclerView.ViewHolder {

    public ImageView imgLogo;
    public TextView txtName;
    public ImageView imgLogo2;

    public AppInfoHolder(View itemView) {
        super(itemView);

        imgLogo = (ImageView) itemView.findViewById(R.id.img_item_edit);
        txtName = (TextView) itemView.findViewById(R.id.txt_item_edit);
        imgLogo2 = (ImageView) itemView.findViewById(R.id.img_item_edit2);

    }


}
