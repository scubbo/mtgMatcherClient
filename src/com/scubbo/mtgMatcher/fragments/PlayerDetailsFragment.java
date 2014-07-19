package com.scubbo.mtgMatcher.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.scubbo.mtgMatcher.Constants;
import com.scubbo.mtgMatcher.R;
import com.scubbo.mtgMatcher.activity.SwiperActivity;

public class PlayerDetailsFragment extends FragmentWithTitle {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View inflatedView = inflater.inflate(R.layout.playerdetailslayout, container, false);
        populatePlayerDetails(inflatedView);
        return inflatedView;

    }

    private void populatePlayerDetails(View inflatedView) {
        final SharedPreferences prefs = context.getSharedPreferences(SwiperActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
        String playerName = prefs.getString(Constants.PREFS_PROPERTY_PLAYER_NAME, "");
        Log.i("someTag", "playerName is " + playerName);
        if (!(playerName.equals(""))) {
            ((EditText) inflatedView.findViewById(R.id.nameEntry)).setText(playerName);
        }

        String playerDciNumber = prefs.getString(Constants.PREFS_PROPERTY_DCI_NUMBER,"");
        if (!(playerDciNumber.equals(""))) {
            ((EditText) inflatedView.findViewById(R.id.dciEntry)).setText(playerDciNumber);
        }
    }

    @Override
    public String getTitle() {
        return "Player Details";
    }
}
