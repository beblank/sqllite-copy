package com.journaldev.sqlite;

/**
 * Created by gian1 on 05/01/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {

    private DatabaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;
    private static final String TAG = "ItemsDbAdapter";

    // predetermined list
    private final String[] LIST_NAME = {
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
    private final String[] LIST_QTY = {"20", "2", "14", "13", "13", "9", "0", "9", "1", "12", "22", "10", "2", "6", "13", "16", "0", "1", "1", "3", "16", "1", "1"};

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void predeterminedList(){
        Cursor cursor = fetch(DatabaseHelper.TABLE_ITEM);
        Log.d(TAG, "predeterminedList: " + cursor.getCount());
        if (cursor.getCount()==0){
            for(int i=0;i<LIST_NAME.length;i++) {
                insert(LIST_NAME[i], LIST_QTY[i]);
            }
        }
    }

    public void insert(String name, String quantity) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.QUANTITY, quantity);
        database.insert(DatabaseHelper.TABLE_ITEM, null, contentValue);
    }

    public Cursor fetch(String tableName) {
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY};
        Cursor cursor = database.query(tableName,
                columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchName(String tableName) {
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY};
        Cursor cursor = database.query(tableName,
                columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.getString(1);
        }
        return cursor;
    }

    public String fetchQty(String tableName, String itemName) {

        String result = "0";
        String query = "SELECT " +  DatabaseHelper.QUANTITY  + " FROM " + DatabaseHelper.TABLE_ITEM + " WHERE "+ DatabaseHelper.NAME +" = '" + itemName + "'";
        Cursor  cursor = database.rawQuery(query,null);
        if (cursor != null) {
            cursor.moveToFirst();
            result =  cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUANTITY));
        }
        return result;
    }

    public Cursor fetchItemsByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY};
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = database.query(DatabaseHelper.TABLE_ITEM,
                    columns, null, null, null, null, null);

        }
        else {
            mCursor = database.query(true, DatabaseHelper.TABLE_ITEM, columns,
                    DatabaseHelper.NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.QUANTITY, desc);
        int i = database.update(DatabaseHelper.TABLE_ITEM, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_ITEM, DatabaseHelper._ID + "=" + _id, null);
    }

}
