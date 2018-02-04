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

    /* Creating table query
     * The ID column attribute is set as primary key so that it can be identified.
     * And Name set to unique so that it will not have duplicates name
    */
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


    /**
     * create database
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * create tables on sqlite created
     * which override onCreate methode from SQLiteOpenHelper class
     * @param db - SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(ORDER_TABLE);
        db.execSQL(FINAL_TABLE);
    }

    /**
     * when upgraded, if table is exist delete them
     * which override onUpgrade methode from SQLiteOpenHelper class
     * @param db - SQLiteDatabase
     * @param oldVersion - old db version
     * @param newVersion - new db version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FINAL);
        onCreate(db);
    }


}
