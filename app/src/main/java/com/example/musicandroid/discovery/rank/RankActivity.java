package com.example.musicandroid.discovery.rank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.musicandroid.R;
import com.example.musicandroid.data.Rank;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.myhome.list.ListAdapter;
import com.example.musicandroid.play.PlayActivity;
import com.example.musicandroid.util.HttpUtil;
import com.example.musicandroid.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class RankActivity extends AppCompatActivity implements View.OnClickListener  {


    private ListAdapter adapter;
    private List<UserSongListBean.SongBean> list = new ArrayList<>();
    private List<Rank> rankList = new ArrayList<>();

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank_list_layout);
        initView();
        getRankList();
        //list = (List<UserSongListBean>) getIntent().getSerializableExtra("allSongList");

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);


        //GridLayoutManager layoutManager = new GridLayoutManager(MyhomeListActivity.this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(list);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListAdapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {

                UserSongListBean.SongBean song = list.get(pos);
                Intent intent=new Intent(RankActivity.this, PlayActivity.class);
                intent.putExtra("song", (Serializable) song);
                intent.putExtra("songList", (Serializable) list);
                startActivity(intent);

            }

            @Override
            public void onItemLongOnClick(View view, int pos) {


            }
        });


    }

//    public void listenToUserListAdapter(final UserListAdapter adapter){
//
//        adapter.setOnItemClickListener(new UserListAdapter.OnItemOnClickListener() {
//            @Override
//            public void onItemOnClick(View view, int pos) {
//
//                UserSongListBean songListBean = list.get(pos);
//                Intent intent=new Intent(RankActivity.this, MyhomeListActivity.class);
//                intent.putExtra("userSongList",songListBean);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onItemLongOnClick(View view, int pos) {
//
//            }
//        });
//
//    }


    private void getRankList(){
        String url = HttpUtil.baseUrl + "/search/getRank";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        Toast.makeText(RankActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText=response.body().string();
                rankList = Utility.getRankList(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //遍历榜单  根据songID查找歌曲  加入Lst《song》 更新UI

                        for (Rank rankSong: rankList){
                            int id = rankSong.getSongId();
                            getSong(id);
                        }


                    }
                });

            }
        });
    }


    //根据id查找歌曲
    private void getSong(int songId){
        String url = HttpUtil.baseUrl + "/song/songId=" + songId;

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        Toast.makeText(RankActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String responseText=response.body().string();
                UserSongListBean.SongBean songBean = Utility.getSong(responseText);
                list.add(songBean);

            }
        });

    }


    public void initView(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

        recyclerView  = findViewById(R.id.rank_recycler_view);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
            default:

        }

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){


        }
    }

}
