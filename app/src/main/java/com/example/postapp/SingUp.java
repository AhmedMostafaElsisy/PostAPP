package com.example.postapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ashehata.mylibrary.ValidateErrorType;
import com.ashehata.mylibrary.ValidateME;
import com.ashehata.mylibrary.ValidateModel;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class SingUp extends AppCompatActivity {
    Button button;
    TextInputEditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        button = findViewById(R.id.confirm);

        email = findViewById(R.id.ED_email);
        password = findViewById(R.id.ED_password);
    }

    public void createAccount(View view) {
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

                        Toast.makeText(SingUp.this, "Valid ", Toast.LENGTH_SHORT).show();
                        sendToMain();
                    }

                    @Override
                    public void onError(@Nullable ValidateErrorType validateErrorType, int validatePosition) {
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