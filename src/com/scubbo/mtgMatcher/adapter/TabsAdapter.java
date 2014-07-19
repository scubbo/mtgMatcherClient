package com.scubbo.mtgMatcher.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import com.scubbo.mtgMatcher.fragments.FragmentWithTitle;

import java.util.ArrayList;
import java.util.List;

public class TabsAdapter extends FragmentPagerAdapter {

    private int NUM_ITEMS = 0;
    private List<FragmentWithTitle> fragments = new ArrayList<FragmentWithTitle>();

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    public void addFragment(FragmentWithTitle fragment) {
        fragments.add(fragment);
        NUM_ITEMS += 1;
    }

    @Override
    public String getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }

}