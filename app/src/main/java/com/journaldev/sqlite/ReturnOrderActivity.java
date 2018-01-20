package com.journaldev.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnOrderActivity extends DatabaseActivity implements View.OnClickListener {

    TextView itemName;
    EditText updateQty;
    Button returnButton;
    Button updateButton;

    Cursor orderCursor;
    Cursor itemCursor;

    String orderName;
    int orderQty;
    int oldQty;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order);

        itemName = (TextView)findViewById(R.id.return_order_item_name);
        updateQty = (EditText) findViewById(R.id.return_order_qty_edit);
        returnButton = (Button) findViewById(R.id.return_order_button);
        updateButton = (Button) findViewById(R.id.update_order_button);

        orderCursor = dbManager.fetch(DatabaseHelper.TABLE_FINAL);
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
                orderQty = orderCursor.getInt(2);
                oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, name));
                dbManager.updateQty(name, oldQty, orderQty, "plus");
                dbManager.deleteByName(DatabaseHelper.TABLE_FINAL, name);
                startActivity(finalize);
                break;
            case R.id.update_order_button:
                if (updateQty.getText().toString() == "" || updateQty.getText().toString().isEmpty()){
                    Toast.makeText(this, "You did not enter a valid input.", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(updateQty.getText().toString()) > Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_FINAL, name))){
                    Toast.makeText(this, "Amount not available", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    orderQty = (Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_FINAL, name)) - Integer.parseInt(updateQty.getText().toString()));
                    oldQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, name));
                    dbManager.updateQty(name, oldQty, orderQty, "plus");
                    dbManager.updateName(DatabaseHelper.TABLE_FINAL, name, String.valueOf(orderQty));
                    if(orderQty < 1){
                        dbManager.deleteByName(DatabaseHelper.TABLE_FINAL, name);
                    }
                    startActivity(finalize);
                    break;
                }
        }

    }
}
