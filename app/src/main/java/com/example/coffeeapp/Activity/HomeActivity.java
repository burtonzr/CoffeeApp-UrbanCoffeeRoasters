package com.example.coffeeapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.coffeeapp.Fragments.DrinkListFragment;
import com.example.coffeeapp.R;

public class HomeActivity extends AppCompatActivity {

    private static final String EXTRA_USERNAME = "com.example.coffeeapp.username";
    public static String usernameText;

    public static Intent newIntent(Context packageContext, String username) {
        Intent intent = new Intent(packageContext, HomeActivity.class);
        usernameText = username;
        intent.putExtra(EXTRA_USERNAME, usernameText);
        return intent;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button placeOrder   = findViewById(R.id.placeOrder);
        final Button viewMyOrders = findViewById(R.id.viewMyOrders);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            private static final long SPLASH_DISPLAY_TIME = 500;
            @Override
            public void onClick(final View view) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = DrinkListFragment.newIntentUsername(HomeActivity.this, usernameText);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,  R.anim.slide_out_left);
                        ((HomeActivity) view.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }, SPLASH_DISPLAY_TIME);
            }
        });
        viewMyOrders.setOnClickListener(new View.OnClickListener() {
            private static final long SPLASH_DISPLAY_TIME = 500;
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = ViewMyOrders.newIntentUsername(HomeActivity.this, usernameText);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }, SPLASH_DISPLAY_TIME);
            }
        });
    }
}
