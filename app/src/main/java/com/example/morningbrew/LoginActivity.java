package com.example.morningbrew;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity{

    private Button loginBtn;
    private EditText etEmail;
    private EditText etPass;
    private Button registerBtn;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if( ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etEmail = findViewById(R.id.email);
        etPass = findViewById(R.id.password);
        loginBtn = findViewById(R.id.LoginBtn);
        registerBtn = findViewById(R.id.btnRegister);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();
                loginUser(email, password);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick sign up button");
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();
                registerUser(email, password);
            }
        });
    }

    private void registerUser(String email, String password) {
    }

    private void loginUser(String email, String password) {
    }

    private void goMainActivity() {
    }
}
