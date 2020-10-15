package com.example.postapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserTokenStorageFactory;
import com.backendless.rt.data.EventHandler;
import com.example.postapp.adptor.Adaptor;
import com.example.postapp.dataModel.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Adaptor adaptor;
    List<Post> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Backendless.initApp(this, "B32BE1D7-649E-293E-FFD9-95FCDBD09300", "03C5A0C3-41BE-4556-8777-1A69B58A1AAD");
        SharedPreferences sh = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String email = sh.getString("Email", null);
        String password = sh.getString("password", null);

        if (email != null && password != null) {
            Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                public void handleResponse(BackendlessUser user) {

                 getData();
                }

                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            sendToLogin();
        }

        floatingActionButton = findViewById(R.id.fab);
    }
private void setUpRecycleView(){
    recyclerView = findViewById(R.id.recycleView);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    adaptor = new Adaptor(MainActivity.this, arrayList);
    recyclerView.setAdapter(adaptor);
}
private void getData(){
        setUpRecycleView();
    DataQueryBuilder builder = DataQueryBuilder.create();
    Backendless.Data.of(Post.class).find(builder, new AsyncCallback<List<Post>>() {
        @Override
        public void handleResponse(List<Post> response) {
            arrayList = response;
            adaptor = new Adaptor(MainActivity.this, arrayList);
            recyclerView.setAdapter(adaptor);
        }

        @Override
        public void handleFault(BackendlessFault fault) {

            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
        }
    });
    EventHandler<Post> rt = Backendless.Data.of(Post.class).rt();
    rt.addCreateListener(new AsyncCallback<Post>() {
        @Override
        public void handleResponse(Post response) {
            arrayList.add(response);
            adaptor.notifyDataSetChanged();
        }

        @Override
        public void handleFault(BackendlessFault fault) {

        }
    });
}
    private void sendToLogin() {
        Toast.makeText(this, "no active user", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Login.class));
        finish();
    }

    public void createPost(View view) {
        Intent intent = new Intent(this, CreatePost.class);
        startActivity(intent);
    }
}