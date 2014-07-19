package com.scubbo.mtgMatcher.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.scubbo.mtgMatcher.Constants;
import com.scubbo.mtgMatcher.R;
import com.scubbo.mtgMatcher.adapter.TabsAdapter;
import com.scubbo.mtgMatcher.fragments.MainPageFragment;
import com.scubbo.mtgMatcher.fragments.PlayerDetailsFragment;

public class SwiperActivity extends FragmentActivity {
    private ViewPager mViewPager;

    private Context context;

    SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        context = getApplicationContext();

        prefs = context.getSharedPreferences(SwiperActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);

        mViewPager = (ViewPager) findViewById(R.id.mainViewPager);

        TabsAdapter tabsAdapter = new TabsAdapter(getFragmentManager());
        tabsAdapter.addFragment(new PlayerDetailsFragment());
        tabsAdapter.addFragment(new MainPageFragment());
        mViewPager.setAdapter(tabsAdapter);


        if (savedInstanceState != null) {
//            bar.setSelectedNavigationItem(savedInstanceState.getInt("MainPage",0));
        }

    }

    public void updatePlayerDetails(View v) {
        EditText nameEntry = (EditText) findViewById(R.id.nameEntry);
        EditText dciEntry = (EditText) findViewById(R.id.dciEntry);

        String playerName = nameEntry.getText().toString();
        String dciNumber = dciEntry.getText().toString();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.PREFS_PROPERTY_PLAYER_NAME, playerName);
        editor.putString(Constants.PREFS_PROPERTY_DCI_NUMBER, dciNumber);
        editor.commit();

        Toast.makeText(context, "Details saved", Toast.LENGTH_SHORT).show();
    }
}
