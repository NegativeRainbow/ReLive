package edu.uw.ischool.maga.relive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;

/**
 * Created by Danish on 3/7/2017.
 */

public class FriendAdapter extends ArrayAdapter<Friend> {

    Context context;
    int layoutResourceId;
    Friend[] data;

    public FriendAdapter(Context context, int layoutResourceID, Friend[] people){
        super(context, layoutResourceID);
        this.context = context;
        data = people;
        layoutResourceId = layoutResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FriendHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new FriendHolder();
            holder.profPic = (ImageView)row.findViewById(R.id.profPic);
            holder.friendName = (TextView)row.findViewById(R.id.friendName);

            row.setTag(holder);
        }
        else
        {
            holder = (FriendHolder) row.getTag();
        }

        Friend weather = data[position];
        holder.friendName.setText(weather.name);
        try {
            URL url = new URL(data[position].currentProfilePictureUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.profPic.setImageBitmap(bmp);
        } catch (Exception e){
            Log.e("FriendAdapter", "Couldn't load image");
            holder.profPic.setImageResource(android.R.drawable.star_on);
        }

        return row;
    }

    static class FriendHolder{
        ImageView profPic;
        TextView friendName;
    }
}
