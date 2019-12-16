package com.example.coffeeapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.coffeeapp.Model.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ViewOrdersAdapter extends RecyclerView.Adapter<MyOrdersViewHolder> {
    private List<Order> listData;
    private Context context;

    public ViewOrdersAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public MyOrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.viewmyorders, parent, false);
        return new MyOrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyOrdersViewHolder holder, int position) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.CartItemCount.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        double price = (Double.parseDouble(listData.get(position).getCoffeePrice())) * (Integer.parseInt(listData.get(position).getQuantity()));
        holder.CartOrderID.setText("Order #: " + String.valueOf(listData.get(position).getOrderID()));
        holder.CartItemPrice.setText(nf.format(price));
        holder.CartItemName.setText(listData.get(position).getCoffeeName());
        holder.CartItemMilk.setText(listData.get(position).getMilkType());
        holder.CartItemSize.setText(listData.get(position).getSize());
     }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
