package com.example.coffeeapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.coffeeapp.Model.Order;
import com.example.coffeeapp.Model.OrderSubmit;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperSignUp extends SQLiteOpenHelper {

    public DatabaseHelperSignUp(Context context) {
        super(context, "CoffeeApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sq) {
        sq.execSQL("CREATE TABLE Users(ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, username TEXT, password TEXT)");
        sq.execSQL("CREATE TABLE Orders (ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, CoffeeID TEXT, CoffeeName TEXT, CoffeePrice TEXT, MilkType TEXT, Size TEXT, Status TEXT, Quantity TEXT, UserID TEXT, OrderID TEXT)");
        sq.execSQL("CREATE TABLE OrderSubmit (ID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE, PickUpTime TEXT, TotalAmount TEXT, CardNumber TEXT, Status TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sq, int oldVersion, int newVersion) {
        //sq.execSQL();
    }

    public void setOrderID() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE Orders SET OrderID = (SELECT ID FROM OrderSubmit ORDER BY ID DESC LIMIT 1) WHERE Status = 0";
        db.execSQL(query);
        db.close();
    }

    // Delete Order from Database
    public void deleteCart(String coffeeID) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM Orders WHERE CoffeeID='%s'", coffeeID);
        db.execSQL(query);
    }

    // Update Cart Status
    public void setStatus(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = String.format("UPDATE Orders SET Status = 1 WHERE UserID='%s'", username);
        db.execSQL(query);
        db.close();
    }

    public void addToCart(Order order) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO Orders (CoffeeID, CoffeeName, CoffeePrice, MilkType, Size, Status, Quantity, UserID) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                order.getCoffeeID(),
                order.getCoffeeName(),
                order.getCoffeePrice(),
                order.getMilkType(),
                order.getSize(),
                order.getStatus(),
                order.getQuantity(),
                order.getUserID());
        db.execSQL(query);
    }

    public List<Order> getCartsByUserStatus(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Orders WHERE Status = 1 AND UserID=? ORDER BY ID DESC", new String[] {username});

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("CoffeeID")),
                        c.getString(c.getColumnIndex("CoffeeName")),
                        c.getString(c.getColumnIndex("CoffeePrice")),
                        c.getString(c.getColumnIndex("MilkType")),
                        c.getString(c.getColumnIndex("Size")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("UserID")),
                        c.getString(c.getColumnIndex("Status")),
                        c.getInt(c.getColumnIndex("OrderID"))
                ));
            } while(c.moveToNext());
        }

        return result;
    }

    public List<Order> getCartsByUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Orders WHERE Status = 0 AND UserID=?", new String[] {username});

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("CoffeeID")),
                        c.getString(c.getColumnIndex("CoffeeName")),
                        c.getString(c.getColumnIndex("CoffeePrice")),
                        c.getString(c.getColumnIndex("MilkType")),
                        c.getString(c.getColumnIndex("Size")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("UserID")),
                        c.getString(c.getColumnIndex("Status")),
                        c.getInt(c.getColumnIndex("OrderID"))
                ));
            } while(c.moveToNext());
        }

        return result;
    }

    public List<Order> getCarts() {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"CoffeeID", "CoffeeName", "CoffeePrice", "MilkType", "Size", "Status",  "Quantity", "UserID", "OrderID"};
        String sqlTable = "Orders";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if(c.moveToFirst()) {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("CoffeeID")),
                        c.getString(c.getColumnIndex("CoffeeName")),
                        c.getString(c.getColumnIndex("CoffeePrice")),
                        c.getString(c.getColumnIndex("MilkType")),
                        c.getString(c.getColumnIndex("Size")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("UserID")),
                        c.getString(c.getColumnIndex("Status")),
                        c.getInt(c.getColumnIndex("OrderID"))
                ));
            } while(c.moveToNext());
        }

        return result;
    }

    public void createOrderSubmit(OrderSubmit orderSubmit) {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderSubmit(PickUpTime, TotalAmount, CardNumber, Status) VALUES ('%s', '%s', '%s', '%s');",
            orderSubmit.getPickUpTime(),
            orderSubmit.getTotalAmount(),
            orderSubmit.getCardNumber(),
            orderSubmit.getStatus());
        db.execSQL(query);
    }

    // Insert User in Database
    public boolean insert(String name, String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long ins = db.insert("users", null, contentValues);
        if(ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    // User table
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email=?", new String[] {email});
        if(cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    // User table
    public boolean checkUserName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE username=?", new String[] {username});
        if(cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }
    }

    // Check username and password when logging in.
    public boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE username=? AND password=?", new String[] {username, password});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}