package edu.uw.ischool.maga.relive;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class QuizSelectFragment extends Fragment {

    Button postButton;
    Button picButton;
    Button bothButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_select, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        // Select quiz, set int in MainApp accordingly
        postButton = (Button) view.findViewById(R.id.postSelect);
        picButton = (Button) view.findViewById(R.id.pictureSelect);
        bothButton = (Button) view.findViewById(R.id.bothSelect);

        picButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), GameActivity.class);
                MainApp.quizType = 0; //picture type
                MainApp.newQuiz();
                startActivity(i);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), GameActivity.class);
                MainApp.quizType = 1; //posts
                MainApp.newQuiz();
                Log.i("App","Display:"+MainApp.quiz[0].dataToShow);
                Log.i("App","Friend Name:"+MainApp.quiz[0].correctFriend.name);
                Log.i("App","Answers:" + MainApp.quiz[0].friendOptions[0]+ " "+ MainApp.quiz[3].friendOptions[0].name+ " "+ MainApp.quiz[3].friendOptions[1].name+ " "+ MainApp.quiz[3].friendOptions[2].name+ " "+ MainApp.quiz[3].friendOptions[3].name);

                startActivity(i);
            }
        });

        bothButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), GameActivity.class);
                MainApp.quizType = 2; //both
                MainApp.newQuiz();
                startActivity(i);
            }
        });




    }
}
