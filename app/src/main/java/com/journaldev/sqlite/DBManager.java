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

    /**
     * constructor
     * @param c
     */
    public DBManager(Context c) {
        context = c;
    }

    /**
     * open sql connection from DatabaseHelper when it crash it will throws SQLException
     * @return DBManager
     */
    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * set activity being used for this class
     * @param activity - set Activity
     */
    public void setActivity(Activity activity){
        this.activity = activity;
    }

    /**
     * close the db connection
     * so no memory leak from database connection
     */
    public void close() {
        dbHelper.close();
    }

    // create predetermined list from csv and insert into sqlite
    public void predeterminedList(){
        Cursor cursor = fetch(DatabaseHelper.TABLE_ITEM);
        Log.d(TAG, "predeterminedList: " + cursor.getCount());
        if (cursor.getCount()==0){
            CSVReader csvReader = new CSVReader();
            csvReader.getCSVFiles(activity, this);
        }
    }

    /**
     * insert into sqlite based on input table name
     * @param tableName - table name
     * @param name - item name
     * @param quantity - item qty
     * @param unit - item unit
     * @param room - item storage room
     */
    public void insert(String tableName, String name, String quantity, String unit, String room) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.NAME, name);
        contentValue.put(DatabaseHelper.QUANTITY, quantity);
        contentValue.put(DatabaseHelper.UNIT, unit);
        contentValue.put(DatabaseHelper.ROOM, room);
        database.insertWithOnConflict(tableName, null, contentValue, SQLiteDatabase.CONFLICT_IGNORE);
    }

    /**
     * get data from selected table name as a cursor
     * @param tableName - table name
     * @return Cursor
     */
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

    /**
     * get data from selected table name as a cursor and sort it by name or by qty
     * @param tableName - table name
     * @param orderBy - sort data
     * @return Cursor
     */
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

    /**
     * get item name from selected table name as a cursor
     * @param tableName - table name
     * @return Cursor
     */
    public Cursor fetchName(String tableName) {
        String[] columns = new String[] { DatabaseHelper._ID,
                DatabaseHelper.NAME};
        Cursor cursor = database.query(tableName,
                columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            cursor.getString(1);
        }
        return cursor;
    }

    /**
     * get item qty from selected table name as a cursor
     * @param tableName - table name
     * @return Cursor
     */
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

    /**
     * get data and filter by name from item table as a cursor
     * @param inputText - item name from item table
     * @return Cursor
     */
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

    /**
     * update qty based on input table and check the operator if its addition or subtraction
     * @param tableName - table name
     * @param name - item name
     * @param curQty - current item qty
     * @param newQty - new item qty
     * @param operator - operator + or -
     * @return int
     */
    public int updateQty(String tableName, String name, int curQty, int newQty, String operator){
        ContentValues contentValues = new ContentValues();
        int qty = 0;
        if (operator == "plus"){
            qty = curQty + newQty;
        } else if (operator == "min"){
            qty = curQty - newQty;
        }
        contentValues.put(DatabaseHelper.QUANTITY, qty);
        int i = database.update(tableName, contentValues, DatabaseHelper.NAME + " = '" + name + "'", null);
        return i;
    }

    /**
     * get data from selected _id and
     * update the name and qty and room
     * @param tableName - table name
     * @param _id - item name
     * @param name - current item qty
     * @param qty - new item qty
     * @param room - operator + or -
     * @return int
     */
    public int update(String tableName, long _id, String name, String qty, String room) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.NAME, name);
        contentValues.put(DatabaseHelper.QUANTITY, qty);
        contentValues.put(DatabaseHelper.ROOM, room);
        int i = database.update(tableName, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    /**
     * get data from selected name and
     * update the name and qty
     * @param tableName - table name
     * @param name - item name
     * @param desc - item qty
     */
    public void updateByName(String tableName, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.QUANTITY, desc);
        database.update(tableName, contentValues, DatabaseHelper.NAME + " = '" + name + "'", null);
    }

    /**
     * delete row based on _id
     * @param tableName - table name
     * @param _id - item id
     */
    public void delete(String tableName, long _id) {
        database.delete(tableName, DatabaseHelper._ID + "=" + _id, null);
    }

    /**
     * delete row based on name
     * @param tableName - table name
     * @param name - item name
     */
    public void deleteByName(String tableName, String name) {
        database.delete(tableName, DatabaseHelper.NAME + "= '" + name + "'", null);
    }

    /**
     * delete table based table name
     * @param tableName - table name
     */
    public void deleteTable(String tableName){
        database.execSQL("DELETE FROM " + tableName);
    }

}
