package com.example.coffeeapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeapp.Database.DatabaseHelperSignUp;
import com.example.coffeeapp.R;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelperSignUp db;
    private Button LoginButton;
    private Button SignUpButton;

    // Called when the Activity is first created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = new DatabaseHelperSignUp(this);

        final EditText username = findViewById(R.id.login_username);
        final EditText password = findViewById(R.id.login_password);
        LoginButton = findViewById(R.id.login_button);
        SignUpButton = findViewById(R.id.signup_button);
        final View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameText = username.getText().toString();
                final String passwordText = password.getText().toString();
                if(usernameText.equals("") || passwordText.equals("")) {
                    Toast.makeText(getApplicationContext(), "Username and password are required to login. ", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean check = db.checkUsernamePassword(usernameText, passwordText);
                    if(check == true) {
                        Toast.makeText(getApplicationContext(), "You Successfully logged in!", Toast.LENGTH_SHORT).show();
                        final long SPLASH_DISPLAY_TIME = 500;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = HomeActivity.newIntent(LoginActivity.this, usernameText);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }, SPLASH_DISPLAY_TIME);
                    } else {
                        Toast.makeText(getApplicationContext(), "You are not a user or your username / password combination is incorrect. Make sure to sign up and create an account. ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        final View.OnClickListener signupListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long SPLASH_DISPLAY_TIME = 500;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }, SPLASH_DISPLAY_TIME);
            }
        };

        LoginButton.setOnClickListener(loginListener);
        SignUpButton.setOnClickListener(signupListener);
    }
}
