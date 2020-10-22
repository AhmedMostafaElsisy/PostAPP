package com.example.postapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dmallcott.dismissibleimageview.DismissibleImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;



public class PostDetails extends AppCompatActivity {
    TextView name, date, desc;
    private CircleImageView profileImage;
    private DismissibleImageView postPic;
    List<Image> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        name = findViewById(R.id.post_de_userName);
        date = findViewById(R.id.post_de_date);
        desc = findViewById(R.id.post_de_desc);
        postPic = findViewById(R.id.post_de_image);
        profileImage = findViewById(R.id.post_de_circleImage);
        Bundle extras = this.getIntent().getExtras();
        String nameData = extras.getString("name");
        String profile = extras.getString("profile");
        String intentDesc = extras.getString("desc");
        String pic = extras.getString("pic");
        String intentDate = extras.getString("date");
        name.setText(nameData);
        Glide.with(this).load(profile).into(profileImage);
        Glide.with(this).load(pic).into(postPic);
        desc.setText(intentDesc);
        date.setText(intentDate);



    }


}