package com.phm.myhello.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.phm.myhello.database.AmountContract.*;

public class AmountDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "amount_list.db";
    public static final int DATABASE_VERSION = 1;

    private static final String CREATE_AMOUNT_TABLE = "CREATE TABLE " +
            AmountEntry.TABLE_AMOUNT_NAME + " (" +
            AmountEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AmountEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
            AmountEntry.COLUMN_MONTH + " INTEGER NOT NULL, " +
            AmountEntry.COLUMN_YEAR + " INTEGER NOT NULL," +
            AmountEntry.COLUMN_TYPE + " TEXT NOT NULL," +
            AmountEntry.COLUMN_TITLE + " TEXT NOT NULL," +
            AmountEntry.COLUMN_AMOUNT + " INTEGER NOT NULL" + ");";

    private static final String CREATE_KEYWORD_TABLE = "CREATE TABLE " +
            AmountEntry.TABLE_KEYWORD_NAME + " (" +
            AmountEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            AmountEntry.COLUMN_KEYWORD + " TEXT NOT NULL" + ");";

    public AmountDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AMOUNT_TABLE);
        db.execSQL(CREATE_KEYWORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AmountEntry.TABLE_AMOUNT_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AmountEntry.TABLE_KEYWORD_NAME);
        onCreate(db);
    }
}
