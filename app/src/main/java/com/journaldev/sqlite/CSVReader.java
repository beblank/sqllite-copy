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

    /**
     * The implementation of a CSV reader is important to read the spreadsheet files that Melvin has sent to me.
     * The sheer amount of data that is in the spreadsheet files need to be converted to
     * CSV files so that it can be read by the program. An array is insufficient due to the size of the list.
     * read csv file inside android resource and raw folder
     * override onClick method which implement from View.OnClickListener
     * @param activity - get activity which needed to get the resources folder
     * @param dbManager - to put the csv data info database
     */
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

    /**
     * itterate csv file line by line and split string value and pass them into array
     * when theres is a string inside value in csv then find the string value which is unit
     * then separate the unit from the digit and insert the value into database
     * when the value is not matched insert the csv value and add " " to the unit
     * @param is - InputStream to process
     * @param dbManager - to put the csv data info database
     */

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
            /*
            * IOException occurs when the program is reading a file, if its error then it only print error stack.
             * Exception handling is important because it aids with the debugging process of the application.
             * It make sure the app will not crash when being used
             */
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
