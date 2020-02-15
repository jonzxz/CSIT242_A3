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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {

    ArrayList<Question> quizOneQns = new ArrayList<>();
    ArrayList<Question> quizTwoQns = new ArrayList<>();
    ArrayList<Question> quizThreeQns = new ArrayList<>();
    ArrayList<Question> quizFourQns = new ArrayList<>();
    ArrayList<Button> options = new ArrayList<>();
    ArrayList<ArrayList<Question>> questionCategories = new ArrayList<>();
    int numQnAnswered = 0;
    int qnNumDisplay = numQnAnswered +1;
    int numCorrect = 0;
    private static int SELECTED_OPTION;
    private static int NUMBER_OF_QNS = 5;
    Random rng = new Random();


    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        makeQuestions();
        questionCategories.add(quizOneQns);
        questionCategories.add(quizTwoQns);
        questionCategories.add(quizThreeQns);
        questionCategories.add(quizFourQns);
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Element initialization
        final TextView questionNumber = (TextView) (getActivity().findViewById(R.id.qnNum));
        final TextView questionTitle = (TextView) (getActivity().findViewById(R.id.qnTitle));
        final Button optionOne = (Button) (getActivity().findViewById(R.id.optionOne));
        final Button optionTwo = (Button) (getActivity().findViewById(R.id.optionTwo));
        final Button optionThree = (Button) (getActivity().findViewById(R.id.optionThree));
        final Button nextButton = (Button) (getActivity().findViewById(R.id.nextBtn));

        // -1 due to prevention of OBO since variable is used as index
        final int selectedLevel = ((MainActivity)getActivity()).SELECTED_LEVEL-1;
        numCorrect = 0;
        int counter = 0;

        // Populate ArrayList of Buttons
        options.add(optionOne);
        options.add(optionTwo);
        options.add(optionThree);

        // Reset score of currently selected level to 0
        ((MainActivity) getActivity()).SESSION_SCORE[selectedLevel] = 0;


        // Initialize first question
        questionNumber.setText("Question " + String.valueOf(QuizFragment.this.qnNumDisplay));
        questionTitle.setText(((questionCategories.get(selectedLevel)).get(0)).toString());
        jumbleOptions(((questionCategories.get(selectedLevel)).get(0)));

        do {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Each Question actions
                    deselectOptions();
                    //Instantiate Question object for each Question
                    final Question q = (QuizFragment.this.questionCategories.get(selectedLevel)).get(QuizFragment.this.numQnAnswered);

                    // Checks if selected answer is equals to Question's answer
                    boolean test =checkAnswer(String.valueOf(q.getAnswer()),
                            QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString());

                    // Debugging line to see if answer is correct
                    Log.d("CHECK", String.valueOf(test));

                    // Point accumulation
                    if (test) {
                        ((MainActivity) getActivity()).SESSION_SCORE[selectedLevel] += 1;
                        numCorrect++;
                    }

                    // Setup for next question
                    QuizFragment.this.numQnAnswered++;
                    QuizFragment.this.qnNumDisplay++;
                    if (numQnAnswered != 0) {
                        questionNumber.setText("Question " + String.valueOf(QuizFragment.this.qnNumDisplay));
                        questionTitle.setText(((questionCategories.get(selectedLevel)).get(numQnAnswered)).toString());
                        jumbleOptions(((questionCategories.get(selectedLevel)).get(QuizFragment.this.numQnAnswered)));
                    }

                    // At 4th question, replace listener for next question with result screen
                    // There is probably a better way to implement but this will do for now
                    if (numQnAnswered == NUMBER_OF_QNS-1) {
                        nextButton.setText("FINISH");
                        nextButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // Checks if selected answer is equals to Question's answer
                                boolean test = checkAnswer(String.valueOf((questionCategories.get(selectedLevel)).get(numQnAnswered).getAnswer()),
                                        QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString());

                                if (test) {
                                    ((MainActivity)getActivity()).SESSION_SCORE[selectedLevel] += 1;
                                    numCorrect++;
                                }
                                // Debugging purposes
                                Log.d("CHECK", String.valueOf(test));
                                Log.d("Final score", String.valueOf(((MainActivity)getActivity()).SESSION_SCORE[selectedLevel]));
                                ((MainActivity)getActivity()).SESSION_SCORE[selectedLevel] = numCorrect * (selectedLevel+1);
                                // Displays result dialog
                                getResultDialog().show();
                            }
                        });
                    }
                }
            });

            counter++;
        } while (counter < NUMBER_OF_QNS);


        // Listener initialization for option buttons
        optionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectOptions();
                optionOne.setBackgroundColor(Color.parseColor("#5cb85c"));
                QuizFragment.this.SELECTED_OPTION = 0;
            }
        });

        optionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectOptions();
                optionTwo.setBackgroundColor(Color.parseColor("#5cb85c"));
                QuizFragment.this.SELECTED_OPTION = 1;
            }
        });

        optionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectOptions();
                optionThree.setBackgroundColor(Color.parseColor("#5cb85c"));
                QuizFragment.this.SELECTED_OPTION = 2;
            }
        });

    }

    // Function to populate ArrayList of Question for each topic that fulfills assignment appendix
    public void makeQuestions() {

        // For Addition Qn 1, 2 / Sub Qn 1, 2
        for (int i = 0; i < 2; i++) {
            int xSub, ySub;
            this.quizOneQns.add(new Question(generateInt(1, 9), generateInt(1, 9), '+'));
            do {
                xSub = generateInt(1, 9);
                ySub = generateInt(1, 9);
            } while (xSub <= ySub);
            this.quizTwoQns.add(new Question(xSub, ySub, '-'));
        }

        // For Addition Qn 3, 4 / Sub Qn 3, 4
        for (int i = 0; i < 2; i++) {
            this.quizOneQns.add(new Question(generateInt(1, 9), generateInt(10, 99), '+'));
            this.quizTwoQns.add(new Question(generateInt(10, 99), generateInt(1, 9), '-'));
        }

        // Addition Qn 5
        this.quizOneQns.add(new Question(generateInt(10, 99), generateInt(10, 99), '+'));

        // Subtraction Qn 5
        int xSub, ySub;
        do {
            xSub = generateInt(10, 99);
            ySub = generateInt(10, 99);
        } while (xSub <= ySub);
        this.quizTwoQns.add(new Question(xSub, ySub, '-'));

        // Multiplication Qn 1
        this.quizThreeQns.add(new Question(generateInt(1, 5), generateInt(1, 5), '*'));
        // Multiplication Qn 2
        this.quizThreeQns.add(new Question(generateInt(6, 9), generateInt(1, 5), '*'));
        // Multiplication Qn 3
        this.quizThreeQns.add(new Question(generateInt(1, 5), generateInt(6, 9), '*'));
        // Multiplication Qn 4
        this.quizThreeQns.add(new Question(generateInt(6, 9), generateInt(6, 9), '*'));
        // Multiplication Qn 5
        this.quizThreeQns.add(new Question(generateInt(10, 20), generateInt(1, 9), '*'));

        // Division Qn 1, 2
        for (int i = 0; i < 2; i ++) {
            int xDiv, yDiv;
            do {
                xDiv = generateInt(4, 9);
                yDiv = generateInt(2, 3);
            } while (xDiv % yDiv != 0);
            this.quizFourQns.add(new Question(xDiv, yDiv, '/'));
        }

        // Division Qn 3, 4
        for (int i = 0; i < 2; i ++) {
            int xDiv, yDiv;
            do {
                xDiv = generateInt(10, 20);
                yDiv = getTwoThreeFive();
            } while (xDiv % yDiv != 0);
            this.quizFourQns.add(new Question(xDiv, yDiv, '/'));
        }

        // Division Qn 5
        int xDiv, yDiv;
        do {
            xDiv = generateInt(22, 30);
            yDiv = getTwoThreeFive();
        } while (xDiv % yDiv != 0);
        this.quizFourQns.add(new Question(xDiv, yDiv, '/'));
    }

    // Populate all Button options with wrong answer, then randomly replace one with correct answer
    private void jumbleOptions(Question q) {
        for (Button b: options) {
            b.setText(String.format("%d", (q.getAnswer() + generateInt(1, 15))));
        }
        int correctButtonIdx = generateInt(0, 2);
        options.get(correctButtonIdx).setText(String.format("%d", (q.getAnswer())));
    }


    // Returns a bool for question, for debugging purposes
    private boolean checkAnswer(String a, String b) {
        return a.equals(b);
    }


    // Returns integer of range lBound and uBound, both ends inclusive
    private int generateInt(int lBound, int uBound) {
        return lBound + (int)(Math.random() * ((uBound - lBound) +1));
    }

    // Randomly returns a 2, 3 or 5
    private int getTwoThreeFive() {
        int[] TwoThreeFive = {2, 3, 5};
        int idx = generateInt(0, 2);
        return TwoThreeFive[idx];
    }


    private void deselectOptions() {
        for (Button b : options) {
            b.setBackgroundColor(Color.parseColor("#f7f7f7"));
        }
    }
    private AlertDialog getResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String playerName = ((MainActivity)getActivity()).PLAYER_NAME;
        int numCurrentQuizWrong = this.NUMBER_OF_QNS - this.numCorrect;
        int multipliedScore = this.numCorrect * ((MainActivity)getActivity()).SELECTED_LEVEL;
        int currentTotalScore = ((MainActivity)getActivity()).getTotalScore();
        final TextView results = new TextView(getActivity());
        results.setText(String.format("Well done %s, you have %d correct and %d incorrect answers or %d points for this topic\n" +
                "Overall in this session, you have %d points", playerName, this.numCorrect, numCurrentQuizWrong, multipliedScore, currentTotalScore));
        results.setTextSize(20);
        results.setPadding(70, 50, 50, 50);
        builder.setView(results)
                .setCancelable(false)
                .setTitle("Quiz Complete!")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).fragmentManager.beginTransaction().replace(R.id.ForFrag, new QuizFragment()).commit();
                ((MainActivity) getActivity()).SESSION_SCORE[((MainActivity)getActivity()).SELECTED_LEVEL-1] = 0;

            }
        }).setNeutralButton("Attempt another quiz", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("clicked", "HOME");
                ((MainActivity) getActivity()).fragmentManager.beginTransaction().replace(R.id.ForFrag, new HomeScreenFragment()).commit();
            }
        });
        AlertDialog resultDialog = builder.create();
        return resultDialog;
    }
}

