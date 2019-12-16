package com.example.coffeeapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyOrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView CartItemName, CartItemPrice, CartItemMilk, CartItemSize, CartOrderID;
    public ImageView CartItemCount;

    public MyOrdersViewHolder(View itemView) {
        super(itemView);
        CartOrderID     = itemView.findViewById(R.id.cart_order_id);
        CartItemName    = itemView.findViewById(R.id.cart_item_name);
        CartItemPrice   = itemView.findViewById(R.id.cart_item_price);
        CartItemCount   = itemView.findViewById(R.id.cart_item_count);
        CartItemMilk    = itemView.findViewById(R.id.cart_item_milk);
        CartItemSize    = itemView.findViewById(R.id.cart_item_size);
    }
}
