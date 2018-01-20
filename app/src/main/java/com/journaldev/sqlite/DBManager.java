package com.journaldev.sqlite;

/**
 * Created by gian1 on 05/01/17.
 */
import android.app.Activity;
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
    private Activity activity;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void close() {
        dbHelper.close();
    }

    public void predeterminedList(){
        Cursor cursor = fetch(DatabaseHelper.TABLE_ITEM);
        Log.d(TAG, "predeterminedList: " + cursor.getCount());
        if (cursor.getCount()==0){
            CSVReader csvReader = new CSVReader();
            csvReader.getCSVFiles(activity, this);
        }
    }

    public void insert(String tableName, String name, String quantity, String unit, String room) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.QUANTITY, quantity);
        contentValue.put(DatabaseHelper.UNIT, unit);
        contentValue.put(DatabaseHelper.ROOM, room);
        database.insertWithOnConflict(tableName, null, contentValue, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public Cursor fetch(String tableName) {
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY, DatabaseHelper.UNIT, DatabaseHelper.ROOM};
        Cursor cursor = database.query(tableName,
                columns, null, null, null, null, DatabaseHelper.NAME);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchSort(String tableName, String orderBy) {
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY, DatabaseHelper.UNIT, DatabaseHelper.ROOM };
        Cursor cursor = database.query(tableName,
                columns, null, null, null, null, orderBy);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchName(String tableName) {
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY, DatabaseHelper.UNIT, DatabaseHelper.ROOM};
        Cursor cursor = database.query(tableName,
                columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.getString(1);
        }
        return cursor;
    }

    // get quantity of item name based on table name input
    public String fetchQty(String tableName, String itemName) {
        String result = "0";
        String query = "SELECT " +  DatabaseHelper.QUANTITY  + " FROM " + tableName + " WHERE "+ DatabaseHelper.NAME +" = '" + itemName + "'";
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
                DatabaseHelper.NAME, DatabaseHelper.QUANTITY, DatabaseHelper.UNIT, DatabaseHelper.ROOM};
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

    public int updateQty(String name, int curQty, int newQty, String operator){
        ContentValues contentValues = new ContentValues();
        int qty = 0;
        if (operator == "plus"){
            qty = curQty + newQty;
        } else if (operator == "min"){
            qty = curQty - newQty;
        }
        contentValues.put(DatabaseHelper.QUANTITY, qty);
        int i = database.update(DatabaseHelper.TABLE_ITEM, contentValues, DatabaseHelper.NAME + " = '" + name + "'", null);
        return i;
    }

    //  update table based on id and replace with name and qty input
    public int update(String tableName, long _id, String name, String qty, String room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.QUANTITY, qty);
        contentValues.put(DatabaseHelper.ROOM, room);
        int i = database.update(tableName, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public int updateName(String tableName, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.QUANTITY, desc);
        int i = database.update(tableName, contentValues, DatabaseHelper.NAME + " = '" + name + "'", null);
        return i;
    }

    public void delete(String tableName, long _id) {
        database.delete(tableName, DatabaseHelper._ID + "=" + _id, null);
    }

    public void deleteByName(String tableName, String name) {
        database.delete(tableName, DatabaseHelper.NAME + "= '" + name + "'", null);
    }

    public void deleteTable(String tableName){
        database.execSQL("DELETE FROM " + tableName);
    }

}
