package com.example.musicandroid.util;

public interface CallBackData<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}
