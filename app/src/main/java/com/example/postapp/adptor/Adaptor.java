package com.example.postapp.adptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.postapp.R;
import com.example.postapp.dataModel.Post;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder> {
    private final Context mContext;
    private List<Post> arrayList;
    private final OnPostListener onPostListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView profileImage;
        TextView desc, name, date;
        ImageView imageView;
        OnPostListener onPostListener;

        public ViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.post_circleImage);
            desc = itemView.findViewById(R.id.post_desc);
            name = itemView.findViewById(R.id.post_userName);
            date = itemView.findViewById(R.id.post_date);
            imageView = itemView.findViewById(R.id.post_image);
            this.onPostListener = onPostListener;
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public Adaptor(Context context, List<Post> arrayList, OnPostListener onPostListener) {
        this.mContext = context;
        this.arrayList = arrayList;
        this.onPostListener = onPostListener;
    }
    public void setData(List<Post> data) {
        arrayList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view, onPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Post post = arrayList.get(position);
        Glide.with(mContext).load(post.getPhotoUrl()).into(holder.imageView);
        Glide.with(mContext).load(post.getProfilePic()).into(holder.profileImage);
        holder.desc.setText(post.getDescription());
        holder.name.setText(post.getNameUser());
        holder.date.setText(post.getDateUpload());
        holder.imageView.setOnClickListener(view -> onPostListener.onPostClick(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnPostListener {
        void onPostClick(int position);
    }

}