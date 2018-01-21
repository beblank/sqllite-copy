package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class OrderListActivity extends ListActivity implements View.OnClickListener{

    Button finalOrder;
    Cursor orderCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        finalOrder = findViewById(R.id.order_final_btn);
        searchFilter = findViewById(R.id.searchFilter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        cursor = dbManager.fetch(DatabaseHelper.TABLE_ITEM);
        setCursor(cursor);
        orderCursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);

        search(searchFilter);
        finalOrder.setOnClickListener(this);

        // when lite view is click send current selected item to intent extra param
        // then call AddOrderActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView nameTextView = (TextView) view.findViewById(R.id.name);
                TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
                TextView unitTextView = (TextView) view.findViewById(R.id.unit);
                TextView roomTextView = (TextView) view.findViewById(R.id.room);

                String id = idTextView.getText().toString();
                String name = nameTextView.getText().toString();
                String qty = qtyTextView.getText().toString();
                String unit = unitTextView.getText().toString();
                String room = roomTextView.getText().toString();

                Intent add_order = new Intent(getApplicationContext(),
                        AddOrderActivity.class);
                add_order.putExtra("name", name);
                add_order.putExtra("qty", qty);
                add_order.putExtra("unit", unit);
                add_order.putExtra("id", id);
                add_order.putExtra("room", room);

                startActivity(add_order);
            }
        });

    }

    // on click handler
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_final_btn:
                for(orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
                    String orderName = orderCursor.getString(1);
                    int orderQty = orderCursor.getInt(2);

                    // check final table name if equal to order name based on iteration on order table
                    // if exist, add final qty with order qty
                    // try to check final qty first before read it
                    // if not exist then it will not execute
                    try{
                        int finalOldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_FINAL, orderName));
                        dbManager.updateQty(DatabaseHelper.TABLE_FINAL, orderCursor.getString(1), finalOldQty, orderQty, "plus");
                    } catch (CursorIndexOutOfBoundsException e){
                        //e.printStackTrace();
                    }

                    // clone all order table which acts like a cart to final table
                    dbManager.insert(DatabaseHelper.TABLE_FINAL,
                            orderCursor.getString(1),
                            orderCursor.getString(2),
                            orderCursor.getString(3),
                            orderCursor.getString(4));

                    // check item table name if equal to order name based on iteration on order table
                    // if exist, subtract current item qty with order qty
                    int oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, orderName));
                    dbManager.updateQty(DatabaseHelper.TABLE_ITEM, orderCursor.getString(1), oldQty, orderQty, "min");

                }
                // delete order table which act like a cart or temporary
                dbManager.deleteTable(DatabaseHelper.TABLE_ORDER);

                // back to finalize activity
                Intent finalizeActivity = new Intent(getApplicationContext(),
                        FinalizeActivity.class);
                startActivity(finalizeActivity);
                break;
        }
    }
}
