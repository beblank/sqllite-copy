package com.journaldev.sqlite;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class TakeOrderActivity extends AppCompatActivity {

    private DBManager dbManager;

    Spinner nameOrderSpinner;
    Spinner qtyOrderSpinner;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper.NAME };

    final int[] to = new int[] { R.id.order_list_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);

        nameOrderSpinner = (Spinner)findViewById(R.id.take_name_order_spinner);
        qtyOrderSpinner = (Spinner)findViewById(R.id.take_qty_order_spinner);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetchName(DatabaseHelper.TABLE_ITEM);

        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_order, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        nameOrderSpinner.setAdapter(adapter);

        spinnerClick();
    }

    private void spinnerClick() {
        nameOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Cursor cursor=(Cursor)parentView.getSelectedItem();
                String itemName=cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                setQtySpinnerList(itemName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private void setQtySpinnerList(String itemName) {

        ArrayList<Integer> qtyList = new ArrayList<Integer>();

        dbManager = new DBManager(this);
        dbManager.open();
        int qty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, itemName));
        if (qty != 0){
            for (int i = 1; i <= qty; i++){
                qtyList.add(i);
            }
        }
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_view_order, qtyList);
        adapter.setDropDownViewResource(R.layout.list_view_order);
        adapter.notifyDataSetChanged();
        qtyOrderSpinner.setAdapter(adapter);
        qtyOrderSpinner.invalidate();
    }
}
