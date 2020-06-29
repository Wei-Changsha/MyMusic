package com.example.musicandroid.myhome;


import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.util.CallBackData;
import com.example.musicandroid.util.Utility;
import com.example.musicandroid.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyHomeModel  {

    public void getRecylerViewData(String address, final CallBackData<List<UserSongListBean>> callBackData){

        final List<UserSongListBean> list = new ArrayList<>();

        address = HttpUtil.baseUrl + "/collect/userId=" + HttpUtil.userId;
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseText=response.body().string();

                //解析数据
                Utility.getAllUserList(responseText,list);
                callBackData.onSuccess(list);
            }
        });

//        List<UserSongListBean> list = new ArrayList<>();
//        list.clear();
//        MusicTest music1 = new MusicTest("iu");
//        for (int i = 0; i< 21;i++){
//            list.add(music1);
//        }
//        Log.d(" llll ", " 2222 size = "+ list.size());


    }


}
