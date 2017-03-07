package edu.uw.ischool.maga.relive;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Kito Pham on 3/5/2017.
 */

public class Friend {
    public String name;
    public int id;
    public String currentProfilePictureUrl;
    public JSONArray posts;
    public JSONArray profilePictures;
    //public int profilePicAlbumId;
    public Friend(String name, int id){
        this.name = name;
        this.id = id;

    }
}
