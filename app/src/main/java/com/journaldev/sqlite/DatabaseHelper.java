package com.journaldev.sqlite;

/**
 * Created by gian1 on 05/01/17.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_ITEM = "ITEMS";
    public static final String TABLE_ORDER = "CART";
    public static final String TABLE_FINAL = "FINAL";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String ROOM = "room";
    public static final String UNIT = "unit";

    // Database Information
    static final String DB_NAME = "ITEMS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_ITEM + "( " + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL UNIQUE, " +
            QUANTITY + " INTEGER NOT NULL, " +
            UNIT + " TEXT NOT NULL, " +
            ROOM + " TEXT NOT NULL " + ");";
    private static final String ORDER_TABLE = "CREATE TABLE " + TABLE_ORDER + "( " + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL UNIQUE, " +
            QUANTITY + " INTEGER NOT NULL, " +
            UNIT + " TEXT NOT NULL, " +
            ROOM + " TEXT NOT NULL " + ");";
    private static final String FINAL_TABLE = "CREATE TABLE " + TABLE_FINAL + "( " + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL UNIQUE, " +
            QUANTITY + " INTEGER NOT NULL, " +
            UNIT + " TEXT NOT NULL, " +
            ROOM + " TEXT NOT NULL " + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(ORDER_TABLE);
        db.execSQL(FINAL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINAL);
        onCreate(db);
    }


}
