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


    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        makeQuestions();
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView questionNumber = (TextView) (getActivity().findViewById(R.id.qnNum));
        TextView questionTitle = (TextView) (getActivity().findViewById(R.id.qnTitle));
        Button optionOne = (Button) (getActivity().findViewById(R.id.optionOne));
        Button optionTwo = (Button) (getActivity().findViewById(R.id.optionTwo));
        Button optionThree = (Button) (getActivity().findViewById(R.id.optionThree));
        options.add(optionOne);
        options.add(optionTwo);
        options.add(optionThree);

       switch (((MainActivity)getActivity()).SELECTED_LEVEL) {
           case 1:
               questionTitle.setText(this.quizOneQns.get(0).toString());
               jumbleOptions(this.quizOneQns.get(0));
               break;
           case 2:
               questionTitle.setText(this.quizTwoQns.get(0).toString());
               break;
           case 3:
               questionTitle.setText(this.quizThreeQns.get(0).toString());
               break;
           case 4:
               questionTitle.setText(this.quizFourQns.get(0).toString());
               jumbleOptions(this.quizFourQns.get(0));
               break;

        }

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
                b.setText(String.valueOf(((int) q.getAnswer()) + rng.nextInt(5) +1));
            }
            b.setText(String.format("%.2f", (q.getAnswer()) + rng.nextInt(5) +1));
        }

        int correctButtonIdx = (int)Math.random() *3;
        if (q.getSymbol() != '/') {
            options.get(correctButtonIdx).setText(String.valueOf(((int) q.getAnswer()) + rng.nextInt(5)));
        }
        options.get(correctButtonIdx).setText(String.format("%.2f", (q.getAnswer())));
    }

}

