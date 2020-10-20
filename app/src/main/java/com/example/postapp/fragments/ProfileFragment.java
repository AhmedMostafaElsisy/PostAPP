package com.example.postapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.bumptech.glide.Glide;
import com.example.postapp.Login;
import com.example.postapp.R;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;


import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    View view;
    CircleImageView imageView;
    TextView name, email, phone, address;
    BackendlessUser user;
    SpeedDialView speedDialView;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        user = Backendless.UserService.CurrentUser();
        imageView = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.ownerName);
        email = view.findViewById(R.id.emailProf);
        phone = view.findViewById(R.id.phoneNo);
        address = view.findViewById(R.id.addProf);
        setUserData();
        speedDialView = view.findViewById(R.id.speedDial);
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_exit, R.drawable.ic_baseline_exit_to_app_24)
                .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, getActivity().getTheme()))
                .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.red_active, getActivity().getTheme()))
                .setLabel("exit")
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.blue, getActivity().getTheme()))
                .setLabelClickable(false)
                .create());
        speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id.fab_edit, R.drawable.ic_baseline_edit_24)
                .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, getActivity().getTheme()))
                .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.black, getActivity().getTheme()))
                .setLabel("edit")
                .setLabelColor(Color.WHITE)
                .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.blue, getActivity().getTheme()))
                .setLabelClickable(false)
                .create());
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.fab_exit:
                        logOut();
                        break;
                    case R.id.fab_edit:
                        Toast.makeText(getActivity(), "edit", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        return view;
    }

    private void logOut() {
        Backendless.UserService.logout(new AsyncCallback<Void>() {
            public void handleResponse(Void response) {

                editor.putString("Email", null);
                editor.putString("password", null);
                editor.apply();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }

            public void handleFault(BackendlessFault fault) {
                Toast.makeText(getActivity(), "Error in logout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserData() {
        Glide.with(getActivity()).load((String) user.getProperty("profilePic")).into(imageView);
        name.setText((String) user.getProperty("name"));
        email.setText(user.getEmail());
        if (user.getProperty("phone") == null) {
            phone.setText("no number (option)");
        } else phone.setText((String) user.getProperty("phone"));

        if (user.getProperty("location") == null) {
            address.setText("no location (option)");
        } else address.setText((String) user.getProperty("location"));

    }
}
