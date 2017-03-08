package edu.uw.ischool.maga.relive;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements FacebookLoginFragment.OnFragmentInteractionListener {

    private Fragment fragment; //Facebook Log In Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainApp.setContext(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //setup wifi check
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //checks if there is any internet access; either from wifi or mobile data
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        //No internet access
        if(!isConnected) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            //Airplane mode is on
            if(isAirplaneModeOn(this)) {
                //Ask user if they want to go to airplane mode settings
                dialog.setMessage("Would you like to go to settings to turn off airplane mode?");
                //Positive (yes, they want to go to airplane mode settings)
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    //Onclick listener for positive button
                    public void onClick(DialogInterface dialog, int which) {
                        //Goes to airplane mode settings
                        Intent dialogIntent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(dialogIntent);
                        //pd.dismiss();
                    }
                });
                //Negative (No, user does not want to go to airplane mode)
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainApp.getContext(), NoInternetActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                //Ask user if they want to go to wifi settings?
                dialog.setMessage("Would you like to go to settings to turn on wifi?");
                //Positive (Yes, they want to go to wifi settings)
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    //On click listener for positive
                    public void onClick(DialogInterface dialog, int which) {
                        //GOes to Wifi Settings
                        Intent dialogIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(dialogIntent);
                        //pd.dismiss();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainApp.getContext(), NoInternetActivity.class);
                        startActivity(intent);
                    }
                });
            }
            //creates and shows the dialog
            AlertDialog alert = dialog.create();
            alert.show();
        } else {
            fragment = FacebookLoginFragment.newInstance();
            android.support.v4.app.FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.fragment, fragment);
            tx.commit();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preferences:
                Intent i = new Intent(MainActivity.this, AppPreferences.class);
                startActivity(i);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }
}
