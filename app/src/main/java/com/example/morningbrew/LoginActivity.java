package com.example.morningbrew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
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

//        if( ParseUser.getCurrentUser() != null) {
//            goMainActivity();
//        }

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_morning_brew);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
                Log.i(TAG, "onClick register button");
                goRegisterUser();
            }
        });
    }

    private void goRegisterUser() {
        Intent j = new Intent(this, RegisterUserActivity.class);
        startActivity(j);
        finish();
    }

    private void loginUser(String email, String password) {
        Log.i(TAG, "Attempting to login user "+ email);
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if( e != null) {
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                Log.i(TAG, "login user");
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
