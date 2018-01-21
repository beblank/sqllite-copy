package com.journaldev.sqlite;

/**
 * Created by gian1 on 05/01/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends DatabaseActivity implements OnClickListener {

    private EditText nameText;
    private EditText qtyText;
    private EditText roomText;
    private Button updateBtn, deleteBtn;
    String table;
    String caller;
    int itemQty;

    private long _id;
    private String name;

    // instantiate ui and get passed data from intent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_edit_record);

        nameText = (EditText) findViewById(R.id.name_edittext);
        qtyText = (EditText) findViewById(R.id.qty_edittext);
        roomText = (EditText) findViewById(R.id.room_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        // get data from activity caller
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        String qty = intent.getStringExtra("qty");
        String room = intent.getStringExtra("room");
        table = intent.getStringExtra("table");

        _id = Long.parseLong(id);

        nameText.setText(name);
        qtyText.setText(qty);
        roomText.setText(room);

        itemQty = Integer.parseInt(dbManager.fetchQty(DatabaseHelper.TABLE_ITEM, name));

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // when update button tapped
            // update table with input text
            case R.id.btn_update:
                if (nameText.getText().toString().equals("") || qtyText.getText().toString().equals("") || roomText.getText().toString().equals("")) {
                    Toast.makeText(EditItemActivity.this, "You did not enter a valid input.", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.update(table, _id, nameText.getText().toString(), qtyText.getText().toString(), roomText.getText().toString());
                    this.returnHome();
                    break;
                }
            case R.id.btn_delete:
                ViewDialog alert = new ViewDialog();
                alert.showDialog(this, "Are you sure you want to delete? ", table, _id);
                break;
        }
    }

    // return to activity caller
    public void returnHome() {
        Log.d("TAG", "returnHome: " + caller);
            Intent home = new Intent(getApplicationContext(), ToolListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
    }
}
