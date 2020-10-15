package com.example.postapp.adptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.postapp.R;
import com.example.postapp.dataModel.Post;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder> {
    private Context mContext;
    private List<Post> arrayList;


    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profileImage;
        TextView desc, name, date;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.post_circleImage);
            desc = itemView.findViewById(R.id.post_desc);
            name = itemView.findViewById(R.id.post_userName);
            date = itemView.findViewById(R.id.post_date);
            imageView = itemView.findViewById(R.id.post_image);
        }
    }

    public Adaptor(Context context, List<Post> arrayList) {
        this.mContext = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        ViewHolder ViewHolder = new ViewHolder(view);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Post post = arrayList.get(position);
        Glide.with(mContext).load(post.getPhotoUrl()).into(holder.imageView);
        Glide.with(mContext).load(post.getProfilePic()).into(holder.profileImage);
        holder.desc.setText(post.getDescription());
        holder.name.setText(post.getNameUser());
        holder.date.setText(post.getDateUpload());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}