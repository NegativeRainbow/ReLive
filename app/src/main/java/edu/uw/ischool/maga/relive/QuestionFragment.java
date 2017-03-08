package edu.uw.ischool.maga.relive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class QuestionFragment extends Fragment implements View.OnClickListener{

    Question currentQuestion;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    Button correct;
    Button intentButton;
    Button nextButton;

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
                /*Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                question.setImageBitmap(bmp);*/

                /*InputStream is = (InputStream) new URL(currentQuestion.dataToShow).getContent();
                Drawable d = Drawable.createFromStream(is,"src name");
                question.setImageDrawable(d);*/

                Picasso.with(getContext())
                        .load(currentQuestion.dataToShow)
                        .resize(500, 500)
                        .into(question);


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
        intentButton = (Button) view.findViewById(R.id.intentButton);
        intentButton.setVisibility(view.GONE);
        intentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri intentUrl =  Uri.parse(currentQuestion.dataURL);
                Intent intent = new Intent(Intent.ACTION_VIEW, intentUrl);
                startActivity(intent);


            }
        });

        nextButton = (Button) view.findViewById(R.id.next);
        nextButton.setVisibility(view.GONE);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.nextQuestion();
            }
        });

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
                intentButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                if(MainApp.current == MainApp.quizLength){
                    nextButton.setText("Finish");
                }
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
        intentButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        MainApp.current++;

        if(MainApp.current == MainApp.quizLength){
            nextButton.setText("Finish");
        }

    }
}
