package edu.uw.ischool.maga.relive;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class QuestionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        final Question currentQuestion = MainApp.quiz[MainApp.current]; // Set current to be current question
        ListView nameSelect = (ListView) view.findViewById(R.id.select_name);
        final TextView timer = (TextView) view.findViewById(R.id.quiz_timer);
        new CountDownTimer(MainApp.quizTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished / 1000 + " seconds remaining");
            }

            public void onFinish() {
                MainApp.correct = false;
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.fragment, new AnswerFragment());
                tx.commit();
            }
        }.start();

        FriendAdapter nameAdapter = new FriendAdapter(this.getActivity(),
                R.layout.listview_friend, currentQuestion.friendOptions);
        nameSelect.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainApp.correct = currentQuestion.friendOptions[i].equals(currentQuestion.correctFriend);
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.fragment, new AnswerFragment());
                tx.commit();
            }
        });
        nameSelect.setAdapter(nameAdapter);
    }
}
