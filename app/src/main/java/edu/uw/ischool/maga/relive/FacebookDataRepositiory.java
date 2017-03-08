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
                AccessToken.getCurrentAccessToken(), //CHANGETHIS
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        FacebookDataRepositiory.readJson(object);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "friends{name,id,picture{url},albums{name,photos{picture}},posts.limit(99999)}");
        //URL TO CHECK JSON, plug below the graph api explorer
        // /me/friends?fields=name,id,picture{url},albums{name,photos{id,picture}},posts.limit(9999)
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

                //Friendslist is initialized at 10, if the number of friends from the JSON surpasses 10
                //then the list will double the size and copy over all existing entries.
                if (i == friendslist.length){
                    Friend[] temp = Arrays.copyOf(friendslist, friendslist.length*2);
                    friendslist = temp;
                }

                //Sets up object to just grab one friend
                JSONObject oneFriend = jarray.getJSONObject(i);
                String friendname = oneFriend.getString("name");
                int friendId = oneFriend.getInt("id");

                //Parses through album field to look for Profile Pictures
                JSONObject albumsObject = oneFriend.getJSONObject("albums");
                JSONArray albumData = albumsObject.getJSONArray("data");
                JSONArray profileAlbum = null;
                //int profileId = 0; deprecated
                //Looks at each album in the Albums field. thisAlbum represents the specific album
                //being analyzed.
                for (int j = 0; j < albumData.length(); j++){

                    JSONObject thisAlbum = albumData.getJSONObject(i);
                    String name = thisAlbum.getString("name");

                    if (name.equals("Profile Pictures")){
                        //profileId = thisAlbum.getInt("id"); deprecrated
                        JSONObject profileAlbumObj = thisAlbum.getJSONObject("photos");
                        profileAlbum = profileAlbumObj.getJSONArray("data");
                        j = albumData.length();
                    }

                }

                //Parses through data to get to posts object and stores it
                JSONObject postsData = oneFriend.getJSONObject("posts");
                JSONArray posts = postsData.getJSONArray("data");

                //Parses through the pictures field to get the url of the profile picture
                JSONObject pictureobject = oneFriend.getJSONObject("picture");
                JSONObject pictureObjectData=pictureobject.getJSONObject("data");
                String profileUrl = pictureObjectData.getString("url");

                //Creates a friend object and places it in the specific index of the friends array
                //Updates and stores data in each friend's object
                Friend thisFriend = new Friend(friendname,friendId);
                thisFriend.currentProfilePictureUrl = profileUrl;
                //thisFriend.profilePicAlbumId = profileId; deprecated
                thisFriend.posts = posts;
                thisFriend.profilePictures = profileAlbum;
                friendslist[i] = thisFriend;


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    // Creates a new quiz everytime its called using the friendlists
    public Question[] createQuiz(){
        Random r = new Random();
        Question[] quiz = new Question[MainApp.quizLength];

        //For every question, a random friend is selected
        for (int i = 0; i < MainApp.quizLength;i++) {

            int friendselect = r.nextInt(friendslist.length);
            Friend randomFriend = friendslist[friendselect];

            //That friend's id, name, and profile picture are stored
            int friendId = randomFriend.id;
            int typeNum = MainApp.quizType;
            //Random picks between a status post or a profile picture
            if(MainApp.quizType==2) {
                typeNum = r.nextInt(2);
            }

            //initializes variables depending on post or profile picture
            String type = "";
            String displayUrl="";
            String intentUrl="";
            int select = r.nextInt(9999);

            //if type is profile picture question;
            if (typeNum == 1){
                type = "Picture";
                try {
                    //Parse through profile pictures to get a random picture. Sets display, intent, and id based off
                    // the picture
                    JSONArray profilePics = randomFriend.profilePictures;
                    JSONObject randomPicObject = profilePics.getJSONObject(select%profilePics.length());
                    int pictureId = randomPicObject.getInt("id");
                    displayUrl = randomPicObject.getString("picture");
                    intentUrl = "https://www.facebook.com/photo.php?fbid=" + pictureId;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else { //if type is a status
                type = "Post";
                try{
                    //Parses through posts of that random friend to get a random post
                    JSONArray posts = randomFriend.posts;
                    JSONObject randomPostObject = posts.getJSONObject(select%posts.length());
                    //Gets a new post until the random post has a message field
                    while(!randomPostObject.has("message")){
                        select = r.nextInt(9999);
                        randomPostObject = posts.getJSONObject(select%posts.length());
                    }
                    //Sets display, intent, and message based off post
                    displayUrl = randomPostObject.getString("message");
                    int postId = randomPostObject.getInt("id");
                    intentUrl = "https://facebook.com/" + friendId + "/posts/" + postId;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //Loops through friendslist to grab random names and fills up the answers
            Friend[] randomAnswers = new Friend[4];
            for (int j = 0; j < randomAnswers.length; j++) {
                int numSelect = r.nextInt(friendslist.length);
                randomAnswers[j] = friendslist[numSelect];
            }

            //overwrites a random one to display the correct answer.
            int answerSelect = r.nextInt(randomAnswers.length);
            randomAnswers[answerSelect] = randomFriend;

            //creates a question object at this index of the quiz
            quiz[i] = new Question(
                    type,
                    intentUrl,
                    randomFriend,
                    randomAnswers
            );
        //End of for loop
        }

        return quiz;
        //End of method
    }
}
