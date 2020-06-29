package com.example.musicandroid.community;

import com.example.musicandroid.base.BaseView;
import com.example.musicandroid.bean.Singer;

import java.util.List;

public interface SingerView extends BaseView {
    void showSingerList(List<Singer> list);
}
