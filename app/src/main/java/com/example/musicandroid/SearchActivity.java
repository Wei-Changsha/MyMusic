package com.example.musicandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.musicandroid.data.UserSongListBean;
import com.example.musicandroid.discovery.rank.RankActivity;
import com.example.musicandroid.util.HttpUtil;
import com.example.musicandroid.util.Utility;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{



    private SearchView sv;
    private ListView lv;
    // 自动完成的列表
    private  String[] mStrings = { "aaaaa", "bbbbbb", "cccccc", "ddddddd" };

    private List<UserSongListBean.SongBean> songBeanList;

    private List<String> hostroyList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String[] strings = new String[hostroyList.size()];
        hostroyList.toArray(strings);

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings));
        lv.setTextFilterEnabled(true);//设置lv可以被过虑
        sv = (SearchView) findViewById(R.id.sv);
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("请输入歌曲名");

        ImageView back = findViewById(R.id.search_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // 实际应用中应该在该方法内执行实际查询
        // 此处仅使用Toast显示用户输入的查询内容
        hostroyList.add(query);
        Toast.makeText(this, "您的选择是:" + query, Toast.LENGTH_SHORT).show();
        search(query);

        return false;

    }

    public void search(String name){
        String url = HttpUtil.baseUrl +  "/search/getSong/name=" + name;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        e.printStackTrace();
                        Toast.makeText(SearchActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String responseText=response.body().string();
                songBeanList = Utility.getSongByName(responseText);


            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       // Toast.makeText(SearchActivity.this, "textChange--->" + newText, 1).show();
        if (TextUtils.isEmpty(newText)) {
            // 清除ListView的过滤
            lv.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            lv.setFilterText(newText);
        }
        return true;


    }
}
