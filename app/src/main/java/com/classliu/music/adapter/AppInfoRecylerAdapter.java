package com.classliu.music.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.classliu.music.R;
import com.classliu.music.holder.AppInfoHolder;

import java.util.List;

/**
 * Created by ji_cheng on 2017/5/16.
 */

public class AppInfoRecylerAdapter extends RecyclerView.Adapter<AppInfoHolder> {

    private Context context;
    private List<ApplicationInfo> mAppList;

    public AppInfoRecylerAdapter(Context context) {
        this.context = context;
        mAppList = context.getPackageManager().getInstalledApplications(0);
    }

    @Override
    public AppInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_custom, null);
        return new AppInfoHolder(v);

    }

    @Override
    public void onBindViewHolder(AppInfoHolder holder, int position) {
        ApplicationInfo item = (ApplicationInfo) mAppList.get(position);
        holder.txtName.setText(item.loadLabel(context.getPackageManager()));
        holder.imgLogo.setImageDrawable(item.loadIcon(context.getPackageManager()));
        holder.imgLogo2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_move));
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
}
