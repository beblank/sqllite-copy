package com.journaldev.sqlite;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewDialog implements View.OnClickListener {

    private EditItemActivity activity;
    private String table;
    private long id;
    private Dialog dialog;

    // show dialog
    public void showDialog(EditItemActivity activity, String msg, String table, long _id){
        dialog = new Dialog(activity);
        id = _id;
        this.table = table;
        this.activity = activity;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.delete_dialog);

        TextView text = dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button okButton = dialog.findViewById(R.id.ok_btn);
        Button cancelButton = dialog.findViewById(R.id.cancel_btn);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        dialog.show();

    }

    // when click ok, delete table, back to tool list activity and close the dialog
    // when cancel clicked, close the dialog
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                activity.dbManager.delete(table, id);
                activity.returnHome();
                dialog.dismiss();
                break;

            case R.id.cancel_btn:
                dialog.dismiss();
                break;
        }
    }
}
