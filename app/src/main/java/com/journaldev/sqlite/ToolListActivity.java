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
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ToolListActivity extends Activity {

    private DBManager dbManager;
    EditText searchFilter;
    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.NAME, DatabaseHelper.QUANTITY};

    final int[] to = new int[] { R.id.id, R.id.title, R.id.desc };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_emp_list);

        searchFilter = (EditText) findViewById(R.id.searchFilter);
        dbManager = new DBManager(this);
        dbManager.open();
        dbManager.predeterminedList();
        Cursor cursor = dbManager.fetch(DatabaseHelper.TABLE_NAME);

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this,
                R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();


        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView titleTextView = (TextView) view.findViewById(R.id.title);
                TextView descTextView = (TextView) view.findViewById(R.id.desc);

                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(),
                        EditItemActivity.class);
                modify_intent.putExtra("title", title);
                modify_intent.putExtra("desc", desc);
                modify_intent.putExtra("id", id);

                startActivity(modify_intent);
            }
        });

        searchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,
                                          int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,
                                      int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbManager.fetchItemsByName(constraint.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {

            Intent add_mem = new Intent(this, AddItemActivity.class);
            startActivity(add_mem);

        }
        return super.onOptionsItemSelected(item);
    }

}