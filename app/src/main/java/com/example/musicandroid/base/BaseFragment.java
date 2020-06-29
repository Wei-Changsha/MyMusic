package com.example.musicandroid.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment <V extends BaseView,P extends BasePresenter<V>> extends Fragment {

    private P presenter;
    private V view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.presenter == null){
            this.presenter = createPresenter();
        }
        if (this.view == null){
            this.view = createView();
        }

        if (this.presenter != null && this.view != null){
            this.presenter.attachView(this.view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
