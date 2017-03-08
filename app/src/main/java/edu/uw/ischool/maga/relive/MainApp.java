package  edu.uw.ischool.maga.relive;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;


import com.facebook.AccessToken;

public class MainApp extends Application {

    private static MainApp singleton;

    public static int  quizLength; //# of questions
    public static int  quizTime; // how long the quiz is
    public static Question[] quiz; // holds data for the quiz
    public AccessToken accessToken; //access token for user
    public static int quizType;
    public static FacebookDataRepositiory repo;
    public static int current;
    public static Context context;

    public static MainApp getInstance(){
        return singleton;
    }

    public void setAccessToken(AccessToken token) {
        this.accessToken = token;

    }

    @Override
    public void onCreate(){
        super.onCreate();
        singleton = this;
        Log.i("App", "Application Launched");
        repo = new FacebookDataRepositiory();
    }

    public void newQuiz(){
        current = 0;
        quiz = repo.createQuiz();

    }



    public static Context getContext () {
        return context;
    }

    public static void setContext(Context newContext) {
        context = newContext;
    }
}