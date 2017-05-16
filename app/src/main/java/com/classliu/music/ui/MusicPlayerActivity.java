package com.classliu.music.ui;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.classliu.music.R;
import com.classliu.music.widget.CircleImageView;
import com.yydcdut.sdlv.Menu;
import com.yydcdut.sdlv.MenuItem;
import com.yydcdut.sdlv.SlideAndDragListView;

import java.util.List;

/**
 * Created by ji_cheng on 2017/5/9.
 */

public class MusicPlayerActivity extends FragmentActivity implements SlideAndDragListView.OnListItemLongClickListener,
        SlideAndDragListView.OnDragListener, SlideAndDragListView.OnSlideListener,
        SlideAndDragListView.OnListItemClickListener, SlideAndDragListView.OnMenuItemClickListener,
        SlideAndDragListView.OnItemDeleteListener, View.OnClickListener {


    private static final String TAG = MusicPlayerActivity.class.getSimpleName();

    private Menu mMenu;
    private List<ApplicationInfo> mAppList;
    private SlideAndDragListView<ApplicationInfo> mListView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_sdlv);
        mContext = this;
        setView();
        initData();
        initMenu();
        initUiAndListener();
    }


    private void setView() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void initData() {
        mAppList = getPackageManager().getInstalledApplications(0);
    }

    public void initMenu() {
        mMenu = new Menu(true, true);
        mMenu.addItem(new MenuItem.Builder().setWidth(200)
                .setBackground(ContextCompat.getDrawable(this, R.drawable.btn_left0))
                .setText("删除")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize(16)
                .build());
    }


    private TextView tvStayScroll;
    private View stayView;
    private ImageView ivCloseMusic;
    private CircleImageView ivBookMusicCover;
    private TextView tvMusicName;

    private TextView tvMusicPlayingTime;
    private SeekBar musicSeekBar;
    private TextView tvMusicAllTime;

    private ImageView ivDeleteMusic;
    private ImageView ivMusicPrevious;
    private ImageView ivPlayStop;
    private ImageView ivNextMusic;

    private ImageView ivPlaymode;
    private TextView tvPlaymode;
    private FrameLayout flMusicPlaymode;


    public void initUiAndListener() {
        mListView = (SlideAndDragListView) findViewById(R.id.lv_edit);
        tvStayScroll = (TextView) findViewById(R.id.tv_stay_scroll);
        View header = LayoutInflater.from(this).inflate(R.layout.music_header, null);
        initHeaderView(header);
        stayView = LayoutInflater.from(this).inflate(R.layout.stay_view, null);

        mListView.addHeaderView(header);
        mListView.addHeaderView(stayView);

        mListView.setMenu(mMenu);

        mListView.setAdapter(mAdapter);
        mListView.setOnListItemLongClickListener(this);
        mListView.setOnDragListener(this, mAppList);
        mListView.setOnListItemClickListener(this);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);

        mListView.setOnListScrollListener(new SlideAndDragListView.OnListScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                makeStayChange(firstVisibleItem);
            }
        });
    }

    private void initHeaderView(View v) {
        ivCloseMusic = (ImageView) v.findViewById(R.id.iv_close_music);
        ivBookMusicCover = (CircleImageView) v.findViewById(R.id.iv_music_cover);
        tvMusicName = (TextView) v.findViewById(R.id.tv_music_name);
        tvMusicPlayingTime = (TextView) v.findViewById(R.id.tv_music_playing_time);
        musicSeekBar = (SeekBar) v.findViewById(R.id.music_seek_bar);
        musicSeekBar.setFocusable(true);
        musicSeekBar.setEnabled(true);
        tvMusicAllTime = (TextView) v.findViewById(R.id.tv_music_all_time);
        ivDeleteMusic = (ImageView) v.findViewById(R.id.iv_delete_music);
        ivMusicPrevious = (ImageView) v.findViewById(R.id.iv_music_previous);
        ivPlayStop = (ImageView) v.findViewById(R.id.iv_play_stop);
        ivNextMusic = (ImageView) v.findViewById(R.id.iv_next_music);
        ivPlaymode = (ImageView) v.findViewById(R.id.iv_playmode);
        tvPlaymode = (TextView) v.findViewById(R.id.tv_playmode);
        flMusicPlaymode = (FrameLayout) v.findViewById(R.id.fl_music_playmode);


        ivCloseMusic.setOnClickListener(this);
        ivMusicPrevious.setOnClickListener(this);
        ivPlayStop.setOnClickListener(this);
        ivNextMusic.setOnClickListener(this);
        flMusicPlaymode.setOnClickListener(this);
        ivDeleteMusic.setOnClickListener(this);
    }


    //悬浮的bar
    private void makeStayChange(int firstVisibleItem) {

        if (firstVisibleItem <= 3) {
            int top = stayView.getTop();
            tvStayScroll.setTranslationY(Math.max(0, top));
        } else if (firstVisibleItem > 3 && firstVisibleItem < 10) {
            tvStayScroll.setTranslationY(0);
        }

    }


    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(MusicPlayerActivity.this).inflate(R.layout.item_custom, null);
                cvh.imgLogo = (ImageView) convertView.findViewById(R.id.img_item_edit);
                cvh.txtName = (TextView) convertView.findViewById(R.id.txt_item_edit);
                cvh.imgLogo2 = (ImageView) convertView.findViewById(R.id.img_item_edit2);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }
            ApplicationInfo item = (ApplicationInfo) this.getItem(position);
            cvh.txtName.setText(item.loadLabel(getPackageManager()));
            cvh.imgLogo.setImageDrawable(item.loadIcon(getPackageManager()));
            cvh.imgLogo2.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_move));

            return convertView;
        }

        class CustomViewHolder {
            public ImageView imgLogo;
            public TextView txtName;
            public ImageView imgLogo2;
        }
    };

    @Override
    public void onListItemLongClick(View view, int position) {
//        boolean bool = mListView.startDrag(position);
//        Toast.makeText(MusicPlayerActivity.this, "onItemLongClick   position--->" + position + "   drag-->" + bool, Toast.LENGTH_SHORT).show();
        Toast.makeText(MusicPlayerActivity.this, "onItemLongClick   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onListItemLongClick   " + position);
    }

    @Override
    public void onDragViewStart(int position) {
        Toast.makeText(MusicPlayerActivity.this, "onDragViewStart   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewStart   " + position);
    }

    @Override
    public void onDragViewMoving(int position) {
//        Toast.makeText(DemoActivity.this, "onDragViewMoving   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewMoving   " + position);
    }

    @Override
    public void onDragViewDown(int position) {
        Toast.makeText(MusicPlayerActivity.this, "onDragViewDown   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewDown   " + position);
    }

    @Override
    public void onListItemClick(View v, int position) {
        Toast.makeText(MusicPlayerActivity.this, "onItemClick   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onListItemClick   " + position);
    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {
        Toast.makeText(MusicPlayerActivity.this, "onSlideOpen   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideOpen   " + position + "  direction--->" + direction);
    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {
        Toast.makeText(MusicPlayerActivity.this, "onSlideClose   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideClose   " + position + "  direction--->" + direction);
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        Log.i(TAG, "onMenuItemClick   " + itemPosition + "   " + buttonPosition + "   " + direction);
        switch (direction) {
            case MenuItem.DIRECTION_RIGHT:
               /* switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_SCROLL_BACK;
                    case 1:*/
                return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
            //}
        }
        return Menu.ITEM_NOTHING;
    }

    @Override
    public void onItemDelete(View view, int position) {
        mAppList.remove(position - mListView.getHeaderViewsCount());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_stop:

                break;
            case R.id.iv_close_music:

                onBackPressed();
                break;
            case R.id.iv_music_previous:

                break;
            case R.id.iv_next_music:

                break;
            case R.id.iv_delete_music:

                break;
            case R.id.fl_music_playmode:

                break;
        }
    }
}
