package com.example.musicandroid.community;

import com.example.musicandroid.base.BasePresenter;
import com.example.musicandroid.bean.Singer;
import com.example.musicandroid.util.CallBackData;

import java.util.List;

public class SingerPresenter extends BasePresenter<SingerView> {

    private SingerModel singerModel;

    public SingerPresenter(){
        this.singerModel = new SingerModel();
    }


    public void serSingerRecyclerView(){
        this.singerModel.getSingerListData("", new CallBackData<List<Singer>>() {
            @Override
            public void onSuccess(List<Singer> list) {
                if (getView() != null){
                    getView().showSingerList(list);
                }
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
