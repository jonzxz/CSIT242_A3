package com.example.csit242_a3;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        TextView questionTitle = (TextView) (getActivity().findViewById(R.id.qnTitle));

        questionTitle.setText(this.quizOneQns.get(0).toString());

        for (Question q : this.quizOneQns) {
            int i = 1;
            Log.d(String.valueOf(i), q.toStringAnswer());
            i++;
        }
        for (Question q : this.quizTwoQns) {
            int i = 1;
            Log.d(String.valueOf(i), q.toStringAnswer());
            i++;
        }
        for (Question q : this.quizThreeQns) {
            int i = 1;
            Log.d(String.valueOf(i), q.toStringAnswer());
            i++;
        }
        for (Question q : this.quizFourQns) {
            int i = 1;
            Log.d(String.valueOf(i), q.toStringAnswer());
            i++;
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

}

