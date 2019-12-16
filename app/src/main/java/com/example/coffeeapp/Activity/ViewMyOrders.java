package com.example.coffeeapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.coffeeapp.Database.DatabaseHelperSignUp;
import com.example.coffeeapp.Model.Order;
import com.example.coffeeapp.R;
import com.example.coffeeapp.ViewOrdersAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewMyOrders extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ViewOrdersAdapter adapter;
    DatabaseHelperSignUp db;
    List<Order> carts = new ArrayList<>();
    TextView totalPriceText;

    private static final String EXTRA_USERNAME = "com.example.coffeeapp.username";
    public static String usernameText;

    public static Intent newIntentUsername(Context packageContext, String username) {
        Intent intent = new Intent(packageContext, ViewMyOrders.class);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmyordersrecyclerview);

        db = new DatabaseHelperSignUp(this);

        mRecyclerView = findViewById(R.id.recycler_view_id);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        totalPriceText = findViewById(R.id.total);

        loadListCoffees();
    }

    private void loadListCoffees() {
        carts = new DatabaseHelperSignUp(this).getCartsByUserStatus(usernameText);
        adapter = new ViewOrdersAdapter(carts, this);
        mRecyclerView.setAdapter(adapter);

        // Calculate total
        double total = 0;
        for(Order order:carts) {
            try {
                total += (Double.parseDouble(order.getCoffeePrice())) * (Integer.parseInt(order.getQuantity()));
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }
        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        totalPriceText.setText(numberFormat.format(total));
        totalPriceText.setText(String.valueOf(total));
    }
}
