package com.example.musicandroid.community;

import com.example.musicandroid.bean.Singer;
import com.example.musicandroid.util.CallBackData;

import java.util.ArrayList;
import java.util.List;

public class SingerModel {


    public void getSingerListData(String address, CallBackData<List<Singer>> callBackData){

        List<Singer> list = new ArrayList<>();
        list.clear();
        Singer singer = new Singer("IU","韩国人气歌手，人气演员，国名妹妹");

        for (int i = 0; i < 20; i++){
            list.add(singer);
        }

        callBackData.onSuccess(list);

    }
}
