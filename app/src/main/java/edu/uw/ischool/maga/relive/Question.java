package edu.uw.ischool.maga.relive;

/**
 * Created by Kito Pham on 3/5/2017.
 */

public class Question {

    public String type;
    public String displayUrl; //Display post/image
    public String intentUrl; //Intent url to actual post
    public String correctName;
    public String[] nameOptions;

    public Question(String type, String dataToShow, String dataURL, String correct, String[] nameOptions){
        this.type = type;
        this.displayUrl = dataToShow;
        this.intentUrl = dataURL;
        correctName = correct;
        this.nameOptions = nameOptions;
    }
}
