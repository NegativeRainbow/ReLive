package  edu.uw.ischool.maga.relive;

import android.app.Application;
import android.util.Log;

public class MainApp extends Application {

    private static MainApp singleton;
    public static int  quizLength; //# of questions
    public static int  quizTime; // how long the quiz is
    public static Question[] quiz; // holds data for the quiz

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