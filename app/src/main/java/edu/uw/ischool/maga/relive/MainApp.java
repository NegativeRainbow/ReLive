package  edu.uw.ischool.maga.relive;

import android.app.Application;
import android.util.Log;

public class MainApp extends Application {

    private static MainApp singleton;
    public int quizLength; //# of questions
    public int quizTime; // how long the quiz is
    public Quiz current; // holds data for the quiz

    public static MainApp getInstance(){
        return singleton;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        singleton = this;
        Log.i("App", "Application Launched");

    }
}