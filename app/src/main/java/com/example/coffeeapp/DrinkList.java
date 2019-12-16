package com.example.coffeeapp;

import android.content.Context;

import com.example.coffeeapp.Model.Coffee;

import java.util.ArrayList;
import java.util.List;

public class DrinkList {
    private static DrinkList sDrinkList;
    private Context mContext;
    private List<Coffee> mListDrinks;

    public static DrinkList get(Context context) {
        if(sDrinkList == null) {
            sDrinkList = new DrinkList(context);
        }

        return sDrinkList;
    }

    private DrinkList(Context context) {
        mContext = context.getApplicationContext();
        mListDrinks = new ArrayList<>();
        for(int i = 0; i < Data.drinkNames.length; i++) {
            mListDrinks.add(new Coffee(
                    Data.drinkNames[i],
                    Data.IDs[i],
                    Data.drawables[i],
                    Data.prices[i],
                    Data.descriptions[i]
            ));
        }
    }

    public List<Coffee> getDrinks() {
        return new ArrayList<>(mListDrinks);
    }
}
