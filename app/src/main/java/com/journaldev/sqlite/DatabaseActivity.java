package com.journaldev.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

// refactor most Activity since they need dbManager instantiation
public class DatabaseActivity extends Activity {
    protected DBManager dbManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(this);
        dbManager.open();
    }
}
