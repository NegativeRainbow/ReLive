package edu.uw.ischool.maga.relive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URL;

public class QuestionFragment extends Fragment implements View.OnClickListener{

    Question currentQuestion;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    Button correct;

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
        if (MainApp.current < MainApp.quizLength) {
            currentQuestion = MainApp.quiz[MainApp.current]; // Set current to be current question
        }

        final View newView = view;
        // Checks type of question and sets the question
        if(currentQuestion.type.equals("Picture")){
            ImageView question = (ImageView) view.findViewById(R.id.question_image);
            try {
                Log.i("App", "Url for Image" + currentQuestion.dataToShow);
                URL url = new URL(currentQuestion.dataToShow);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                question.setImageBitmap(bmp);
            } catch (Exception e){
                Log.e("QuestionFragment", "Couldn't load image");
                question.setImageResource(android.R.drawable.star_on);
            }
        } else if(currentQuestion.type.equals("Post")){
            TextView question = (TextView) view.findViewById(R.id.question_status);
            question.setText(currentQuestion.dataToShow);
        } else {
            Log.wtf("QuestionFragment", "Type not defined");
        }

        //Button stuff
        answer1 = (Button) view.findViewById(R.id.answer1);
        answer2 = (Button) view.findViewById(R.id.answer2);
        answer3 = (Button) view.findViewById(R.id.answer3);
        answer4 = (Button) view.findViewById(R.id.answer4);


        answer1.setText(currentQuestion.friendOptions[0].name);
        answer1.setOnClickListener(this);
        if(answer1.getText().equals(currentQuestion.correctFriend.name)){
            correct = answer1;
        }
        answer2.setText(currentQuestion.friendOptions[1].name);
        answer2.setOnClickListener(this);
        if(answer2.getText().equals(currentQuestion.correctFriend.name)){
            correct = answer2;
        }

        answer3.setText(currentQuestion.friendOptions[2].name);
        answer3.setOnClickListener(this);
        if(answer3.getText().equals(currentQuestion.correctFriend.name)){
            correct = answer3;
        }

        answer4.setText(currentQuestion.friendOptions[3].name);
        answer4.setOnClickListener(this);
        if(answer4.getText().equals(currentQuestion.correctFriend.name)){
            correct = answer4;
        }
        //End Button Stuff

        // Sets up countdown timer and listview
        /*ListView nameSelect = (ListView) view.findViewById(R.id.select_name);*/
        final TextView timer = (TextView) view.findViewById(R.id.quiz_timer);
        new CountDownTimer(MainApp.quizTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(millisUntilFinished / 1000 + " seconds remaining");
            }

            public void onFinish() {
                answer1.setBackgroundColor(Color.RED);
                answer2.setBackgroundColor(Color.RED);
                answer3.setBackgroundColor(Color.RED);
                answer4.setBackgroundColor(Color.RED);
                correct.setBackgroundColor(Color.GREEN);
                if(currentQuestion.type.equals("Picture")){
                    ImageView question = (ImageView) newView.findViewById(R.id.question_image);
                    question.setImageBitmap(null);

                }
                TextView text = (TextView) newView.findViewById(R.id.question_status);
                text.setText("Times Up!!!");
            }
        }.start();

        // Goes to answer fragment with with the answer marked as correct or not correct
        /*FriendAdapter nameAdapter = new FriendAdapter(this.getActivity(),
                R.layout.listview_friend, currentQuestion.friendOptions); // listview_friend resource is in layout resources, can edit in there
        nameSelect.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainApp.correct = currentQuestion.friendOptions[i].equals(currentQuestion.correctFriend);
                FragmentTransaction tx = getFragmentManager().beginTransaction();
                tx.replace(R.id.fragment, new AnswerFragment());
                tx.addToBackStack(null);
                tx.commit();
            }
        });
        nameSelect.setAdapter(nameAdapter);*/
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.answer1 :
                if (!answer1.getText().equals(currentQuestion.correctFriend.name)){
                    answer1.setBackgroundColor(Color.RED);

                } else {
                    MainApp.correctNum++;
                }
                correct.setBackgroundColor(Color.GREEN);
                break;
            case R.id.answer2 :
                if (!answer2.getText().equals(currentQuestion.correctFriend.name)){
                    answer2.setBackgroundColor(Color.RED);

                } else {
                    MainApp.correctNum++;
                }
                correct.setBackgroundColor(Color.GREEN);
                break;
            case R.id.answer3 :
                if (!answer3.getText().equals(currentQuestion.correctFriend.name)){
                    answer3.setBackgroundColor(Color.RED);

                } else {
                    MainApp.correctNum++;
                }
                correct.setBackgroundColor(Color.GREEN);
                break;
            case R.id.answer4 :
                if (!answer4.getText().equals(currentQuestion.correctFriend.name)){
                    answer4.setBackgroundColor(Color.RED);

                } else {
                    MainApp.correctNum++;
                }
                correct.setBackgroundColor(Color.GREEN);
                break;
        }
        MainApp.current++;
        GameActivity.nextQuestion();
    }
}
