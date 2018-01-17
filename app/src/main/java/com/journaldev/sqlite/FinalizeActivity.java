package com.journaldev.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FinalizeActivity extends Activity implements View.OnClickListener {

    private DBManager dbManager;
    Button doneBtn;
    private ListView listView;

    private SimpleCursorAdapter adapter;

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
        Cursor cursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);

        adapter = new SimpleCursorAdapter(this,
                R.layout.list_view_items, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        doneBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_btn:
                Log.d("finalize", "onClick: ");
            break;
        }
    }
}
