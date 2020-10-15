package com.example.postapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.example.postapp.dataModel.Post;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUp extends AppCompatActivity implements IPickResult {
    Bitmap bitmap;
    MaterialButton button;
    TextInputEditText text;
    CircleImageView imageView;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        button = findViewById(R.id.setup_btn);
        text = findViewById(R.id.setup_ED_name);
        imageView = findViewById(R.id.setup_image);
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
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

    public void setUpFinish(View view) {
        if (bitmap == null) {
            Toast.makeText(this, "please take image first", Toast.LENGTH_SHORT).show();
            return;
        }
        setUserProperty();
    }

    private void createAccount(String response) {
        BackendlessUser user = new BackendlessUser();
        user.setProperty("email", email);
        user.setPassword(password);
        user.setProperty("name", text.getText().toString());
        user.setProperty("profilePic", response);
        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            public void handleResponse(BackendlessUser registeredUser) {
                sendToMain();
            }

            public void handleFault(BackendlessFault fault) {

                Toast.makeText(SetUp.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUserProperty() {


        String name = text.getText().toString();
        //upload image
        Backendless.Files.Android.upload(bitmap, Bitmap.CompressFormat.PNG, 30
                , name + ".png"
                , "userProfilePic", new AsyncCallback<BackendlessFile>() {
                    @Override
                    public void handleResponse(BackendlessFile response) {
                        String pic = response.getFileURL();
                        createAccount(pic);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {

                        Toast.makeText(SetUp.this, "image upload problem", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendToMain() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}