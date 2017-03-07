package edu.uw.ischool.maga.relive;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Kito Pham on 3/5/2017.
 */

public class FacebookDataRepositiory {

    public static Friend[] friendslist;

    public FacebookDataRepositiory(){
        /* make the API call */
        friendslist = new Friend[10];
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        FacebookDataRepositiory.readJson(object);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,friends");
        request.setParameters(parameters);
        request.executeAsync();

    }

    public static void readJson(JSONObject object){
        JSONObject friendsobject = null;
        try {
            friendsobject = object.getJSONObject("friends");
            JSONArray jarray = friendsobject.getJSONArray("data");
            for(int i = 0; i < jarray.length();i++) {
                if (i == friendslist.length){
                    Friend[] temp = Arrays.copyOf(friendslist, friendslist.length*2);
                    friendslist = temp;
                }
                JSONObject oneFriend = jarray.getJSONObject(i);
                String friendname = oneFriend.getString("name");
                int friendId = oneFriend.getInt("id");
                friendslist[i] = new Friend(friendname,friendId);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Question createQuiz(){
        Random r = new Random();
        Question[] quiz = new Question[MainApp.quizLength];

        for (int i = 0; i < MainApp.quizLength;i++) {

            int friendselect = r.nextInt(friendslist.length);
            int friendId = friendslist[friendselect].id;
            String friendName = friendslist[friendselect].name;
        }

    }
}
