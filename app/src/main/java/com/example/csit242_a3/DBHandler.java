package com.example.csit242_a3;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String ID = "ID";
    private static final String DB_NAME = "MathGame";
    private static final String QUESTIONS = "Questions";
    private static final String X = "X";
    private static final String Y = "Y";
    private static final String ANSWER = "ANSWER";
    private static final String SYMBOL = "SYMBOL";
    private static final String CATEGORY = "CATEGORY";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_QN_TABLE = "CREATE TABLE " + QUESTIONS + "(" + ID + " INTEGER PRIMARY KEY,"
                + X + " REAL," + Y + " REAL," + ANSWER + " REAL," + SYMBOL + " TEXT, " + CATEGORY + " TEXT)";
        db.execSQL(CREATE_QN_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QUESTIONS);
        // Creating tables again
        onCreate(db);
    }
}
