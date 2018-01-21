package com.journaldev.sqlite;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ViewDialog {

    public void showDialog(final EditItemActivity activity, String msg, DBManager dbManager, String table, long _id){
        final Dialog dialog = new Dialog(activity);
        final long id = _id;
        final String tableName = table;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.delete_dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.dbManager.delete(tableName, id);
                activity.returnHome();
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
