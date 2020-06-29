package com.example.musicandroid.myhome;

import android.util.Log;

import com.example.musicandroid.base.BasePresenter;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.util.CallBackData;


import java.util.List;

public class MyHomePresenter extends BasePresenter<MyHomeView> {
    private MyHomeModel myHomeModel;

    public MyHomePresenter(){
        this.myHomeModel = new MyHomeModel();
    }


    public void setHomeRecyclerView(){

        this.myHomeModel.getRecylerViewData( "", new CallBackData<List<UserSongListBean>>() {
            @Override
            public void onSuccess(List list) {

                if (getView() != null){
                    getView().setMusicList(list);
                    Log.d("llll", "  333 size = "+ list.size());
                }

                Log.d("llll", "  ggg size = "+ list.size());
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


    public void click(String type, String url, String name, String songList ){
         //this.myHomeModel.click(type,name,songList,url);

        if (getView() != null){
            getView().showClickResult(type,name,songList,url);
        }

    }



}
