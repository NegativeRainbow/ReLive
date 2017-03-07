package  edu.uw.ischool.maga.relive;

import android.app.Application;
import android.util.Log;

import com.facebook.AccessToken;

public class MainApp extends Application {

    private static MainApp singleton;
    public int quizLength; //# of questions
    public int quizTime; // how long the quiz is
    public Quiz current; // holds data for the quiz
    public AccessToken accessToken; //access token for user

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

    }
}