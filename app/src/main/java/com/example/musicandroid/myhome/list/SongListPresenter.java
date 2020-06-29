package com.example.musicandroid.myhome.list;

import com.example.musicandroid.base.BasePresenter;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.util.CallBackData;

import java.util.List;

public class SongListPresenter extends BasePresenter<SongListView> {
    private SongListModel songListModel;

    public SongListPresenter() {
        this.songListModel = new SongListModel();
    }

    public void setSongRecyclerView(){
        this.songListModel.getSongListData("", new CallBackData<List<UserSongListBean.SongBean>>() {

            @Override
            public void onSuccess(List<UserSongListBean.SongBean> songBeans) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailed() {

            }

            @Override
            public void onFinish() {

            }
        });

    }
}
