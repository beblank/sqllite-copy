package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
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
    Button finalOrder;
    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.NAME, DatabaseHelper.QUANTITY};

    final int[] to = new int[] { R.id.id, R.id.name, R.id.qty };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        searchFilter = (EditText) findViewById(R.id.searchFilterOrder);
        addOrder = (Button) findViewById(R.id.order_add_btn);
        finalOrder = (Button) findViewById(R.id.order_final_btn);
        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);

        listView = (ListView) findViewById(R.id.order_list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_items, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        addOrder.setOnClickListener(this);
        finalOrder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_add_btn:
                Intent addOrderActivity = new Intent(getApplicationContext(),
                        AddOrderActivity.class);
                startActivity(addOrderActivity);
                break;
            case R.id.order_final_btn:
                Intent finalizeActivity = new Intent(getApplicationContext(),
                        FinalizeActivity.class);
                startActivity(finalizeActivity);
                break;
        }
    }
}
