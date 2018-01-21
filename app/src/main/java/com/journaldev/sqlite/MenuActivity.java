package com.journaldev.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity implements View.OnClickListener {

    Button toolButton;
    Button orderButton;

    private DBManager dbManager;

    //instantiate ui and create predetermined list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolButton = (Button) findViewById(R.id.tools_btn);
        orderButton = (Button) findViewById(R.id.order_btn);

        dbManager = new DBManager(this);
        dbManager.open();
        dbManager.setActivity(this);
        dbManager.predeterminedList();

        toolButton.setOnClickListener(this);
        orderButton.setOnClickListener(this);
    }

    // on tools button clicked, start ToolListActivity
    // on order button clicked, start OrderListActivity
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tools_btn:
                Intent toolList = new Intent(getApplicationContext(),
                        ToolListActivity.class);
                startActivity(toolList);
                break;

            case R.id.order_btn:
                Intent orderList = new Intent(getApplicationContext(),
                        OrderListActivity.class);
                startActivity(orderList);
                break;
        }
    }
}
