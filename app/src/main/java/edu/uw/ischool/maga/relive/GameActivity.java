package edu.uw.ischool.maga.relive;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {
    private static FragmentManager fragManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fragManager = getSupportFragmentManager();
        fragManager.beginTransaction()
                .replace(R.id.fragment,new QuestionFragment())
                .commit();
    }

    public static void nextQuestion(){
        if (MainApp.current < MainApp.quizLength) {
            fragManager.beginTransaction()
                    .replace(R.id.fragment, new QuestionFragment())
                    .commit();
        }
    }
}
