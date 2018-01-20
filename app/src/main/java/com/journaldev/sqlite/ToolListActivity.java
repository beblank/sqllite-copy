package com.journaldev.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

public class ToolListActivity extends ListActivity implements View.OnClickListener {


    Button addItemButton;
    Button sortName;
    Button sortQty;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_emp_list);

        addItemButton = findViewById(R.id.item_add_btn);
        sortName = findViewById(R.id.sort_name);
        sortQty = findViewById(R.id.sort_qty);
        searchFilter = findViewById(R.id.searchFilter);

        addItemButton.setOnClickListener(this);
        sortName.setOnClickListener(this);
        sortQty.setOnClickListener(this);

        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);

        search(searchFilter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.name);
                TextView descTextView = (TextView) view.findViewById(R.id.qty);
                TextView roomTextView = (TextView) view.findViewById(R.id.room);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();
                String room = roomTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        EditItemActivity.class);
                modify_intent.putExtra("name", title);
                modify_intent.putExtra("qty", desc);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("room", room);
                modify_intent.putExtra("table", DatabaseHelper.TABLE_ITEM);
                modify_intent.putExtra("caller", "item");

                startActivity(modify_intent);
            }
        });
    }

    private void sortHandler(String orderBy){
        cursor = dbManager.fetchSort(DatabaseHelper.TABLE_ITEM, orderBy);
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_items, cursor, from, to, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_add_btn:
                Intent addItemActivity = new Intent(getApplicationContext(),
                        AddItemActivity.class);
                startActivity(addItemActivity);
                break;
            case R.id.sort_name:
                sortHandler(DatabaseHelper.NAME);
                break;
            case R.id.sort_qty:
                sortHandler(DatabaseHelper.QUANTITY);
                break;
        }
    }
}