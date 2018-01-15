package com.journaldev.sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class AddOrderActivity extends Activity {

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetchName(DatabaseHelper.TABLE_ORDER);

        Log.d("dodol", "onCreate: " + cursor.getColumnIndex("name"));
    }
}
