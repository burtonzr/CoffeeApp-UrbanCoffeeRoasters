package com.example.coffeeapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coffeeapp.Database.DatabaseHelperSignUp;
import com.example.coffeeapp.R;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelperSignUp db;

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        db = new DatabaseHelperSignUp(this);

        final EditText Name            = findViewById(R.id.signup_name);
        final EditText Email           = findViewById(R.id.signup_email);
        final EditText Username        = findViewById(R.id.signup_username);
        final EditText Password        = findViewById(R.id.signup_password);
        final EditText ConfirmPassword = findViewById(R.id.signup_confirm_password);
        final Button SignUpButton = findViewById(R.id.signup_button);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameText     = Name.getText().toString();
                String emailText    = Email.getText().toString();
                String usernameText = Username.getText().toString();
                String passwordText = Password.getText().toString();
                String confirmText  = ConfirmPassword.getText().toString();

                if (nameText.equals("") || emailText.equals("")
                        || usernameText.equals("") || passwordText.equals("")) {
                    Toast.makeText(getApplicationContext(), "No field can be empty. ", Toast.LENGTH_SHORT).show();
                } else {
                    if(passwordText.length() >= 5) {
                        if(passwordText.equals(confirmText)) {
                            Boolean checkEmail    = db.checkEmail(emailText);
                            Boolean checkUsername = db.checkUserName(usernameText);
                            if(checkEmail == true) {
                                if(checkUsername == true) {
                                    Boolean insert = db.insert(nameText, emailText, usernameText, passwordText);
                                    if(insert == true) {
                                        final long SPLASH_DISPLAY_TIME = 500;
                                        Toast.makeText(getApplicationContext(), "Sign Up Success!", Toast.LENGTH_SHORT).show();
                                        new Handler().postDelayed(new Runnable(){
                                            public void run() {
                                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                            }
                                        }, SPLASH_DISPLAY_TIME);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Username already exists. ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Email already exists. ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Passwords do not match. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is not long enough. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
