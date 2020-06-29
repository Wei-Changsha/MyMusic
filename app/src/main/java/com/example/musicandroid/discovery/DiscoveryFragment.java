package com.example.musicandroid.discovery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.R;
import com.example.musicandroid.base.BaseFragment;
import com.example.musicandroid.bean.SongList;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.discovery.rank.RankActivity;
import com.example.musicandroid.myhome.list.MyhomeListActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DiscoveryFragment extends BaseFragment<DiscoveryView,DiscoveryPresenter> implements DiscoveryView, View.OnClickListener  {

    private static final String ARG_SECTION = "section";
    private RecyclerView recyclerView;
    private DiscoveryListAdapter adapter;
    private ImageView recommendIV, songListIV, rankIV, zhuanJiIV;

    private List<UserSongListBean> allList = new ArrayList<>();

    private static UserSongListBean rankList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery,container,false);
        findView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rankList = new UserSongListBean();
        rankList.setStyle("排行榜");

        DiscoveryPresenter presenter = new DiscoveryPresenter();
        presenter.attachView(DiscoveryFragment.this);
        presenter.setListData();
    }


    //实例化碎片的构造方法
    public static DiscoveryFragment newInstance(String setion) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, setion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId()){
            case R.id.recommend:
                //TODO
                break;
            case R.id.song_list:
                Log.d("zzz","  song list!");
                intent=new Intent(getActivity(), RankActivity.class);
                intent.putExtra("allSongList", (Serializable) allList);
                startActivity(intent);
                break;
            case R.id.rank:
                intent=new Intent(getActivity(), MyhomeListActivity.class);
                intent.putExtra("userSongList", rankList);
                startActivity(intent);
                break;
            case R.id.zhuan_ji:
                //TODO 专辑相关信息
                break;
        }
    }

    @Override
    public DiscoveryPresenter createPresenter() {
        return new DiscoveryPresenter();
    }

    @Override
    public DiscoveryView createView() {
        return this;
    }

    @Override
    public void showDiscoveryList(List<UserSongListBean> songLists) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DiscoveryListAdapter(songLists);
        adapter.notifyDataSetChanged();

        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        this.allList = songLists;

    }


    public void findView(View view){
        recyclerView = view.findViewById(R.id.discovery_recycler_view);

        view.findViewById(R.id.recommend).setOnClickListener(this);
        view.findViewById(R.id.song_list).setOnClickListener(this);
        view.findViewById(R.id.rank).setOnClickListener(this);
        view.findViewById(R.id.zhuan_ji).setOnClickListener(this);

    }
}
