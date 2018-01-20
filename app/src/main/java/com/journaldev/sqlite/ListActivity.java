package com.journaldev.sqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

public class ListActivity extends DatabaseActivity {

    EditText searchFilter;
    ListView listView;

    SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.NAME, DatabaseHelper.QUANTITY, DatabaseHelper.UNIT, DatabaseHelper.ROOM};

    final int[] to = new int[] { R.id.id, R.id.name, R.id.qty, R.id.unit, R.id.room };

    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cursor = dbManager.fetch(DatabaseHelper.TABLE_ITEM);
        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_items, cursor, from, to, 0);
        adapter.notifyDataSetChanged();


        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbManager.fetchItemsByName(constraint.toString());
            }
        });
    }

    public void search(EditText searchFilter){
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
    }
}
