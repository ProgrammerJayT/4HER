package com.forher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context) {
        super(context, "4HER.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String contacts = "create table contacts " +
                "(contact_id INTEGER PRIMARY KEY AUTOINCREMENT, contact_one text, contact_two text, contact_three text)";

        String user = "create table user " +
                "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, user_fname text, user_lname text, user_phone text, user_email text)";
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {

    }
}
