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

public class FinalizeActivity extends ListActivity implements View.OnClickListener {

    Button doneBtn;
    private ListView listView;

    Cursor itemCursor;

    String orderName;
    int orderQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize);

        doneBtn = (Button) findViewById(R.id.done_btn);
        searchFilter = findViewById(R.id.searchFilter);

        cursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);
        setCursor(cursor);
        itemCursor = dbManager.fetch(DatabaseHelper.TABLE_ITEM);

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);

        search(searchFilter);
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

                startActivity(modify_intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_btn:
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                orderName = cursor.getString(1);
                orderQty = cursor.getInt(2);
                int oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, orderName));
                dbManager.updateQty(cursor.getString(1), oldQty, orderQty, "min");
            }
            Intent main = new Intent(getApplicationContext(),
                    MenuActivity.class);
            startActivity(main);
            break;
        }
    }
}
