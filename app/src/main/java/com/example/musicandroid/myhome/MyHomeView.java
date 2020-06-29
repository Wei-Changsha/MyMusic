package com.example.musicandroid.myhome;

import com.example.musicandroid.base.BaseView;
import com.example.musicandroid.data.UserSongListBean;

import java.util.List;

public interface MyHomeView extends BaseView {
    void showClickResult(String type, String name , String songlist,String address );

    //用来显示“新建歌单”的歌单，是一个关于歌单的列表
    void setMusicList(List<UserSongListBean> songLists);

}
