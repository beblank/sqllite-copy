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

public class AddOrderActivity extends DatabaseActivity implements View.OnClickListener {


    private SimpleCursorAdapter adapter;

    Spinner nameOrderSpinner;
    TextView currentQtyView;
    EditText qtyInput;
    Button orderButton;
    String selectedItemName;
    String currentQty;

    final String[] from = new String[] { DatabaseHelper.NAME };

    final int[] to = new int[] { R.id.order_list_text};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        nameOrderSpinner = (Spinner)findViewById(R.id.add_name_order_spinner);
        currentQtyView = (TextView)findViewById(R.id.add_order_current_qty);
        qtyInput = (EditText) findViewById(R.id.add_qty_order_edit);

        orderButton = (Button) findViewById(R.id.add_order);
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
        dbManager = new DBManager(this);
        dbManager.open();
        currentQty = dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, itemName);
        currentQtyView.setText( "Available :" + currentQty);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_order:
                if (qtyInput.getText().toString().equals("")){
                    Toast.makeText(AddOrderActivity.this, "You did not enter a valid input.", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(qtyInput.getText().toString()) > Integer.parseInt(currentQty)){
                    Toast.makeText(AddOrderActivity.this, "Quantity not Available.", Toast.LENGTH_SHORT).show();
                } else {
                    //dbManager.insert(DatabaseHelper.TABLE_ORDER, selectedItemName, qtyInput.getText().toString());
                    Intent main = new Intent(AddOrderActivity.this, OrderListActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    break;
                }
        }
    }
}
