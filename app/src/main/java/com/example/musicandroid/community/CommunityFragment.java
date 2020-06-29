package com.example.musicandroid.community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.R;
import com.example.musicandroid.base.BaseFragment;
import com.example.musicandroid.bean.Singer;
import com.example.musicandroid.discovery.DiscoveryListAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class CommunityFragment extends BaseFragment<SingerView,SingerPresenter> implements SingerView {

    private TabLayout tabLayout;
    private SingerAdapter adapter;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);
        findView(view);
        initList();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SingerPresenter presenter = new SingerPresenter();
        presenter.attachView(CommunityFragment.this);
        presenter.serSingerRecyclerView();
    }

    private static final String ARG_SECTION = "section";
    //实例化碎片的构造方法
    public static CommunityFragment newInstance(String setion) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, setion);
        fragment.setArguments(args);
        return fragment;
    }


    void findView(View view){
        tabLayout = view.findViewById(R.id.singer_tab_layout);
        recyclerView = view.findViewById(R.id.singer_recycler_view);

    }


    //选项卡  选择歌手类型
    void initList(){

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        //TODO 选中全部歌手逻辑
                        Toast.makeText(getContext(),"all singer",Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        //TODO 选中男歌手
                        Toast.makeText(getContext(),"man singer",Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        //TODO 选中女歌手

                        Toast.makeText(getContext(),"woman singer",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //TODO 选中组合歌手
                        Toast.makeText(getContext(),"group singer",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public SingerPresenter createPresenter() {
        return new SingerPresenter();
    }

    @Override
    public SingerView createView() {
        return this;
    }

    @Override
    public void showSingerList(List<Singer> list) {

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SingerAdapter(list);
        adapter.notifyDataSetChanged();

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        Log.d("rrr", "  000 isEmpty = "+ list.size());



    }
}
