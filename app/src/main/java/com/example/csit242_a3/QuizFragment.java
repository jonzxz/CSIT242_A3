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
                    // At 3rd question, replace listener for next question with result screen
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

    public void makeQuestions() {
        int xOne, yOne, xTwo, yTwo, xThree, yThree, xFour, yFour;
        for (int i = 0; i < NUMBER_OF_QNS; i ++) {

            xOne = (int)(Math.random() * 10) + 1;
            yOne = (int)(Math.random() * 10) + 1;
            this.quizOneQns.add(new Question(xOne, yOne, '+'));

            do {
                xTwo = (int)(Math.random() * 10) + 1;
                yTwo = (int)(Math.random() * 10) + 1;
            } while (xTwo < yTwo);
            this.quizTwoQns.add(new Question(xTwo, yTwo, '-'));


            xThree = (int)(Math.random() * 10) + 1;
            yThree = (int)(Math.random() * 10) + 1;
            this.quizThreeQns.add(new Question(xThree, yThree, '*'));

            do {
                xFour = (int) (Math.random() * 10) + 1;
                yFour = (int) (Math.random() * 10) + 1;
            } while (xFour < yFour);
            this.quizFourQns.add(new Question(xFour, yFour, '/'));
        }
    }

    private void jumbleOptions(Question q) {
        Random rng = new Random();
        for (Button b: options) {

            if (q.getSymbol() != '/') {
                b.setText(String.valueOf(((int) q.getAnswer()) + rng.nextInt(5) +1) + ".0");
            } else
                b.setText(String.format("%.1f", (q.getAnswer()) + rng.nextInt(5) +1));
        }

        int correctButtonIdx = (int)Math.random() *3;
        if (q.getSymbol() != '/') {
            options.get(correctButtonIdx).setText(String.valueOf(((int) q.getAnswer()) + ".0"));
        } else
            options.get(correctButtonIdx).setText(String.format("%.1f", (q.getAnswer())));
    }


    private boolean checkAnswer(String a, String b) {
        return a.equals(b);
    }

    private int countScore(String a, String b) {
        return (a.equals(b)) ? 1 : 0;
    }
}

