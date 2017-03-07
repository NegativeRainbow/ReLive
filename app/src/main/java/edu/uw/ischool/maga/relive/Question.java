package edu.uw.ischool.maga.relive;

/**
 * Created by Kito Pham on 3/5/2017.
 */

public class Question {

    public String type;
    public String displayUrl; //Display post/image. Is flat string for the status post if type is post
    public String intentUrl; //Intent url to actual post
    public String correctName;
    public String[] nameOptions;


    /*
     FOR UI GUYS
     to call image ui go to stackoverflow.com/questions/5841710/get-user-image-from-facebook-graph-api
     do img_value = new URL(blah lahblah) with blahblahablhah being the Question[i].displayUrl
     */
    public Question(String type, String dataToShow, String dataURL, String correct, String[] nameOptions){
        this.type = type;
        this.displayUrl = dataToShow;
        this.intentUrl = dataURL;
        correctName = correct;
        this.nameOptions = nameOptions;
    }
}
