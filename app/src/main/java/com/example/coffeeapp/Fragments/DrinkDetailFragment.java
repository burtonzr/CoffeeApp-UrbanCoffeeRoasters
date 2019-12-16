package com.example.coffeeapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.coffeeapp.Database.DatabaseHelperSignUp;
import com.example.coffeeapp.Activity.DrinkDetailActivity;
import com.example.coffeeapp.Model.Coffee;
import com.example.coffeeapp.Model.Order;
import com.example.coffeeapp.R;

public class DrinkDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    DatabaseHelperSignUp db;
    public static Integer setCoffeeID;
    public static String usernameText;
    private Coffee mDrink;
    TextView coffee_name, coffee_price, coffee_description;
    Spinner milk_type, size;
    ImageView coffee_image;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    FloatingActionButton mBtnCart;
    ElegantNumberButton numberButton;

    String milkTypText;
    String sizeText;

    public static final String ARG_COFFEE_ID = "coffee_id";
    private static final String EXTRA_COFFEE_ID = "com.example.coffeeapp_coffee_id";
    public static final String EXTRA_USERNAME = "com.example.coffeeapp.Fragments.username";

    public static Intent newIntent(Context packageContext, Integer coffeeID, String username) {
        Intent intent = new Intent(packageContext, DrinkDetailActivity.class);
        intent.putExtra(EXTRA_COFFEE_ID, coffeeID);
        intent.putExtra(EXTRA_USERNAME, username);
        usernameText = username;
        return intent;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.milk_type) {
            milkTypText = adapterView.getSelectedItem().toString();
        }

        if(adapterView.getId() == R.id.size) {
            sizeText = adapterView.getSelectedItem().toString();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_drink_detail, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelperSignUp(getActivity());

        setCoffeeID = DrinkListFragment.setCoffeeID;
        numberButton             = getView().findViewById(R.id.number_button);
        mBtnCart                 = getView().findViewById(R.id.btnCart);
        coffee_description       = getView().findViewById(R.id.coffee_description);
        coffee_price             = getView().findViewById(R.id.coffee_price);
        coffee_name              = getView().findViewById(R.id.coffee_name);
        coffee_image             = getView().findViewById(R.id.image_coffee);
        milk_type                = getView().findViewById(R.id.milk_type);
        size                     = getView().findViewById(R.id.size);
        mCollapsingToolbarLayout = getView().findViewById(R.id.collapsing);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        mDrink = new Coffee(String.valueOf(coffee_name), setCoffeeID, 1, Double.parseDouble(coffee_price.getText().toString()), String.valueOf(coffee_description));

        if(setCoffeeID == 1) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Espresso",
                            "2.75",
                            "No Milk",
                            "1 Ounce",
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                    ));
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 2) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sizeText.equals("$0.50 - Medium")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Americano",
                            "3.25",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else if(sizeText.equals("$1.00 - Large")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Americano",
                            "3.75",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Americano",
                            "2.75",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    }

                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 3) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sizeText.equals("$0.50 - Medium")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Latte",
                            "4.50",
                            milkTypText,
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else if(sizeText.equals("$1.00 - Large")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Latte",
                            "5.00",
                            milkTypText,
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Latte",
                            "4.00",
                            milkTypText,
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    }
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 4) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sizeText.equals("$0.50 - Medium")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Cappuccino",
                            "4.25",
                            milkTypText,
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else if(sizeText.equals("$1.00 - Large")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Cappuccino",
                            "4.75",
                            milkTypText,
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Cappuccino",
                            "3.75",
                            milkTypText,
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    }
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 5) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Gibraltar",
                            "3.00",
                            milkTypText,
                            "4.50 Ounces",
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                    ));
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 6) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Flat White",
                            "3.75",
                            milkTypText,
                            "5 1/2 Ounces",
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                    ));
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 7) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Macchiato",
                            "3.00",
                            milkTypText,
                            "2 Ounces",
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                    ));
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 8) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sizeText.equals("$0.50 - Medium")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Pour Over",
                            "4.50",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else if(sizeText.equals("$1.00 - Large")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Pour Over",
                            "5.00",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Pour Over",
                            "4.00",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    }
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(setCoffeeID == 9) {
            mBtnCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sizeText.equals("$0.50 - Medium")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Tea",
                            "4.00",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else if(sizeText.equals("$1.00 - Large")) {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Tea",
                            "4.50",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    } else {
                        new DatabaseHelperSignUp(getActivity().getBaseContext()).addToCart(new Order(
                            String.valueOf(mDrink.getID()),
                            "Tea",
                            "3.50",
                            "No Milk",
                            sizeText,
                            numberButton.getNumber(),
                            usernameText,
                            "0",
                            0
                        ));
                    }
                    Toast.makeText(getActivity(), "Added to Cart", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(savedInstanceState == null) {
            if(setCoffeeID == null) {
                if (setCoffeeID == 1) {
                    coffee_name.setText("Espresso");
                    coffee_price.setText("2.75");
                    coffee_image.setImageResource(R.drawable.espresso_50);
                    coffee_description.setText("Espresso is a coffee that is brewed by forcing a small amount of nearly boiling water under pressure through finely ground coffee beans.");
                } else if (setCoffeeID == 2) {
                    coffee_name.setText("Americano");
                    coffee_price.setText("2.75");
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.americano_50);
                    coffee_description.setText("Caffè Americano is a type of coffee drink prepared by diluting an espresso with hot water, giving it a similar strength to, but different flavor from, traditionally brewed coffee.");
                } else if (setCoffeeID == 3) {
                    coffee_name.setText("Latte");
                    coffee_price.setText("4.00");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.latte_50);
                    coffee_description.setText("A latte is a coffee drink made with espresso and steamed milk.");
                } else if (setCoffeeID == 4) {
                    coffee_name.setText("Cappuccino");
                    coffee_price.setText("3.75");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.cappuccino_50);
                    coffee_description.setText("Cappuccino is an espresso based drink with 1/3 espresso, 1/3 foam milk, and 1/3 steamed milk. ");
                } else if (setCoffeeID == 5) {
                    coffee_name.setText("Gibraltar");
                    coffee_price.setText("3.00");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.gibraltar_1_50);
                    coffee_description.setText("A drink servied in a gibraltar glass made with a double shot of espresso and a bit of thin milk. ");
                } else if (setCoffeeID == 6) {
                    coffee_name.setText("Flat White");
                    coffee_price.setText("3.75");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.flatwhite_50);
                    coffee_description.setText("A flat white is similar to a latte, but is smaller in volume and made with less microfoam. It has a higher proportion of coffee to milk. ");
                } else if (setCoffeeID == 7) {
                    coffee_name.setText("Macchiato");
                    coffee_price.setText("3.00");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.macchicato_50);
                    coffee_description.setText("It is an espresso coffee drink with a small amount of milk, usually foamed. ");
                } else if(setCoffeeID == 8) {
                    coffee_name.setText("Pour Over");
                    coffee_price.setText("4.00");
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.pourover_50);
                    coffee_description.setText("Coffee that is brewed by pouring hot water onto coffee grounds using a filter. ");
                } else if(setCoffeeID == 9) {
                    coffee_name.setText("Tea");
                    coffee_price.setText("3.50");
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.tea_50);
                    coffee_description.setText("Our black tea is made using the shrub Camellia sinensis. ");
                }
            } else {
                if (setCoffeeID == 1) {
                    coffee_name.setText("Espresso");
                    coffee_price.setText("2.75");
                    coffee_image.setImageResource(R.drawable.espresso_50);
                    coffee_description.setText("Espresso is a coffee that is brewed by forcing a small amount of nearly boiling water under pressure through finely ground coffee beans.");
                } else if (setCoffeeID == 2) {
                    coffee_name.setText("Americano");
                    coffee_price.setText("2.75");
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.americano_50);
                    coffee_description.setText("Caffè Americano is a type of coffee drink prepared by diluting an espresso with hot water, giving it a similar strength to, but different flavor from, traditionally brewed coffee.");
                } else if (setCoffeeID == 3) {
                    coffee_name.setText("Latte");
                    coffee_price.setText("4.00");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.latte_50);
                    coffee_description.setText("A latte is a coffee drink made with espresso and steamed milk.");
                } else if (setCoffeeID == 4) {
                    coffee_name.setText("Cappuccino");
                    coffee_price.setText("3.75");
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.cappuccino_50);
                    coffee_description.setText("Cappuccino is an espresso based drink with 1/3 espresso, 1/3 foam milk, and 1/3 steamed milk. ");
                } else if (setCoffeeID == 5) {
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    coffee_name.setText("Gibraltar");
                    coffee_price.setText("3.00");
                    coffee_image.setImageResource(R.drawable.gibraltar_1_50);
                    coffee_description.setText("A drink servied in a gibraltar glass made with a double shot of espresso and a bit of thin milk. ");
                } else if (setCoffeeID == 6) {
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    coffee_name.setText("Flat White");
                    coffee_price.setText("3.75");
                    coffee_image.setImageResource(R.drawable.flatwhite_50);
                    coffee_description.setText("A flat white is similar to a latte, but is smaller in volume and made with less microfoam. It has a higher proportion of coffee to milk. ");
                } else if (setCoffeeID == 7) {
                    ArrayAdapter<CharSequence> milkAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.milks, android.R.layout.simple_spinner_item);
                    milkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    milk_type.setAdapter(milkAdapter);
                    milk_type.setOnItemSelectedListener(this);
                    coffee_name.setText("Macchiato");
                    coffee_price.setText("3.00");
                    coffee_image.setImageResource(R.drawable.macchicato_50);
                    coffee_description.setText("It is an espresso coffee drink with a small amount of milk, usually foamed. ");
                } else if(setCoffeeID == 8) {
                    coffee_name.setText("Pour Over");
                    coffee_price.setText("4.00");
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.pourover_50);
                    coffee_description.setText("Coffee that is brewed by pouring hot water onto coffee grounds using a filter. ");
                } else if(setCoffeeID == 9) {
                    coffee_name.setText("Tea");
                    coffee_price.setText("3.50");
                    ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sizes, android.R.layout.simple_spinner_item);
                    sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    size.setAdapter(sizeAdapter);
                    size.setOnItemSelectedListener(this);
                    coffee_image.setImageResource(R.drawable.tea_50);
                    coffee_description.setText("Our black tea is made using the shrub Camellia sinensis. ");
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentCoffeeID", setCoffeeID);
    }

    public static DrinkDetailFragment newInstance(Integer CoffeeID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_COFFEE_ID, CoffeeID);

        DrinkDetailFragment fragment = new DrinkDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
