package edu.uw.ischool.maga.relive;

/**
 * Created by Kito Pham on 3/5/2017.
 */

public class Question {

    public String type;
    public String dataToShow;
    public String dataURL;
    public String correctName;
    public String[] nameOptions;
    public String profPicUrl;

    public Question(String type, String dataToShow, String dataURL, String correct, String[] nameOptions){
        this.type = type;
        this.dataToShow = dataToShow;
        this.dataURL = dataURL;
        correctName = correct;
        this.nameOptions = nameOptions;
    }
}
