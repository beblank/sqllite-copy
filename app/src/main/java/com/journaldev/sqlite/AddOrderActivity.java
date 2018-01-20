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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddOrderActivity extends DatabaseActivity implements View.OnClickListener {


    private TextView nameText;
    private EditText qtyText;
    private TextView qtyMax;
    private Button addBtn;

    private long _id;
    private String name;
    private String qty;
    private String unit;
    private String room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_add_order);

        nameText = findViewById(R.id.name_edittext);
        qtyText = findViewById(R.id.qty_edittext);
        qtyMax = findViewById(R.id.max_qty);

        addBtn = findViewById(R.id.add_order_btn);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        qty = intent.getStringExtra("qty");
        unit = intent.getStringExtra("unit");
        room = intent.getStringExtra("room");
        _id = Long.parseLong(id);

        nameText.setText(name);
        qtyMax.setText("Max quantity = " +qty);

        addBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_order_btn:
                if (nameText.getText().toString().equals("") || qtyText.getText().toString().equals("")) {
                    Toast.makeText(AddOrderActivity.this, "You did not enter a valid input.", Toast.LENGTH_SHORT).show();
                }else if (Integer.parseInt(qtyText.getText().toString()) > Integer.parseInt(qty)){
                    Toast.makeText(this, "Amount not available", Toast.LENGTH_SHORT).show();
                    break;
                }else{
                    dbManager.insert(DatabaseHelper.TABLE_ORDER, nameText.getText().toString(), qtyText.getText().toString(), unit, room);
                    this.returnHome();
                    break;
                }
        }
    }

    public void returnHome() {
        Intent home = new Intent(getApplicationContext(), OrderListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }
}
