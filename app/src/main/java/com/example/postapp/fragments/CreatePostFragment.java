package com.example.postapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.postapp.MainActivity;
import com.example.postapp.R;
import com.example.postapp.dataModel.Post;
import com.google.android.material.button.MaterialButton;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.Date;

public class CreatePostFragment extends Fragment {
    View view;
    Bitmap bitmap;
    ImageView imageView;
    EditText editText;
    MaterialButton button;
    ProgressBar progressBar;
    Post post = new Post();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.create_post_fragment, container, false);
        imageView = view.findViewById(R.id.new_post_image);
        editText = view.findViewById(R.id.new_post_desc);
        button = view.findViewById(R.id.post_btn);
        progressBar = view.findViewById(R.id.new_post_progress);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picImage();
            }
        });
        return view;
    }

    public void picImage() {
        PickImageDialog.build(new PickSetup())
                .setOnPickResult(new IPickResult() {
                    @Override
                    public void onPickResult(PickResult r) {
                        if (r.getError() == null) {
                            bitmap = r.getBitmap();
                            imageView.setImageBitmap(bitmap);

                        } else {
                            Toast.makeText(getActivity(), r.getError().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setOnPickCancel(new IPickCancel() {
                    @Override
                    public void onCancelClick() {
                        //TODO: do what you have to if user clicked cancel
                    }
                }).show(getFragmentManager());
    }


    public void uploadPost() {
        if (bitmap == null) {
            Toast.makeText(getActivity(), "please take image first", Toast.LENGTH_SHORT).show();
            return;
        }
        BackendlessUser user = Backendless.UserService.CurrentUser();
        String name = (String) user.getProperty("name");

        String profilePic = (String) user.getProperty("profilePic");
        post.setDateUpload(new Date().toString());
        //upload image
        Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG, 30
                , name + post.getDateUpload() + ".png"
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
                                Toast.makeText(getActivity(), "postUpload", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                                Toast.makeText(getActivity(), "postUpload problem", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(getActivity(), "image upload problem", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
