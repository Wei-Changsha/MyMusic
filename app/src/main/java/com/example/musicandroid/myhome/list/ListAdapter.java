package com.example.musicandroid.myhome.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.R;
import com.example.musicandroid.data.UserSongListBean;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<UserSongListBean.SongBean> list = new ArrayList<>();

    public interface OnItemOnClickListener{
        void onItemOnClick(View view,int pos);
        void onItemLongOnClick(View view ,int pos);
    }

    //设置监听的方法和声明了一个这个接口的内部变量    供外部来设置监听
    private OnItemOnClickListener mOnItemOnClickListener;
    public void setOnItemClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }

    //删除函数
    public void removeItem(int pos){
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    public ListAdapter(List<UserSongListBean.SongBean> list){
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView listRank;
        TextView songName;
        TextView singer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listRank = itemView.findViewById(R.id.song_rank);
            songName = itemView.findViewById(R.id.list_song_name);
            singer = itemView.findViewById(R.id.list_song_singer);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserSongListBean.SongBean song = list.get(position);


        String singerName;

        /*TODO
         *song.getSingerId()  根据歌手的id去数据库中查找歌手名字
         */
        if (song.getSongId() == 11){
            singerName = "IU";
        }else {
            singerName = "kong";
        }
        holder.singer.setText(singerName);//填歌手的名字
        holder.songName.setText(song.getName());
        holder.listRank.setText(position + 1 + "");

    }

    @Override
    public int getItemCount() {
        if (list != null)
             return this.list.size();
        else return 0;

    }


}
