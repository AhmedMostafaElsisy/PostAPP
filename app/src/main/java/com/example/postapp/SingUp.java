package com.example.postapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ashehata.mylibrary.ValidateErrorType;
import com.ashehata.mylibrary.ValidateME;
import com.ashehata.mylibrary.ValidateModel;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SingUp extends AppCompatActivity {
    Button button;
    ProgressBar progressBar;
    TextInputEditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        button = findViewById(R.id.confirm);
        progressBar = findViewById(R.id.reg_progress);
        email = findViewById(R.id.ED_email);
        password = findViewById(R.id.ED_password);
    }

    public void createAccount(View view) {
        progressBar.setVisibility(View.VISIBLE);

        validate();
    }

    private void validate() {
        List<ValidateModel> validateModels = new ArrayList<>();

        validateModels.add(ValidateME.validateEmail(email.getText().toString()));
        validateModels.add(ValidateME.validatePassword(password.getText().toString(), 6));
        ValidateME.checkAllValidation(validateModels,
                new ValidateME.OnValidationResult() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(SingUp.this, "Valid ", Toast.LENGTH_SHORT).show();
                        sendToMain();
                    }

                    @Override
                    public void onError(@Nullable ValidateErrorType validateErrorType, int validatePosition) {
                        progressBar.setVisibility(View.INVISIBLE);
                        switch (validateErrorType) {
                            case Email:
                                Toast.makeText(SingUp.this, "inValid email", Toast.LENGTH_SHORT).show();
                                break;
                            case Password:
                                Toast.makeText(SingUp.this, "inValid password", Toast.LENGTH_SHORT).show();
                                break;

                        }
                    }
                }
        );
    }

    private void sendToMain() {
        Intent intent = new Intent(this, SetUp.class);
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", password.getText().toString());
        startActivity(intent);
        finish();
    }
}