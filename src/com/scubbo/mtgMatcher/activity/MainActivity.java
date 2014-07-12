package com.scubbo.mtgMatcher.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.scubbo.mtgMatcher.R;
import com.scubbo.mtgMatcher.http.Callbackable;
import com.scubbo.mtgMatcher.http.HttpWrapper;
import com.scubbo.mtgMatcher.registration.GCMRegistrationHelper;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {
    // Blame http://developer.android.com/google/gcm/client.html

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    static final String TAG = "GCMDemo";

    TextView mDisplay;
    GoogleCloudMessaging gcm;
    Context context;

    String regid;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mDisplay = (TextView) findViewById(R.id.loggerView);

        context = getApplicationContext();

        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            GCMRegistrationHelper gcmRegistrationHelper = new GCMRegistrationHelper(gcm, context);
            regid = gcmRegistrationHelper.getGCMRegistrationId();
        } else {
            mDisplay.setText("you do not have play services");
        }
    }

    public void submitRegistration(View view) {
        EditText nameEntry = (EditText) findViewById(R.id.nameEntry);
        String name = nameEntry.getText().toString();
        nameEntry.setText("");

        EditText dciEntry = (EditText) findViewById(R.id.dciEntry);
        String dciNum = dciEntry.getText().toString();
        dciEntry.setText("");

        HttpWrapper httpWrapper = new HttpWrapper();
        Map<String, String> options = new HashMap<String, String>();
        options.put("name",name);
        options.put("dciNumber",dciNum);
        options.put("regId",regid);

        Callbackable callbackable = new Callbackable<String,Void>() {
            protected Void operate(String val) {
                callbackRegistration(val);
                return null;
            }
        };

        findViewById(R.id.ProgressBar).setVisibility(View.VISIBLE);
        httpWrapper.sendPost("http://scubbo.org:2020/actions/public/registerPlayer.py",options, callbackable);
    }

    public void callbackRegistration(String backendResponse) {
        findViewById(R.id.ProgressBar).setVisibility(View.INVISIBLE);
        Log.i(TAG,"backendResponse was callbacked as " + backendResponse);
        TextView logView = (TextView) findViewById(R.id.loggerView);
        JSONObject jsonResponse = null;
        try {
            jsonResponse = new JSONObject(backendResponse);
            if (jsonResponse.getString("status").equals("failure")) { //TODO: Boooo, you should classify this
                if (jsonResponse.getString("code").equals("alreadyRegistered")) {
                    logView.setText("Sorry, you're already registered as " + jsonResponse.getJSONObject("data").get("name") + " with DCI Number " + jsonResponse.getJSONObject("data").get("dciNumber"));
                } else {
                    logView.setText("An unknown error occured. Response dump: " + jsonResponse.toString());
                }
            } else if (jsonResponse.getString("status").equals("success")) {
                logView.setText("Success! You are now registered");
            }
        } catch (JSONException e) {
            Log.i(TAG,"Something went wrong in parsing the backendResponse");
            logView.setText(e.getMessage());
            e.printStackTrace();
        }


    }

    // You need to do the Play Services APK check here too.
    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                String TAG="THIS IS A TAG";
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}
