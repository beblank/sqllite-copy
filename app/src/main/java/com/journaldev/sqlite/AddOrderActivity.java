package com.journaldev.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddOrderActivity extends Activity implements View.OnClickListener {

    private DBManager dbManager;
    private SimpleCursorAdapter adapter;

    Spinner nameOrderSpinner;
    Spinner qtyOrderSpinner;
    Button orderButton;
    String selectedItemName;
    String selectedQtyItem;

    final String[] from = new String[] { DatabaseHelper.NAME };

    final int[] to = new int[] { R.id.order_list_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        nameOrderSpinner = (Spinner)findViewById(R.id.add_name_order_spinner);
        qtyOrderSpinner = (Spinner)findViewById(R.id.take_qty_order_spinner);
        orderButton = (Button) findViewById(R.id.add_order);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetchName(DatabaseHelper.TABLE_ITEM);

        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_order, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        nameOrderSpinner.setAdapter(adapter);

        spinnerClick();

        orderButton.setOnClickListener(this);
    }

    private void spinnerClick() {
        nameOrderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Cursor cursor=(Cursor)parentView.getSelectedItem();
                String itemName=cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                selectedItemName = itemName;
                setQtySpinnerList(itemName);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
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
        qtyOrderSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                ((TextView) view).setTextColor(Color.BLACK);
                selectedQtyItem = parent.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_order:
                dbManager.insert(DatabaseHelper.TABLE_ORDER, selectedItemName, selectedQtyItem);
                Intent main = new Intent(AddOrderActivity.this, OrderListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;
        }
    }
}
