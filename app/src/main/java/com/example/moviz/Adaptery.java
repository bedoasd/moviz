package com.example.moviz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    Context mcontext    ;
    List<MovieModel>mData;

    public Adaptery(Context mcontext, List<MovieModel> mData) {
        this.mcontext =  mcontext;
        this.mData = mData;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(mcontext).inflate(R.layout.movie_item,parent,false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).name);
        holder.id.setText(mData.get(position).getId());
        Glide.with(mcontext)
                .load("https://image.tmdb.org/t/p/w500"+mData.get(position)
                .getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView name;
        TextView id;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_txt);
            id=itemView.findViewById(R.id.id_txt);
            img=itemView.findViewById(R.id.imageView);
        }
    }
}
