package com.journaldev.sqlite;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVReader {

    public void getCSVFiles(Activity activity, DBManager dbManager){

        Resources res = activity.getResources();
        InputStream[] inputArray = {res.openRawResource(R.raw.pasco),
                res.openRawResource(R.raw.physics)};
        for (InputStream is:inputArray){
            readInputStream(is, dbManager);
        }
    }

    private void readInputStream(InputStream is, DBManager dbManager){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        String[] tokens = {};
        Pattern intPattern = Pattern.compile("(\\d)");
        Pattern stringPattern = Pattern.compile("([A-Za-z]+)");
        try {
            while ((line = reader.readLine()) != null) {
                //set splitter
                tokens = line.split(",");
                Matcher stringMatcher = stringPattern.matcher(tokens[3]);
                if (stringMatcher.find()) {
                    String unit = stringMatcher.group(1);
                    Matcher matcher = intPattern.matcher(tokens[3]);
                    if (matcher.find()) {
                        Log.d("dodol", "getCSVFiles: " + tokens[2] + " " + matcher.group(1) + " " + unit + " " + tokens[1]);
                        dbManager.insert(DatabaseHelper.TABLE_ITEM, tokens[2], matcher.group(1), unit, tokens[1]);

                    }
                } else {
                    dbManager.insert(DatabaseHelper.TABLE_ITEM, tokens[2], tokens[3], " ", tokens[1]);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
