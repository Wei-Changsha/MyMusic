package com.example.musicandroid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.musicandroid.community.CommunityFragment;
import com.example.musicandroid.discovery.DiscoveryFragment;
import com.example.musicandroid.myhome.MyFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//ViewPager的适配器  主页面
public class ViewAdapter extends FragmentPagerAdapter implements Serializable {

    List<Fragment> fragmentList=new ArrayList<>();
    String[] tabs = {"我的","发现","社区"};
    public ViewAdapter(@NonNull FragmentManager fm){
        super(fm);
    }

    public ViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    //初始化碎片集合
    private void initFragment(){
        MyFragment myFragment = MyFragment.newInstance("this is bookshelf");
        DiscoveryFragment discoveryFragment = DiscoveryFragment.newInstance("this is bookshelf");
        CommunityFragment communityFragment = CommunityFragment.newInstance("this is bookshelf");
        fragmentList.add(myFragment);
        fragmentList.add(discoveryFragment);
        fragmentList.add(communityFragment);
    }
    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        initFragment();
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    //Tab标题为对应页通过getPageTitle()返回的文本
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
