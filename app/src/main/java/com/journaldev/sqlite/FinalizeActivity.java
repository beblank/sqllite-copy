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

    // instantiate ui and list view click handler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize);

        doneBtn = (Button) findViewById(R.id.done_btn);
        searchFilter = findViewById(R.id.searchFilter);

        cursor = dbManager.fetch(DatabaseHelper.TABLE_FINAL);
        setCursor(cursor);

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

    // on done click, back to main menu
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
