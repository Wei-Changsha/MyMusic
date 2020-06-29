package com.example.musicandroid.myhome;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicandroid.R;
import com.example.musicandroid.data.UserSongListBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<UserSongListBean> list;
    private Context mContent;
    private ImageView imageView;
    private Activity activity;


    public UserListAdapter(List<UserSongListBean> list, Activity activity){
        this.list = list;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;

        TextView singer;
        TextView songNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            singer = itemView.findViewById(R.id.singer_name);
            songNum = itemView.findViewById(R.id.song_num);
            imageView = itemView.findViewById(R.id.singer_music_list_image);
        }
    }


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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContent == null){
            mContent = parent.getContext();
        }

        View view = LayoutInflater.from(mContent).inflate(R.layout.item_myhome_musiclist,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        UserSongListBean musicTest = list.get(position);
        holder.singer.setText(musicTest.getStyle());
        holder.songNum.setText(musicTest.getSongs().size() + "首");

        //如果歌单不为空，，就把第一首歌的图片做背景，否则为默认图片
        if (musicTest.getSongs().size() != 0){
            String imageUrl = list.get(0).getPic();
            Glide.with(activity).load(imageUrl).into(imageView);
        }


        if(mOnItemOnClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemOnClickListener.onItemOnClick(holder.itemView,position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemOnClickListener.onItemLongOnClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
