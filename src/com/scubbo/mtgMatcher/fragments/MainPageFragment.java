package com.scubbo.mtgMatcher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.scubbo.mtgMatcher.R;

public class MainPageFragment extends FragmentWithTitle {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mainpagelayout, container, false);
    }

    @Override
    public String getTitle() {
        return "Main Page Title";
    }
}
