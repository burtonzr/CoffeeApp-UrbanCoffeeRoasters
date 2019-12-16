package com.example.coffeeapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView CartItemName, CartItemPrice, CartItemMilk, CartItemSize, CartOrderID;
    public ImageView CartItemCount;
    public RelativeLayout view_background;
    public LinearLayout view_foreground;

    public CartViewHolder(View itemView) {
        super(itemView);
        CartOrderID     = itemView.findViewById(R.id.cart_order_id);
        CartItemName    = itemView.findViewById(R.id.cart_item_name);
        CartItemPrice   = itemView.findViewById(R.id.cart_item_price);
        CartItemCount   = itemView.findViewById(R.id.cart_item_count);
        CartItemMilk    = itemView.findViewById(R.id.cart_item_milk);
        CartItemSize    = itemView.findViewById(R.id.cart_item_size);
        view_background = (RelativeLayout) itemView.findViewById(R.id.view_background);
        view_foreground = (LinearLayout) itemView.findViewById(R.id.view_foreground);
    }
}