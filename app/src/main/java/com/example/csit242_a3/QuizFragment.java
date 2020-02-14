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

import org.w3c.dom.Text;

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
    boolean isStart = false;



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

        final int selectedLevel = ((MainActivity)getActivity()).SELECTED_LEVEL-1; //1 = +, 2 = -
        int counter = 0;
        questionTitle.setText(((questionCategories.get(selectedLevel)).get(0)).toString());
        jumbleOptions(((questionCategories.get(selectedLevel)).get(0)));

        do {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuizFragment.this.qnAnswered++;
                    nextButton.setText("NEXT");
                    QuizFragment.this.qnNumDisplay++;
                    questionTitle.setText((QuizFragment.this.questionCategories.get(selectedLevel)).get(QuizFragment.this.qnAnswered).toString());
//                    jumbleOptions(QuizFragment.this.quizOneQns.get(QuizFragment.this.qnAnswered));
                    jumbleOptions(((questionCategories.get(selectedLevel)).get(QuizFragment.this.qnAnswered)));
                    questionNumber.setText("Question " + String.valueOf(QuizFragment.this.qnNumDisplay));

//                    if (String.valueOf(QuizFragment.this.quizOneQns.get(QuizFragment.this.qnAnswered).getAnswer()).equals(QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString())) {
//                        Log.d("ANS", "CORRECT!!!");
//                    }
                    if (qnAnswered == 2) {
                        nextButton.setText("FINISH");
                        nextButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("yay", "YAAAAAAAAAY");
                            }
                        });

                    }
                }


            });

            if (counter == 3) {
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("yay", "YAAAAAAAAAY");
                    }
                });
            }
            counter++;
        } while (counter < this.quizOneQns.size());


//
//        switch (((MainActivity)getActivity()).SELECTED_LEVEL) {
//            int counter = 0;
//            case 1:
//                for (Question q: quizOneQns) {
//                    Log.d("QN", q.toStringAnswer());
//                }
//                questionTitle.setText((this.quizOneQns.get(0)).toString());
//                jumbleOptions(QuizFragment.this.quizOneQns.get(0));
//                questionNumber.setText("Question 1");
//                do {
//                    nextButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            QuizFragment.this.qnAnswered++;
//                            nextButton.setText("NEXT");
//                            QuizFragment.this.qnNumDisplay++;
//                            questionTitle.setText(QuizFragment.this.quizOneQns.get(QuizFragment.this.qnAnswered).toString());
//                            jumbleOptions(QuizFragment.this.quizOneQns.get(QuizFragment.this.qnAnswered));
//                            questionNumber.setText("Question " + String.valueOf(QuizFragment.this.qnNumDisplay));
//
//                            if (String.valueOf(QuizFragment.this.quizOneQns.get(QuizFragment.this.qnAnswered).getAnswer()).equals(QuizFragment.this.options.get(QuizFragment.this.SELECTED_OPTION).getText().toString())) {
//                                Log.d("ANS", "CORRECT!!!");
//                            }
//                            if (qnAnswered == 2) {
//                                nextButton.setText("FINISH");
//                                nextButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Log.d("yay", "YAAAAAAAAAY");
//                                    }
//                                });
//
//                            }
//                        }
//
//
//                    });
//
//                    if (counter == 3) {
//                        nextButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Log.d("yay", "YAAAAAAAAAY");
//                            }
//                        });
//                    }
//                    counter++;
//                } while (counter < this.quizOneQns.size());
//
//                break;
//            case 2:
//                questionTitle.setText(this.quizTwoQns.get(0).toString());
//                break;
//            case 3:
//                questionTitle.setText(this.quizThreeQns.get(0).toString());
//                break;
//            case 4:
//                questionTitle.setText(this.quizFourQns.get(0).toString());
//                jumbleOptions(this.quizFourQns.get(0));
//                break;
//
//        }

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
        for (int i = 0; i < 3; i ++) {

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
                b.setText(String.format("%.2f", (q.getAnswer()) + rng.nextInt(5) +1));
        }

        int correctButtonIdx = (int)Math.random() *3;
        if (q.getSymbol() != '/') {
            options.get(correctButtonIdx).setText(String.valueOf(((int) q.getAnswer()) + ".0"));
        } else
            options.get(correctButtonIdx).setText(String.format("%.2f", (q.getAnswer())));
    }

}

