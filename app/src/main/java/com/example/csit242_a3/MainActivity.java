package com.example.csit242_a3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.graphics.Color;
import android.os.Bundle;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static String PLAYER_NAME;
    public static int SELECTED_LEVEL = 0;
    public static int[] SESSION_SCORE = {0, 0, 0, 0};
    public FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager.beginTransaction().replace(R.id.ForFrag, new HomeScreenFragment()).commit();
    }

    public boolean isNameEmpty() {
        return (this.PLAYER_NAME == null);
    }

    public int getTotalScore() {
        return Arrays.stream(this.SESSION_SCORE).sum();
    }
}
