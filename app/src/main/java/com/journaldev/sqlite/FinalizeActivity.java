package com.journaldev.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalizeActivity extends DatabaseActivity implements View.OnClickListener {

    Button doneBtn;
    private ListView listView;

    private SimpleCursorAdapter adapter;

    Cursor orderCursor;
    Cursor itemCursor;

    String orderName;
    int orderQty;


    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.NAME, DatabaseHelper.QUANTITY};

    final int[] to = new int[] { R.id.id, R.id.name, R.id.qty };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize);

        doneBtn = (Button) findViewById(R.id.done_btn);
        listView = (ListView) findViewById(R.id.order_list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        orderCursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);
        itemCursor = dbManager.fetch(DatabaseHelper.TABLE_ITEM);


        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_items, orderCursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        doneBtn.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long viewId) {
                TextView titleTextView = (TextView) view.findViewById(R.id.name);

                String name = titleTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        ReturnOrderActivity.class);
                modify_intent.putExtra("name", name);
                modify_intent.putExtra("table", DatabaseHelper.TABLE_ORDER);

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_btn:
            Intent main = new Intent(getApplicationContext(),
                    MenuActivity.class);
            startActivity(main);
            break;
        }
    }
}
