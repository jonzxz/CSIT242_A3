package com.example.csit242_a3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.csit242_a3.Session;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MathQuiz";
    private static final String TABLE_SCORE_BOARD = "Scoreboard";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCOREBOARD_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT", TABLE_SCORE_BOARD, KEY_ID, KEY_NAME, KEY_DATE, KEY_SCORE);
        db.execSQL(CREATE_SCOREBOARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_SCORE_BOARD));
    }

    public void addScore(Session s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, s.getName());
        values.put(KEY_DATE, s.getDate());
        values.put(KEY_SCORE, s.getScore());
        db.insert(TABLE_SCORE_BOARD, null, values);
        db.close();
    }

    public Session getSession(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID, KEY_NAME, KEY_DATE, KEY_SCORE};
        String selection = String.format("%s=?", KEY_ID);
        String[] selectionArgs = { String.valueOf(id)};
        Cursor cursor = db.query(TABLE_SCORE_BOARD, columns, selection, selectionArgs, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Session s = new Session(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        return s;
    }

}
