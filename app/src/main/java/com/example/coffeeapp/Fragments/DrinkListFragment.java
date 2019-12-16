package com.example.coffeeapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coffeeapp.Activity.CartActivity;
import com.example.coffeeapp.Activity.DrinkDetailActivity;
import com.example.coffeeapp.Data;
import com.example.coffeeapp.Database.DatabaseHelperSignUp;
import com.example.coffeeapp.Activity.DrinkListActivity;
import com.example.coffeeapp.Model.Coffee;
import com.example.coffeeapp.Model.Order;
import com.example.coffeeapp.R;

import java.util.ArrayList;
import java.util.List;

public class DrinkListFragment extends Fragment {
    public static Integer setCoffeeID;
    public static String usernameText;
    private static ArrayList<Coffee> data;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    List<Order> carts = new ArrayList<>();

    private static final String EXTRA_USERNAME = "com.example.coffeeapp.Fragments.username";
    private static final String EXTRA_COFFEE_ID = "com.example.coffeeapp.coffee_id";

    public static Intent newIntentUsername(Context packageContext, String username) {
        Intent intent = new Intent(packageContext, DrinkListActivity.class);
        usernameText = username;
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_layout, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_id);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        carts = new DatabaseHelperSignUp(getActivity()).getCarts();

        FloatingActionButton goToCart = view.findViewById(R.id.btnGoToCart);
        goToCart.setOnClickListener(new View.OnClickListener() {
            private static final long SPLASH_DISPLAY_TIME = 500;
            @Override
            public void onClick(final View view) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent cartIntent = CartActivity.newIntentUsername(view.getContext(), usernameText);
                        startActivity(cartIntent);
                        ((DrinkListActivity) view.getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }, SPLASH_DISPLAY_TIME);
            }
        });

        data = new ArrayList<>();

        for(int i = 0; i < Data.drinkNames.length; i++) {
            data.add(new Coffee(
                    Data.drinkNames[i],
                    Data.IDs[i],
                    Data.drawables[i],
                    Data.prices[i],
                    Data.descriptions[i]
            ));
        }

        if(mAdapter == null) {
            mAdapter = new Adapter(data);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        return view;
    }

    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Coffee mDrink;
        private ImageView mImageView;
        private TextView mDrinkNameView;

        public void bind(Coffee drink) {
            mDrink = drink;
            mImageView.setImageResource(mDrink.getImage());
            mDrinkNameView.setText(mDrink.getDrinkName());
        }

        public Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.drink_list, parent, false));
            itemView.setOnClickListener(this);

            mImageView = itemView.findViewById(R.id.image_view);
            mDrinkNameView = itemView.findViewById(R.id.text_view);
        }

        @Override
        public void onClick(View view) {
            final long SPLASH_DISPLAY_TIME = 500;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = DrinkDetailFragment.newIntent(getActivity(), mDrink.getID(), usernameText);
                    Bundle args = new Bundle();
                    setCoffeeID = mDrink.getID();
                    args.putSerializable(EXTRA_COFFEE_ID, setCoffeeID);
                    args.putSerializable(EXTRA_USERNAME, usernameText);
                    startActivity(intent, args);
                    ((DrinkListActivity) getContext()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }, SPLASH_DISPLAY_TIME);
        }
    }

    private class Adapter extends RecyclerView.Adapter<Holder> {

        public List<Coffee> data;

        public Adapter(List<Coffee> drinks) {
            data = drinks;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new Holder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            Coffee drink = data.get(position);
            holder.bind(drink);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private static final String EXTRA_DRINK_ID = "com.example.coffeeapp.drink_id";

    public static Intent newIntent(Context packageContext, int drinkID) {
        Intent intent = new Intent(packageContext, DrinkListActivity.class);
        intent.putExtra(EXTRA_DRINK_ID, drinkID);
        return intent;
    }
}
