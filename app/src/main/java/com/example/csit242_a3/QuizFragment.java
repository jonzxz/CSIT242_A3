package com.example.csit242_a3;


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

import static com.example.csit242_a3.MainActivity.SELECTED_LEVEL;


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
    int qnAnswered = 0;
    int qnNumDisplay = qnAnswered+1;
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
        final TextView questionNumber = (TextView) (getActivity().findViewById(R.id.qnNum));
        final TextView questionTitle = (TextView) (getActivity().findViewById(R.id.qnTitle));
        final Button optionOne = (Button) (getActivity().findViewById(R.id.optionOne));
        final Button optionTwo = (Button) (getActivity().findViewById(R.id.optionTwo));
        final Button optionThree = (Button) (getActivity().findViewById(R.id.optionThree));
        final Button nextButton = (Button) (getActivity().findViewById(R.id.nextBtn));

        options.add(optionOne);
        options.add(optionTwo);
        options.add(optionThree);

        final int selectedLevel = ((MainActivity)getActivity()).SELECTED_LEVEL-1; //1 = +, 2 = - but -1 here cuz array index
        int counter = 0;

        // Initialize first question
        questionNumber.setText("Question " + String.valueOf(QuizFragment.this.qnNumDisplay));
        questionTitle.setText(((questionCategories.get(selectedLevel)).get(0)).toString());
        jumbleOptions(((questionCategories.get(selectedLevel)).get(0)));

        do {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Increment question number related variable
                    QuizFragment.this.qnAnswered++;
                    QuizFragment.this.qnNumDisplay++;
                    final Question q = (QuizFragment.this.questionCategories.get(selectedLevel)).get(QuizFragment.this.qnAnswered);
                    //final String selectedOptionValue = QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString();

                    // Replace displayable elements with next question
                    //Question Number
                    questionNumber.setText("Question " + String.valueOf(QuizFragment.this.qnNumDisplay));

                    //Title
                    questionTitle.setText(q.toString());

                    //Options
                    jumbleOptions(((questionCategories.get(selectedLevel)).get(QuizFragment.this.qnAnswered)));

                    boolean test =checkAnswer(String.valueOf(q.getAnswer()),
                            QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString());

                    Log.d("CHECK", String.valueOf(test));
                    ((MainActivity)getActivity()).SESSION_SCORE[selectedLevel] += countScore(String.valueOf(q.getAnswer()),
                            QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString());

                    // At 4th question, replace listener for next question with result screen
                    if (qnAnswered == NUMBER_OF_QNS-1) {
                        nextButton.setText("FINISH");
                        nextButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean test =checkAnswer(String.valueOf(q.getAnswer()),
                                        QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString());
                                ((MainActivity)getActivity()).SESSION_SCORE[selectedLevel] += countScore(String.valueOf(q.getAnswer()),
                                        QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString());
                                Log.d("CHECK", String.valueOf(test));
                                Log.d("Final score", String.valueOf(((MainActivity)getActivity()).SESSION_SCORE[selectedLevel]));
                            }
                        });
                    }
                }


            });

            counter++;
        } while (counter < this.quizOneQns.size());


        optionOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizFragment.this.SELECTED_OPTION = 0;
            }
        });

        optionTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizFragment.this.SELECTED_OPTION = 1;
            }
        });

        optionThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        int correctButtonIdx = (int)Math.random() *3;
        options.get(correctButtonIdx).setText(String.format("%d", (q.getAnswer())));
    }


    // Returns a bool for question, for debugging purposes
    private boolean checkAnswer(String a, String b) {
        return a.equals(b);
    }

    // Returns score earned for question by checking if values are the same
    private int countScore(String a, String b) {
        return (a.equals(b)) ? 1 : 0;
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
}

