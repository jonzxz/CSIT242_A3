package com.example.csit242_a3;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment {

    public static String PLAYER_NAME = "";
    public static int SELECTED_LEVEL = 0;
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
        this.getView().setBackgroundColor(Color.parseColor("#0275d8"));

        // Sets listener for "SET NAME" button to create AlertDialog to key in player name
        Button setName = (Button) this.getView().findViewById(R.id.setNameBtn);
        final Button quizOneBtn = (Button) this.getView().findViewById(R.id.quizOne);
        final Button quizTwoBtn = (Button) this.getView().findViewById(R.id.quizTwo);
        final Button quizThreeBtn = (Button) this.getView().findViewById(R.id.quizThree);
        final Button quizFourBtn = (Button) this.getView().findViewById(R.id.quizFour);
        final Button startBtn = (Button) this.getView().findViewById(R.id.startBtn);
        final Button quitBtn = (Button) this.getView().findViewById(R.id.quitBtn);
        final Button instructionBtn = (Button) this.getView().findViewById(R.id.viewInstruction);
        final Button viewScoreBtn = (Button) this.getView().findViewById(R.id.viewScore);

        quizBtns.add(quizOneBtn);
        quizBtns.add(quizTwoBtn);
        quizBtns.add(quizThreeBtn);
        quizBtns.add(quizFourBtn);

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
                                HomeScreenFragment.this.PLAYER_NAME = input;
                                ((MainActivity)getActivity()).PLAYER_NAME = input;
                            }
                        });
                AlertDialog setNamePrompt = DialogBuilder.create();
                setNamePrompt.show();

            }
        });

        quizOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizOneBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                HomeScreenFragment.this.SELECTED_LEVEL = 1;
                Log.d("BtnOne", "Level 1 selected!");
            }

        });

        quizTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizTwoBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                HomeScreenFragment.this.SELECTED_LEVEL = 2;
                Log.d("BtnTwo", "Level 2 selected!");
            }
        });

        quizThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizThreeBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                HomeScreenFragment.this.SELECTED_LEVEL = 3;
                Log.d("BtnThree", "Level 3 selected!");
            }
        });

        quizFourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectQuizes();
                quizFourBtn.setBackgroundColor(Color.parseColor("#f0ad4e"));
                HomeScreenFragment.this.SELECTED_LEVEL = 4;
                Log.d("BtnFour", "Level 4 selected!");
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getSelectedLevel()) {
                    case 0:
                        Toast.makeText(getActivity(), "Please select a level", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        fragmentManager.beginTransaction().replace(R.id.ForFrag, new HomeScreenFragment()).commit();
                        ((MainActivity)getActivity()).fragmentManager.beginTransaction().replace(R.id.ForFrag, new AddFrag()).commit();
                        Log.d("Level 1 selected", "Starting level 1!");
                        break;
                    case 2:
                        Log.d("Level 2 selected", "Starting level 2!");
                        break;
                    case 3:
                        Log.d("Level 3", "Starting level 3!");
                        break;
                    case 4:
                        Log.d("Level 4", "Starting level 4!");
                        break;
                    default:
                        Toast.makeText(getActivity(), "Please select a level", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

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
                        "Click START to begin!");
                instructionText.setPadding(70, 50, 50, 50);
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

        viewScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    public void deselectQuizes() {
        for (Button b : quizBtns) {
            b.setBackgroundColor(Color.parseColor("#f7f7f7"));
        }

    }

    private int getSelectedLevel() {
        return this.SELECTED_LEVEL;
    }

}
