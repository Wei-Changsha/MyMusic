package com.example.musicandroid.discovery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.R;
import com.example.musicandroid.data.UserSongListBean;

import java.util.List;

public class DiscoveryListAdapter extends RecyclerView.Adapter<DiscoveryListAdapter.ViewHolder> {

    List<UserSongListBean> list ;


    DiscoveryListAdapter(List<UserSongListBean> list){
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title ;
        ImageView pic1;
        TextView text1;
        ImageView pic2;
        TextView text2;
        ImageView pic3;
        TextView text3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.discovery_list_title);
            pic1 = itemView.findViewById(R.id.discovery_list_pic1);
            text1 = itemView.findViewById(R.id.discovery_list_text1);
            pic2 = itemView.findViewById(R.id.discovery_list_pic2);
            text2 = itemView.findViewById(R.id.discovery_list_text2);
            pic3 = itemView.findViewById(R.id.discovery_list_pic3);
            text3 = itemView.findViewById(R.id.discovery_list_text3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_discovery_list, parent,false);

        return new DiscoveryListAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserSongListBean songList = list.get(position);
        holder.title.setText(songList.getStyle());

//        int songId1 = songList.getListId().get(0);
//        int songId2 = songList.getListId().get(1);
//        int songId3 = songList.getListId().get(2);
        UserSongListBean.SongBean song0 = (UserSongListBean.SongBean) songList.getSongs().get(0);
        UserSongListBean.SongBean song1 = (UserSongListBean.SongBean) songList.getSongs().get(1);
        UserSongListBean.SongBean song2 = (UserSongListBean.SongBean) songList.getSongs().get(2);

        holder.text1.setText(song0.getName());
        holder.text2.setText(song1.getName());
        holder.text3.setText(song2.getName());

        //TODO  到歌单库里根据id找到歌曲 设置歌曲封面和歌曲名称

       // Song songpp = new Song(11,"IU","","");
       // holder.pic1.setImageDrawable(R.drawable.pic);
        holder.text1.setText("IU的歌1");
        holder.text2.setText("IU的歌2");
        holder.text3.setText("IU的歌3");






    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
