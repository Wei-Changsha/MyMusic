package com.example.musicandroid.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<V extends BaseView,P extends BasePresenter<V>> extends AppCompatActivity {
    private P presenter;
    private V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (this.presenter == null){
//            this.presenter = createPresenter();
//        }
//        if (this.view == null){
//            this.view = createView();
//        }
//
//        if (this.presenter != null && this.view != null){
//            this.presenter.attachView(this.view);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {


        return super.onCreateView(name, context, attrs);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter != null && this.view != null){
            this.presenter.detachView();
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public abstract P createPresenter();

    public abstract V createView();
}
