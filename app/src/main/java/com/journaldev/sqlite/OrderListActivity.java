package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class OrderListActivity extends AppCompatActivity implements View.OnClickListener{

    private DBManager dbManager;
    EditText searchFilter;
    Button addOrder;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        searchFilter = (EditText) findViewById(R.id.searchFilterOrder);
        addOrder = (Button) findViewById(R.id.order_add_btn);
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);

        addOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_add_btn:
                Intent addOrderActivity = new Intent(getApplicationContext(),
                        AddOrderActivity.class);
                startActivity(addOrderActivity);
                break;
        }
    }
}