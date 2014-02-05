package com.riaancornelius.flux.ui.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elsabe
 */
public class CustomPagerAdapter extends FragmentPagerAdapter{
    //list of fragments to page
    private List<Fragment> fragments;

    public CustomPagerAdapter(FragmentManager fragmentManager) {
        //TODO add loading fragment if SIZE == 0
        super(fragmentManager);
        fragments = new ArrayList<Fragment>();
    }

    public void addFragment(Fragment f) {
        fragments.add(f);
        super.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
