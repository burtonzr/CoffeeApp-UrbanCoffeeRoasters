package com.example.coffeeapp.Activity;

import android.support.v4.app.Fragment;

import com.example.coffeeapp.Fragments.DrinkDetailFragment;
import com.example.coffeeapp.R;

public class DrinkDetailActivity extends SingleFragmentActivity {

    private static final String EXTRA_COFFEE_ID = "com.example.coffeeapp.coffee_id";

    @Override
    public Fragment createFragment() {
        Integer CoffeeID = getIntent().getIntExtra(EXTRA_COFFEE_ID, 0);
        return DrinkDetailFragment.newInstance(CoffeeID);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
