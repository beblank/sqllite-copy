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

public class FinalizeActivity extends Activity implements View.OnClickListener {

    private DBManager dbManager;
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

        dbManager = new DBManager(this);
        dbManager.open();
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
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.name);
                TextView descTextView = (TextView) view.findViewById(R.id.qty);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        EditItemActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("table", DatabaseHelper.TABLE_ORDER);
                modify_intent.putExtra("caller", "final");

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_btn:
            for(orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
                orderName = orderCursor.getString(1);
                orderQty = orderCursor.getInt(2);
                int oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, orderName));
                dbManager.updateQty(orderCursor.getString(1), oldQty, orderQty);
            }
            dbManager.deleteTable(DatabaseHelper.TABLE_ORDER);
            Intent main = new Intent(getApplicationContext(),
                    MenuActivity.class);
            startActivity(main);
            break;
        }
    }
}
