package com.journaldev.sqlite;

/**
 * Created by gian1 on 05/01/17.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends DatabaseActivity implements OnClickListener {

    private Button addTodoBtn;
    private EditText nameEditText;
    private EditText qtyEditText;
    private EditText unitEditText;
    private EditText roomEditText;

    // instantiate ui
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_record);

        nameEditText = (EditText) findViewById(R.id.name_edittext);
        qtyEditText = (EditText) findViewById(R.id.qty_edittext);
        unitEditText = (EditText) findViewById(R.id.unit_edittext);
        roomEditText = (EditText) findViewById(R.id.room_edittext);

        addTodoBtn = (Button) findViewById(R.id.add_record);

        addTodoBtn.setOnClickListener(this);
    }

    // on click add data based on input with name, qty, and room not null
    // if unit is empty then the input will be " "
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_record:
                if (nameEditText.getText().toString().equals("") || qtyEditText.getText().toString().equals("") || roomEditText.getText().toString().equals("")){
                    Toast.makeText(AddItemActivity.this, "You did not enter a valid input.", Toast.LENGTH_SHORT).show();
                }else{
                    dbManager.insert(DatabaseHelper.TABLE_ITEM, nameEditText.getText().toString(), qtyEditText.getText().toString(), unitEditText.getText().toString(), roomEditText.getText().toString());
                    Intent main = new Intent(AddItemActivity.this, ToolListActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    break;
                }
        }
    }

}