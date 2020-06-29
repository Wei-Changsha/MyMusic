package com.example.musicandroid.community;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicandroid.R;
import com.example.musicandroid.bean.Singer;

import java.util.List;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.ViewHolder> {

    List<Singer> list;

    public SingerAdapter(List<Singer> list){
        this.list = list;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView singerPic;
        TextView name;
        TextView intro;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            singerPic = itemView.findViewById(R.id.singer_pic_list);
            name = itemView.findViewById(R.id.singer_name_list);
            intro = itemView.findViewById(R.id.singer_intro_list);
        }
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_singer_list, parent,false);

        return new SingerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Singer singer = list.get(position);

        holder.name.setText("IU");
        holder.intro.setText("韩国人气歌手，演员");
        //holder.singerPic.
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
