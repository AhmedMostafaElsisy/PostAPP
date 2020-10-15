package com.example.postapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.postapp.dataModel.Post;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.Date;


public class CreatePost extends AppCompatActivity implements IPickResult {
    Bitmap bitmap;
    ImageView imageView;
    EditText editText;
    MaterialButton button;
    ProgressBar progressBar;
    Post post = new Post();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        imageView = findViewById(R.id.new_post_image);
        editText = findViewById(R.id.new_post_desc);
        button = findViewById(R.id.post_btn);
        progressBar = findViewById(R.id.new_post_progress);
    }

    public void picImage(View view) {
        PickImageDialog.build(new PickSetup()).show(this);
    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            bitmap = r.getBitmap();
            imageView.setImageBitmap(bitmap);

        } else {
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void uploadPost(View view) {
        if (bitmap == null) {
            Toast.makeText(this, "please take image first", Toast.LENGTH_SHORT).show();
            return;
        } BackendlessUser user = Backendless.UserService.CurrentUser();
        String name = (String) user.getProperty("name");

        String profilePic= (String) user.getProperty("profilePic");
      post.setDateUpload(new Date().toString());
        //upload image
        Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG, 30
                , name +post.getDateUpload()+ ".png"
                , "posts", new AsyncCallback<BackendlessFile>() {
                    @Override
                    public void handleResponse(BackendlessFile response) {

                        //upload data
                        post.setNameUser(name);
                        post.setProfilePic(profilePic);
                        post.setDescription(editText.getText().toString());
                        post.setPhotoUrl(response.getFileURL());


                        Backendless.Data.of(Post.class).save(post, new AsyncCallback<Post>() {
                            @Override
                            public void handleResponse(Post response) {
                                Toast.makeText(CreatePost.this, "postUpload", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreatePost.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(CreatePost.this, "postUpload problem", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(CreatePost.this, "image upload problem", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}