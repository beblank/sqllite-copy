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
    // read csv file inside android resource and raw folder
    public void getCSVFiles(Activity activity, DBManager dbManager){
        Resources res = activity.getResources();
        InputStream[] inputArray = {res.openRawResource(R.raw.pasco),
                res.openRawResource(R.raw.physics),
                res.openRawResource(R.raw.chemicals),
                res.openRawResource(R.raw.chemistry)};
        for (InputStream is:inputArray){
            readInputStream(is, dbManager);
        }
    }

    // itterate csv file line by line and split string value and pass them into array
    // when theres is a string inside value in csv then find the string value which is unit
    // then separate the unit from the digit and insert the value into database
    // when the value is not matched insert the csv value and add " " to the unit
    private void readInputStream(InputStream is, DBManager dbManager){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        String[] tokens = {};
        // regex to get digit
        Pattern intPattern = Pattern.compile("(\\d)");
        // regex to get string
        Pattern stringPattern = Pattern.compile("([a-zA-Z\\s]+)");
        try {
            while ((line = reader.readLine()) != null) {
                //set splitter
                tokens = line.split(",");
                Matcher stringMatcher = stringPattern.matcher(tokens[3]);
                if (stringMatcher.find()) {
                    String unit = stringMatcher.group(1);
                    Matcher matcher = intPattern.matcher(tokens[3]);
                    if (matcher.find()) {
                        dbManager.insert(DatabaseHelper.TABLE_ITEM, tokens[2].replace("'", "''"), matcher.group(1), unit, tokens[1]);

                    }
                } else {
                    dbManager.insert(DatabaseHelper.TABLE_ITEM, tokens[2].replace("'", "''"), tokens[3], " ", tokens[1]);
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
