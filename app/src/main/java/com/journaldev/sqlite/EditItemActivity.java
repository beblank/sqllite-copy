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

public class EditItemActivity extends Activity implements OnClickListener {

    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;
    String table;
    String caller;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_edit_record);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        table = intent.getStringExtra("table");
        caller = intent.getStringExtra("caller");

        _id = Long.parseLong(id);

        titleText.setText(name);
        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                if (titleText.getText().toString().equals("") || descText.getText().toString().equals("")){
                    Toast.makeText(EditItemActivity.this, "You did not enter a valid input.", Toast.LENGTH_SHORT).show();
                }else{
                    dbManager.update(table, _id, titleText.getText().toString(), descText.getText().toString());

                    Intent main = new Intent(EditItemActivity.this, ToolListActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    this.returnHome();
                    break;
                }

            case R.id.btn_delete:
                dbManager.delete(table, _id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Log.d("TAG", "returnHome: " + caller);
        if (caller.equals("order")){
            Intent home = new Intent(getApplicationContext(), OrderListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
        } else if (caller.equals("item")){
            Intent home = new Intent(getApplicationContext(), ToolListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
        } else if (caller.equals("final")){
            Intent home = new Intent(getApplicationContext(), FinalizeActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(home);
        }
    }
}
