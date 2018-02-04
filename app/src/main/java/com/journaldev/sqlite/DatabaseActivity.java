package com.journaldev.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

// refactor most Activity since they need dbManager instantiation
public class DatabaseActivity extends Activity {
    protected DBManager dbManager;

    /**
     * instantiate DBManager to be used by inherited activity
     * which override onCreate methode from Activity class
     * @param savedInstanceState - If the activity is being re-initialized
     *      after previously being shut down then this Bundle contains
     *      the data it most recently supplied in onSaveInstanceState(Bundle).
     *      Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = new DBManager(this);
        dbManager.open();
    }
}
