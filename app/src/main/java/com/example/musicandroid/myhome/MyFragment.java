package com.example.musicandroid.myhome;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.musicandroid.R;
import com.example.musicandroid.base.BaseFragment;
import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.myhome.list.MyhomeListActivity;
import com.example.musicandroid.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;


public class MyFragment extends BaseFragment<MyHomeView, MyHomePresenter> implements MyHomeView, View.OnClickListener {

    private static final String ARG_SECTION = "section";


    private UserListAdapter adapter;

    private RecyclerView recyclerView;
    private MyHomePresenter presenter;

    private List<UserSongListBean> list = new ArrayList<>();

    private static UserSongListBean localSongList;
    private static UserSongListBean starSongList;
    private static UserSongListBean favorSongList;
    private static UserSongListBean recentPlaySongList;


    private GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);

    //实例化碎片的构造方法
    public static MyFragment newInstance(String setion) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION, setion);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

        findView(view);

        initMyList();



        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserListAdapter(list,getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        listenToUserListAdapter(adapter);


        return view;
    }


    private void initMyList()  {
        localSongList = new UserSongListBean();
        localSongList.setStyle("本地音乐");
        starSongList = new UserSongListBean();
        starSongList.setStyle("我的收藏");
        favorSongList = new UserSongListBean();
        favorSongList.setStyle("我最喜欢的");
        recentPlaySongList = new UserSongListBean();
        recentPlaySongList.setStyle("最近播放");


       // postNewList(HttpUtil.userId,"","本地音乐");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyHomePresenter presenter = new MyHomePresenter();
        presenter.attachView(MyFragment.this);
        presenter.setHomeRecyclerView();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (this.presenter != null && this != null){
            this.presenter.detachView();
        }
    }

    @Override
    public MyHomePresenter createPresenter() {
        return new MyHomePresenter();
    }

    @Override
    public MyHomeView createView() {
        return this;
    }


    @Override
    public void showClickResult(String type, String name, String songlist, String address) {

        //getPresenter().test(list);


    }

    @Override
    public void setMusicList(List songLists) {


        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserListAdapter(songLists,getActivity());
        adapter.notifyDataSetChanged();

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        Log.d("llll", "  000 isEmpty = "+ String.valueOf(songLists.size()));
        this.list = songLists;


    }


    public void findView(View view){

        recyclerView = view.findViewById(R.id.my_home_recycler_view);

        //顶部4个选项
        view.findViewById(R.id.my_localMusic).setOnClickListener(this);
        view.findViewById(R.id.my_star).setOnClickListener(this);
        view.findViewById(R.id.my_downlandMusic).setOnClickListener(this);
        view.findViewById(R.id.my_newSong).setOnClickListener(this);

        //我的音乐
        view.findViewById(R.id.my_myMusic_more).setOnClickListener(this);
        view.findViewById(R.id.my_myMusic_favor).setOnClickListener(this);
        view.findViewById(R.id.my_myMusic_recentPlay).setOnClickListener(this);
        view.findViewById(R.id.my_myMusic_newRecommend).setOnClickListener(this);

        //创建歌单
        view.findViewById(R.id.my_create_new).setOnClickListener(this);

    }





    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        Intent intent;
        switch (itemId){
            case R.id.my_localMusic:{
                Log.d("llll","  localMusic!");
                intent=new Intent(getActivity(),MyhomeListActivity.class);
                intent.putExtra("userSongList", localSongList);
                startActivity(intent);

                break;
            }
            case R.id.my_star:{
                Log.d("llll","  star!");
                intent=new Intent(getActivity(),MyhomeListActivity.class);
                intent.putExtra("userSongList", starSongList);
                startActivity(intent);
                break;
            }
            case R.id.my_downlandMusic:{
                break;
            }
            case R.id.my_newSong:{
                break;
            }
            case R.id.my_create_new:{

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view1 = View.inflate(getContext(),R.layout.create_song_list,null);
                final EditText newName = view1.findViewById(R.id.edit_new_name);
                final EditText newAuthor = view1.findViewById(R.id.edit_new_author);
                final Button newTime = view1.findViewById(R.id.new_time);

                builder.setTitle("新建歌单").setView(view1);


                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DATE);

                newTime.setText(year + "-" + month + "-" + day);

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO 在这里存储歌单数据

                                String saveName = newName.getText().toString();
                                String saveAuthor = newAuthor.getText().toString();

                                //post请求
                                try {
                                    postNewList(HttpUtil.userId,"",saveName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                List<UserSongListBean.SongBean> list1 = new ArrayList<>();

                                UserSongListBean songList = new UserSongListBean(saveName,list1);
                                list.add(songList);

                                //更新“我的” 用户歌单列表
                               //GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
//                                recyclerView.setLayoutManager(layoutManager);
//                                adapter = new UserListAdapter(list,getActivity());
//                                adapter.notifyDataSetChanged();
//                                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//                                recyclerView.setAdapter(adapter);


                            }
                        }).setNegativeButton("取消",null)
                        .create().show();




//                //选择创建时间
//                newTime.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Calendar calendar=Calendar.getInstance();
//                        int year=calendar.get(Calendar.YEAR);
//                        int month=calendar.get(Calendar.MONTH);
//                        int day=calendar.get(Calendar.DATE);
//
//                        newTime.setText(year + "-" + month + "-" + day);
//                        new DatePickerDialog(getContext(),R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//
//                                i1 ++;
//                                String alarm_date=i+"年"+i1 +"月"+i2+"日";
//
//                                newTime.setText(alarm_date);
//                            }
//                        },year,month,day).show();
//                    }
//                });

//                newNot.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });


                break;
            }
            case R.id.my_myMusic_favor:{
                intent=new Intent(getActivity(),MyhomeListActivity.class);
                intent.putExtra("userSongList", favorSongList);
                startActivity(intent);
                break;
            }
            case R.id.my_myMusic_recentPlay:{
                intent=new Intent(getActivity(),MyhomeListActivity.class);
                intent.putExtra("userSongList",recentPlaySongList);
                startActivity(intent);
                break;
            }
            case R.id.my_myMusic_newRecommend:{
                break;
            }
            case R.id.my_myMusic_more:{
                Log.d("llll","  create more!");
                break;
            }


        }
    }

    public void listenToUserListAdapter(final UserListAdapter adapter){

        adapter.setOnItemClickListener(new UserListAdapter.OnItemOnClickListener() {
            @Override
            public void onItemOnClick(View view, int pos) {

                UserSongListBean songListBean = list.get(pos);

                Intent intent=new Intent(getActivity(),MyhomeListActivity.class);
                intent.putExtra("userSongList",songListBean);
                startActivity(intent);
            }

            @Override
            public void onItemLongOnClick(View view, int pos) {

            }
        });

    }

    public void postNewList(int userId, String pic, String style) throws JSONException, IOException {

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(0);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId",userId);//用户id
        jsonObject.put("pic",pic);
        jsonObject.put("style",style);
        jsonObject.put("songs",jsonArray);

        jsonObject.toString();


        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://www.baidu.com/";

//        HttpUtil.baseUrl + "/collect/addCollect"
                Response response = null;
                try {
                    response = HttpUtil.post(url,jsonObject.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();



    }
}
