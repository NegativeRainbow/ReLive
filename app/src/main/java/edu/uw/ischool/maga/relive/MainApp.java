package  edu.uw.ischool.maga.relive;

import android.app.Application;
import android.util.Log;

import com.facebook.AccessToken;

public class MainApp extends Application {

    private static MainApp singleton;

    public static int  quizLength; //# of questions
    public static int  quizTime; // how long the quiz is
    public static Question[] quiz; // holds data for the quiz
    public static AccessToken accessToken; //access token for user
    public static int quizType;
    public static FacebookDataRepositiory repo;
    public static int current;
    public static boolean correct;

    public static MainApp getInstance(){
        return singleton;
    }

    public static void setAccessToken(AccessToken token) {
        accessToken = token;

    }

    @Override
    public void onCreate(){
        quizLength = 10;
        super.onCreate();
        singleton = this;
        Log.i("App", "Application Launched");


    }

    public static void newQuiz(){
        current = 0;
        quiz = repo.createQuiz();

    }

    public static void makeRepo(){
        repo = new FacebookDataRepositiory();
    }
}