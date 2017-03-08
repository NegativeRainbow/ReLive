package edu.uw.ischool.maga.relive;

/**
 * Created by Kito Pham on 3/5/2017.
 */

public class Question {

    public String type;
    public String dataToShow;
    public Friend correctFriend;
    public Friend[] friendOptions;

    public Question(String type, String dataToShow, Friend correct, Friend[] friendOptions){
        this.type = type;
        this.dataToShow = dataToShow;
        correctFriend = correct;
        this.friendOptions = friendOptions;
    }
}
