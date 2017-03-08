package edu.uw.ischool.maga.relive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;

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

        // Checks type of question and sets the question
        if(currentQuestion.type.equals("Picture")){
            ImageView question = (ImageView) this.getActivity().findViewById(R.id.question_image);
            try {
                URL url = new URL(currentQuestion.dataToShow);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                question.setImageBitmap(bmp);
            } catch (Exception e){
                Log.e("QuestionFragment", "Couldn't load image");
                question.setImageResource(android.R.drawable.star_on);
            }
        } else if(currentQuestion.type.equals("Post")){
            TextView question = (TextView) this.getActivity().findViewById(R.id.question_status);
            question.setText(currentQuestion.dataToShow);
        } else {
            Log.wtf("QuestionFragment", "Type not defined");
        }

        // Sets up countdown timer and listview
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


        // Goes to answer fragment with with the answer marked as correct or not correct
        FriendAdapter nameAdapter = new FriendAdapter(this.getActivity(),
                R.layout.listview_friend, currentQuestion.friendOptions); // listview_friend resource is in layout resources, can edit in there
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
