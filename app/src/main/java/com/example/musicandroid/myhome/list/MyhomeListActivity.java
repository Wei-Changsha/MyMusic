package com.example.musicandroid.myhome.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musicandroid.R;
import com.example.musicandroid.base.BaseActivity;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.play.PlayActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class MyhomeListActivity extends BaseActivity<SongListView,SongListPresenter> implements SongListView, View.OnClickListener  {

    private RecyclerView recyclerView;
    private ListAdapter adapter;

    //private List<Song> list = new ArrayList<>();

    private ImageView songListPic ;
    private TextView songListStyle;

    private List<UserSongListBean.SongBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhome_list);
        initView();

        final UserSongListBean songListBean = (UserSongListBean) getIntent().getSerializableExtra("userSongList");


        //getSong(songListBean);

         list = songListBean.getSongs();

        if (list !=null &&list.size() != 0){
            Glide.with(MyhomeListActivity.this).load(songListBean.getPic()).into(songListPic);
        }
        songListStyle.setText(songListBean.getStyle());


        GridLayoutManager layoutManager = new GridLayoutManager(MyhomeListActivity.this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(list);
        adapter.notifyDataSetChanged();
        //加分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(MyhomeListActivity.this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListAdapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {

                UserSongListBean.SongBean song = list.get(pos);
                Intent intent=new Intent(MyhomeListActivity.this, PlayActivity.class);
                intent.putExtra("song", (Serializable) song);
                intent.putExtra("songList", (Serializable) list);
                startActivity(intent);

            }

            @Override
            public void onItemLongOnClick(View view, int pos) {

            }
        });

        SongListPresenter songListPresenter = new SongListPresenter();
        songListPresenter.attachView(this);
        songListPresenter.setSongRecyclerView();
    }


//    public void getSong(UserSongListBean songListBean){
//        if (songListBean.getSongs() != null){
//            List<UserSongListBean.SongBean> songs = songListBean.getSongs();
//
//            for (UserSongListBean.SongBean song: songs){
//                Song newSong = new Song(song.getSongId(),song.getName(),song.getIntroduction(),song.getUrl());
//                this.list.add(newSong);
//            }
//        }
//
//    }

    @Override
    public SongListPresenter createPresenter() {
        return new SongListPresenter();
    }

    @Override
    public SongListView createView() {
        return this;
    }

    public void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        recyclerView  = findViewById(R.id.song_list_recycler_view);
        songListPic = findViewById(R.id.song_list_pic);
        songListStyle = findViewById(R.id.song_list_style);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.myhome_list_menu,menu);

        return true;

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent;

        switch (item.getItemId()){

            case R.id.list_more:{
                Toast.makeText(this,"更多",Toast.LENGTH_SHORT).show();
                //跳转到“查找”
                break;
            }
            case R.id.list_search:{
                Toast.makeText(this,"查找",Toast.LENGTH_SHORT).show();
                //跳转到“查找”
                break;
            }

            case android.R.id.home:{
                finish();
                break;
            }

            default:

        }

        return true;
    }


    @Override
    public void onClick(View view) {

    }


    @Override
    public void showSongList(List<UserSongListBean.SongBean> list) {

    }
}
