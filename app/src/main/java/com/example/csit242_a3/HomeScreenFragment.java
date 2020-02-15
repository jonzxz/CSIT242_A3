package com.example.csit242_a3;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Home Screen Fragment, is initial Fragment to be displayed upon activity start

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment {

    public ArrayList<Button> quizBtns = new ArrayList<>();

    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Set background color
        this.getView().setBackgroundColor(Color.parseColor("#0275d8"));

        // Sets listener for "SET NAME" button to create AlertDialog to key in player name
        final Button setName = (Button) this.getView().findViewById(R.id.setNameBtn);
        final Button quizOneBtn = (Button) this.getView().findViewById(R.id.quizOne);
        final Button quizTwoBtn = (Button) this.getView().findViewById(R.id.quizTwo);
        final Button quizThreeBtn = (Button) this.getView().findViewById(R.id.quizThree);
        final Button quizFourBtn = (Button) this.getView().findViewById(R.id.quizFour);
        final Button startBtn = (Button) this.getView().findViewById(R.id.startBtn);
        final Button quitBtn = (Button) this.getView().findViewById(R.id.quitBtn);
        final Button instructionBtn = (Button) this.getView().findViewById(R.id.viewInstruction);
        final Button viewScoreBtn = (Button) this.getView().findViewById(R.id.viewScore);
        final TextView currentSessionScore = (TextView) this.getView().findViewById(R.id.currentSessScore);
        final TextView nameLabel = (TextView) (this.getView().findViewById(R.id.helloMsg));

        // If player name is not set, displays first, otherwise player name will be set and button will be disabled for the session
        if (((MainActivity)getActivity()).PLAYER_NAME == null) {
            nameLabel.setText(String.format("Hello! Please set a name!"));
        } else {
            nameLabel.setText(String.format("Hello %s!", ((MainActivity)getActivity()).PLAYER_NAME));
            setName.setEnabled(false);
        }

        // Displays current session score - required when player comes back to HomeScreen after completing a topic
        currentSessionScore.setText(String.format("Current Session: %s", String.valueOf(((MainActivity) getActivity()).getTotalScore())));

        // Populate ArrayList of Buttons for coloring purpose
        quizBtns.add(quizOneBtn);
        quizBtns.add(quizTwoBtn);
        quizBtns.add(quizThreeBtn);
        quizBtns.add(quizFourBtn);

        // SetNameButton listener
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getActivity());
                final EditText nameInput = new EditText(getActivity());
                DialogBuilder.setView(nameInput)
                        .setTitle("Enter your name!")
                        .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String input = nameInput.getText().toString();
                                TextView nameLabel = getActivity().findViewById(R.id.helloMsg);
                                nameLabel.setText(String.format("Hello %s!", input));
                                ((MainActivity)getActivity()).PLAYER_NAME = input;
                                setName.setEnabled(false);
                            }
                        });
                AlertDialog setNamePrompt = DialogBuilder.create();
                setNamePrompt.show();
            }
        });

        // Quiz button listeners, colorize selected quiz type and set Activity's SELECTED_LEVEL to be caught in QuizFragment
        quizOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizOneBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                ((MainActivity) getActivity()).SELECTED_LEVEL = 1;
               Log.d("BtnOne", "Level 1 selected!");
            }

        });

        quizTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizTwoBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                ((MainActivity) getActivity()).SELECTED_LEVEL = 2;
                Log.d("BtnTwo", "Level 2 selected!");
            }
        });

        quizThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizThreeBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                ((MainActivity) getActivity()).SELECTED_LEVEL = 3;
                Log.d("BtnThree", "Level 3 selected!");
            }
        });

        quizFourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizFourBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                ((MainActivity) getActivity()).SELECTED_LEVEL = 4;

                Log.d("BtnFour", "Level 4 selected!");
            }
        });

        // Start Button listener, checks that level and name are set and starts a QuizFragment
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(((MainActivity) getActivity()).isNameEmpty())) {
                    if (((MainActivity)getActivity()).SELECTED_LEVEL != 0) {
                        ((MainActivity) getActivity()).fragmentManager.beginTransaction().replace(R.id.ForFrag, new QuizFragment()).commit();
                    } else {
                        Toast.makeText(getActivity(), "Please select a level", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Enter your name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Quit Button listener, ****TO INSERT ROW INTO DB FOR DATETIME, CURRENT SESSION SCORE AND PLAYER NAME*****
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        // Instruction Button listener to create an Instruction dialog
        instructionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder DialogBuilder = new AlertDialog.Builder(getActivity());
                final TextView instructionText = new TextView(getActivity());
                instructionText.setText("Welcome to Kindergarten Math Quiz!\n\n" +
                        "To play the game, set your name\n" +
                        "Select the type of quiz you wish to attempt\n" +
                        "Level 1: Addition\nLevel 2: Subtraction\nLevel 3: Multiplication\n" +
                        "Level 4: Division\n\n" +
                        "Points are calculated based on (Level) * Number of correct answers\n\n" +
                        "Click START to begin!\n\n\n" +
                        "Do note that only the latest attempt for each quiz will be counted into your score\n\n" +
                        "e.g. If you scored 4 on Addition, retried and scored 2, only 2 points will be counted!");
                instructionText.setPadding(70, 50, 50, 50);
                instructionText.setTextSize(16);
                DialogBuilder.setView(instructionText)
                        .setTitle("Instructions").setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog instructionDialog = DialogBuilder.create();
                instructionDialog.show();

            }
        });

        // View Score Button listener *** SUPPOSED TO PULL LAST N ROWS OF DB****
        viewScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Session> list = ((MainActivity)getActivity()).dbHelper.getAllScores();
                for (Session s : list) {
                    String log = String.format("Name: %s, Date: %s, Score: %d", s.getName(), s.getDate(), s.getScore());
                    Log.d("RECORD", log);
                }
            }
        });

    }


    public void deselectQuizes() {
        for (Button b : quizBtns) {
            b.setBackgroundColor(Color.parseColor("#f7f7f7"));
        }

    }

}
