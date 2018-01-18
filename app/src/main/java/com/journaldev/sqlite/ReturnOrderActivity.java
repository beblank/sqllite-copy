package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ReturnOrderActivity extends DatabaseActivity implements View.OnClickListener {

    TextView itemName;
    EditText updateQty;
    Button returnButton;
    Button updateButton;

    Cursor orderCursor;
    Cursor itemCursor;

    String orderName;
    int orderQty;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order);

        itemName = (TextView)findViewById(R.id.return_order_item_name);
        updateQty = (EditText) findViewById(R.id.return_order_qty_edit);
        returnButton = (Button) findViewById(R.id.return_order_button);
        updateButton = (Button) findViewById(R.id.update_order_button);

        orderCursor = dbManager.fetch(DatabaseHelper.TABLE_ORDER);
        itemCursor = dbManager.fetch(DatabaseHelper.TABLE_ITEM);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        itemName.setText(name);

        returnButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent finalize = new Intent(getApplicationContext(), FinalizeActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch (v.getId()){
            case R.id.return_order_button:
                for(orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
                    orderName = orderCursor.getString(1);
                    orderQty = orderCursor.getInt(2);
                    int oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, orderName));
                    dbManager.updateQty(orderCursor.getString(1), oldQty, orderQty, "plus");
                }
                dbManager.deleteByName(DatabaseHelper.TABLE_ORDER, name);


                startActivity(finalize);
                break;
            case R.id.update_order_button:
                for(orderCursor.moveToFirst(); !orderCursor.isAfterLast(); orderCursor.moveToNext()) {
                    orderName = orderCursor.getString(1);
                    orderQty = (orderCursor.getInt(2) - Integer.parseInt(updateQty.getText().toString()));
                    int oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, orderName));
                    dbManager.updateQty(orderCursor.getString(1), oldQty, orderQty, "plus");
                    dbManager.updateName(DatabaseHelper.TABLE_ORDER, orderName, String.valueOf(orderQty));
                }
                if(orderQty < 1){
                    dbManager.deleteByName(DatabaseHelper.TABLE_ORDER, name);
                }


                startActivity(finalize);
                break;
        }

    }
}
