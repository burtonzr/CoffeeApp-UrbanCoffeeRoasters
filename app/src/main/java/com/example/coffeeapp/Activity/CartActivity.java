package com.example.coffeeapp.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeapp.CartAdapter;
import com.example.coffeeapp.CartViewHolder;
import com.example.coffeeapp.Database.DatabaseHelperSignUp;
import com.example.coffeeapp.Helper.RecyclerItemTouchHelper;
import com.example.coffeeapp.Interface.RecyclerItemTouchHelperListener;
import com.example.coffeeapp.Model.Order;
import com.example.coffeeapp.Model.OrderSubmit;
import com.example.coffeeapp.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity implements RecyclerItemTouchHelperListener {

    private RecyclerView mRecyclerViewListCart;
    RecyclerView.LayoutManager mLayoutManager;

    RelativeLayout rootLayout;

    DatabaseHelperSignUp db;

    TextView totalPriceText;
    Button btnPlaceOrder;

    List<Order> carts = new ArrayList<>();

    CartAdapter adapter;

    double total;

    public static String usernameText;
    private static final String EXTRA_USERNAME = "com.example.coffeeapp.Fragments.username";

    public static Intent newIntentUsername(Context packageContext, String username) {
        Intent intent = new Intent(packageContext, CartActivity.class);
        usernameText = username;
        intent.putExtra(EXTRA_USERNAME, usernameText);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_cart);

            db = new DatabaseHelperSignUp(this);

            mRecyclerViewListCart = findViewById(R.id.listCart);
            mRecyclerViewListCart.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerViewListCart.setLayoutManager(mLayoutManager);

            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

            // Swipe to delete
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerViewListCart);

            totalPriceText = findViewById(R.id.total);
            btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

            loadListCoffees();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch(OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    private void loadListCoffees() {
        carts = new DatabaseHelperSignUp(this).getCartsByUser(usernameText);
        adapter = new CartAdapter(carts, this);
        mRecyclerViewListCart.setAdapter(adapter);

        // Calculate total
        total = 0;
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

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total > 0.0) {
                    openDialogAlert();
                } else {
                    Toast.makeText(getBaseContext(), "You need a drink in your cart before you can complete an order. ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if(viewHolder instanceof CartViewHolder) {
            String name = ((CartAdapter)mRecyclerViewListCart.getAdapter()).getItem(viewHolder.getAdapterPosition()).getCoffeeName();
            final Order deleteItem = ((CartAdapter)mRecyclerViewListCart.getAdapter()).getItem(viewHolder.getAdapterPosition());

            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            new DatabaseHelperSignUp(getBaseContext()).deleteCart(deleteItem.getCoffeeID());
            // Update total price
            total = 0;
            List<Order> orders = new DatabaseHelperSignUp(getBaseContext()).getCartsByUser(usernameText);
            for(Order item: orders) {
                total += (Double.parseDouble(item.getCoffeePrice())) * (Integer.parseInt(item.getQuantity()));
            }
            Locale locale = new Locale("en", "US");
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

            totalPriceText.setText(numberFormat.format(total));
            totalPriceText.setText(String.valueOf(total));

            // Make Snackbar
            Snackbar snackbar = Snackbar.make(rootLayout, name + " removed from cart.", Snackbar.LENGTH_SHORT);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.restoreItem(deleteItem, deleteIndex);
                    new DatabaseHelperSignUp(getBaseContext()).addToCart(deleteItem);

                    // Add back the price that was taken out from deleting the cart item.
                    double total = 0;
                    List<Order> orders = new DatabaseHelperSignUp(getBaseContext()).getCarts();
                    for(Order item: orders) {
                        total += (Double.parseDouble(item.getCoffeePrice())) * (Integer.parseInt(item.getQuantity()));
                    }
                    Locale locale = new Locale("en", "US");
                    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);

                    totalPriceText.setText(numberFormat.format(total));
                    totalPriceText.setText(String.valueOf(total));
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private void openDialogAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
        alertDialog.setTitle("Complete Order");
        alertDialog.setMessage("Enter the day and time you plan to pick up your order. Place your card number for payment. ");
        LinearLayout linearLayout = new LinearLayout(CartActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editText   = new EditText(CartActivity.this);
        final EditText cardNumber = new EditText(CartActivity.this);
        editText.setHint("Preferred Pick Up");
        cardNumber.setHint("Card Number");
        editText.setWidth(100);
        cardNumber.setWidth(100);
        editText.setInputType(InputType.TYPE_CLASS_DATETIME);
        cardNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        linearLayout.addView(editText);
        linearLayout.addView(cardNumber);

        alertDialog.setView(linearLayout);
        alertDialog.setIcon(R.drawable.shopping_cart);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String editTextString   = editText.getText().toString();
                final String cardNumberString = cardNumber.getText().toString();
                if(editTextString.equals("") || cardNumberString.equals("")) {
                    Toast.makeText(getBaseContext(), "Preferred pick up time and card number are required. ", Toast.LENGTH_LONG).show();
                } else {
                    if(cardNumberString.length() < 16) {
                        Toast.makeText(getBaseContext(), "Card number is not long enough. Needs to be 16 characters. ", Toast.LENGTH_LONG).show();
                    } else {
                        new DatabaseHelperSignUp(getBaseContext()).createOrderSubmit(new OrderSubmit(
                                editTextString,
                                String.valueOf(total),
                                cardNumberString,
                                "1"
                        ));
                        db.setOrderID();
                        db.setStatus(usernameText);
                        Toast.makeText(getBaseContext(), "Order Placed!", Toast.LENGTH_SHORT).show();
                        final long SPLASH_DISPLAY_TIME = 500;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(CartActivity.this, LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                        }, SPLASH_DISPLAY_TIME);
                    }
                }
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }
}
