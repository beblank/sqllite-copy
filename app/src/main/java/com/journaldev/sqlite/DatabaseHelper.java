package com.journaldev.sqlite;

/**
 * Created by gian1 on 05/01/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Table Name
    public static final String TABLE_NAME = "ITEMS";

    // Table columns
    public static final String _ID = "_id";
    public static final String NAME = "subject";
    public static final String QUANTITY = "description";

    // Database Information
    static final String DB_NAME = "ITEMS.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT NOT NULL, " + QUANTITY + " INTEGER);";

    // predetermined list
    private static final String[] LIST_NAME = {
        "Bio Chamber",
        "Carts/ Accessory",
        "CO2 Gas",
        "Colorimeter",
        "Conductivity Probe",
        "Current Probe",
        "Cuvette Rack",
        "Differential Voltage Probe",
        "Dissolved Oxygen",
        "Gas Pressure Sensor",
        "LabQuest Mini",
        "Light Sensor",
        "Magnetic Field",
        "O2 Gas",
        "pH sensor",
        "Photogate",
        "Plastic Cuvettes (visible range)",
         "SpectroVis Optical Fiber",
        "SpectroVis Plus Spectrophotometer",
         "Spirometer",
         "Temperature",
         "UVA sensor",
         "UVB sensor"};

    private static final int[] LIST_QTY = {20, 2, 14, 13, 13, 9, 0, 9, 1, 12, 22, 10, 2, 6, 13, 16, 0, 1, 1, 3, 16, 1, 1};


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

        for(int i=0;i<LIST_NAME.length;i++) {
            insertData(db, i, LIST_NAME[i], LIST_QTY[i]);
        }
    }

    private void insertData(SQLiteDatabase db, int i, String name, int qty) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(NAME, name);
        contentValue.put(QUANTITY, qty);
        db.insert(TABLE_NAME, null, contentValue);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
