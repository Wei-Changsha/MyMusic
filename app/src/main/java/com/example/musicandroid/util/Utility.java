package com.example.musicandroid.util;

import com.example.musicandroid.data.Rank;
import com.example.musicandroid.data.UserSongListBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Utility {


    //获取用户所有歌单
    public static void getAllUserList(String response, List<UserSongListBean> list){
        Gson gson = new Gson();
        list = gson.fromJson(response,new TypeToken<List<UserSongListBean>>(){}.getType());
    }



    //利用songId获取歌曲实体获取歌曲实体
    public static UserSongListBean.SongBean getSong(String response){
        UserSongListBean.SongBean songBean ;
        Gson gson = new Gson();
        songBean = gson.fromJson(response,UserSongListBean.SongBean.class);
        return songBean;
    }

    //利用songId获取歌曲实体获取歌曲实体
    public static List<UserSongListBean.SongBean> getSongByName(String respoanse){
        List<UserSongListBean.SongBean> list;

        Gson gson = new Gson();
        list = gson.fromJson(respoanse,new TypeToken<List<UserSongListBean.SongBean>>(){}.getType());
        return list;
    }


    //获取排行榜列表获取排行榜
    //概要：发现功能中，有一个排行榜功能，应该利用这个请求获取
    //请求方法：GET
    //url：`/search/getRank`
    //返回值：包括了一个Rank的列表，关于含义请查看类的解释
    //object示例：

    public static  List<Rank> getRankList(String response){
        List<Rank> list = new ArrayList<>();
        Gson gson = new Gson();
        list = gson.fromJson(response,new TypeToken<List<Rank>>(){}.getType());
        return list;
    }



}
