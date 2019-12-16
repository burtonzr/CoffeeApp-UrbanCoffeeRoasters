package com.example.coffeeapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.coffeeapp.Database.DatabaseHelperSignUp;

public class Provider extends ContentProvider {

    private SQLiteDatabase mDatabase;
    public static final String PROVIDER = "com.example.coffeeapp.Provider";
    public static final String URL ="content://" + PROVIDER + "/Orders";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelperSignUp mDatabaseHelper = new DatabaseHelperSignUp(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        return (mDatabaseHelper != null ? true : false);
    }

    @Override
    public Cursor query(Uri uri, String[] projections, String sel, String[] args, String sortOrder) {
        Cursor cursor = mDatabase.query("Orders", projections, sel, args, null, null, sortOrder);
        getContext().getContentResolver().notifyChange(uri, null);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Context context = getContext();
        DatabaseHelperSignUp mDatabaseHelper = new DatabaseHelperSignUp(context);
        mDatabase = mDatabaseHelper.getWritableDatabase();
        long rowID = mDatabase.insert("Orders", "", contentValues);
        if(rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
