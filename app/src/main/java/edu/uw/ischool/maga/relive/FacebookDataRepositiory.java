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
        //Makes a graph api request to grab a json with a list of friends
        //and the posts, name, id, and albums of that friend
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        FacebookDataRepositiory.readJson(object);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "friends{name,id,picture{url},albums,posts.limit(99999)}");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public static void readJson(JSONObject object){
        JSONObject friendsobject = null;
        try {
            //Grabs all friends and then iterates through each friend creating a Friend object
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

                //Parses through album field to look for Profile Pictures ID
                JSONObject albumsObject = oneFriend.getJSONObject("albums");
                JSONArray albumData = albumsObject.getJSONArray("data");
                int profileId = 0;
                for (int j = 0; j < albumData.length(); j++){
                    JSONObject thisAlbum = albumData.getJSONObject(i);
                    String name = thisAlbum.getString("name");
                    if (name.equals("Profile Pictures")){
                        profileId = thisAlbum.getInt("id");
                        j = albumData.length();
                    }
                }


                //Parses through the pictures field to get the url of the profile picture
                JSONObject pictureobject = oneFriend.getJSONObject("picture");
                JSONObject pictureObjectData=pictureobject.getJSONObject("data");
                String profileUrl = pictureObjectData.getString("url");

                //Creates a friend object and places it in the specific index of the friends array
                Friend thisFriend = new Friend(friendname,friendId);
                thisFriend.currentProfilePictureUrl = profileUrl;
                thisFriend.profilePicAlbumId = profileId;
                friendslist[i] = thisFriend;


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
