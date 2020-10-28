package com.example.postapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.postapp.adptor.ViewPagerAdapter;
import com.example.postapp.fragments.CreatePostFragment;
import com.example.postapp.fragments.HomeFragment;
import com.example.postapp.fragments.ProfileFragment;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp(this, "290E3DAA-329C-BD01-FF85-3F4C5898E500", "1AC31B70-1843-4B00-962F-2483B09E784C");
        SharedPreferences sh = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sh.getString("Email", null);
        String password = sh.getString("password", null);
        if (email != null && password != null) {
            Backendless.UserService.login(email, password, new AsyncCallback<BackendlessUser>() {
                public void handleResponse(BackendlessUser user) {
                    final BubbleNavigationLinearView bubbleNavigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
                    final ViewPager viewPager = findViewById(R.id.view_pager);

                    ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

                    adapter.addFrag(new HomeFragment(), "Home");
                    adapter.addFrag(new CreatePostFragment(), "new Post");
                    adapter.addFrag(new ProfileFragment(), "Profile");
                    viewPager.setAdapter(adapter);
                    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int i, float v, int i1) {
                        }

                        @Override
                        public void onPageSelected(int i) {
                            bubbleNavigationLinearView.setCurrentActiveItem(i);
                        }

                        @Override
                        public void onPageScrollStateChanged(int i) {

                        }
                    });

                    bubbleNavigationLinearView.setNavigationChangeListener((view, position) -> viewPager.setCurrentItem(position, true));
                }

                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MainActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            sendToLogin();
        }

    }
    private void sendToLogin() {
        Toast.makeText(this, "no active user", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, Login.class));
        finish();
    }

}