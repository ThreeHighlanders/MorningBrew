package com.example.morningbrew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText registerEmail;
    private EditText registerPassword;
    private Button registerBtn;
    public static final String TAG = "RegisterUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.regEmail);
        registerPassword = findViewById(R.id.regPassword);
        registerBtn = findViewById(R.id.btnContinue);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick continue button");
                String email = registerEmail.getText().toString();
                String pass = registerPassword.getText().toString();
                registerUser(email, pass);
            }
        });
    }

    private void registerUser(String email, String password) {
        Log.i(TAG, "Attempting to register user "+ email);
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if ( e == null) {
                    Toast.makeText(RegisterUserActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    goMainActivity();
                }
                else {
                    Toast.makeText(RegisterUserActivity.this, "Issue with registering", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
