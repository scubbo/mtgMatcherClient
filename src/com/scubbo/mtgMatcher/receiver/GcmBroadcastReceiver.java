package com.scubbo.mtgMatcher.receiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.scubbo.mtgMatcher.service.GcmIntentService;

/**
 * Created by jackjack on 27/06/2014.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "GCMDemo";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}