package com.example.musicandroid.discovery;

import com.example.musicandroid.bean.SongList;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.util.CallBackData;
import com.example.musicandroid.util.HttpUtil;
import com.example.musicandroid.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DiscoveryModel {

    public void getListData(String address, final CallBackData<List<UserSongListBean>> callBackData){


        final List<UserSongListBean> list = new ArrayList<>();

        //获取所有系统歌单
        address = HttpUtil.baseUrl + "/search/getList";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText=response.body().string();
                Utility.getAllUserList(responseText,list);
                callBackData.onSuccess(list);
            }
        });

        //获取系统歌单

//        List<SongList> list =  new ArrayList<>();
//        list.clear();
//        SongList songList = new SongList("iu的歌");
//        for (int i = 0;i < 10; i++){
//            list.add(songList);
//        }
//
//        callBackData.onSuccess(list);

    }
}
