package com.example.coffeeapp.Activity;

import android.support.v4.app.Fragment;

import com.example.coffeeapp.Fragments.DrinkListFragment;
import com.example.coffeeapp.R;

public class DrinkListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new DrinkListFragment();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
