package com.example.csit242_a3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static String PLAYER_NAME;
    public FragmentManager fragmentManager = getSupportFragmentManager();
    public DBHandler db = new DBHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager.beginTransaction().replace(R.id.ForFrag, new HomeScreenFragment()).commit();
//        Log.d("DB NAME:", db.getDatabaseName());
//        generateQuestion();
//        SQLiteDatabase databaseIns = db.getReadableDatabase();
//        Cursor cursor = databaseIns.rawQuery("SELECT * FROM Questions", null);
//        Log.d("ADDED ROW!!:", cursor.getString(0) + cursor.getString(1) + cursor.getString(2));
    }

    public void generateQuestion() {
        SQLiteDatabase dbIns = db.getWritableDatabase();
        this.db.addQuestion(new Question(1, 2, 3, "+"));
    }
}
