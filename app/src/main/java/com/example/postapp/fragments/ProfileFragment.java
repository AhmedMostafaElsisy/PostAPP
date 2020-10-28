package com.example.postapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.material.textfield.TextInputEditText;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import com.wang.avi.AVLoadingIndicatorView;


import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private TextInputEditText name, email, phone, address;
    private BackendlessUser user;
    private SpeedDialView speedDialView;
    public static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private AVLoadingIndicatorView loadingIndicatorView;

    @SuppressLint("NonConstantResourceId")
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        init();
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
        speedDialView.setOnActionSelectedListener(actionItem -> {
            switch (actionItem.getId()) {
                case R.id.fab_exit:
                    logOut();
                    break;
                case R.id.fab_edit:
                    speedDialView.replaceActionItem(new SpeedDialActionItem.Builder(R.id.fab_save, R.drawable.ic_baseline_save_24)
                            .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, getActivity().getTheme()))
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.black, getActivity().getTheme()))
                            .setLabel("save")
                            .setLabelColor(Color.WHITE)
                            .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.blue, getActivity().getTheme()))
                            .create(), 1);
                    enableEdit(true);
                    break;
                case R.id.fab_save:
                    speedDialView.replaceActionItem(new SpeedDialActionItem.Builder(R.id.fab_edit, R.drawable.ic_baseline_edit_24)
                            .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.white, getActivity().getTheme()))
                            .setFabImageTintColor(ResourcesCompat.getColor(getResources(), R.color.black, getActivity().getTheme()))
                            .setLabel("edit")
                            .setLabelColor(Color.WHITE)
                            .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.blue, getActivity().getTheme()))
                            .setLabelClickable(false)
                            .create(), 1);
                    enableEdit(false);
                    updateData();
                    break;
            }
            return false;
        });
        return view;
    }

    private void init() {
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        user = Backendless.UserService.CurrentUser();
        imageView = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.ET_full_name);
        email = view.findViewById(R.id.ET_Email);
        phone = view.findViewById(R.id.ET_phone);
        address = view.findViewById(R.id.ET_Address);
        loadingIndicatorView = view.findViewById(R.id.avi_Profile);
    }

    private void enableEdit(boolean mode) {
        if (mode) {
            email.setEnabled(true);
            name.setEnabled(true);
            phone.setEnabled(true);
            address.setEnabled(true);
        } else {
            email.setEnabled(false);
            name.setEnabled(false);
            phone.setEnabled(false);
            address.setEnabled(false);
        }
    }

    private void updateData() {
        loadingIndicatorView.setVisibility(View.VISIBLE);
        loadingIndicatorView.show();
        String emailData = email.getText().toString();
        String nameData = name.getText().toString();
        String phoneData = phone.getText().toString();
        String addressData = address.getText().toString();
        user.setEmail(emailData);
        user.setProperty("phone", phoneData);
        user.setProperty("name", nameData);
        user.setProperty("location", addressData);
        Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
            public void handleResponse(BackendlessUser user) {
                loadingIndicatorView.smoothToHide();
            }

            public void handleFault(BackendlessFault fault) {
                loadingIndicatorView.smoothToHide();

                Toast.makeText(getActivity(),fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                Toast.makeText(getActivity(), fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserData() {
        Glide.with(Objects.requireNonNull(getActivity())).load((String) user.getProperty("profilePic")).into(imageView);
        name.setText((String) user.getProperty("name"));

        email.setText(user.getEmail());
        if (user.getProperty("phone") != null)
            phone.setText((String) user.getProperty("phone"));

        if (user.getProperty("location") != null)
           address.setText((String) user.getProperty("location"));

    }
}
