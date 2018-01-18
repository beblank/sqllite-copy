package com.journaldev.sqlite;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_order);

        itemName = (TextView)findViewById(R.id.return_order_item_name);
        updateQty = (EditText) findViewById(R.id.return_order_qty_edit);
        returnButton = (Button) findViewById(R.id.return_order_button);
        updateButton = (Button) findViewById(R.id.update_order_button);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        itemName.setText(name);

        returnButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_order_button:
                break;
            case R.id.update_order_button:
                break;
        }

    }
}
