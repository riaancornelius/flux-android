package com.riaancornelius.flux.ui.components;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.fest.util.Iterables;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Elsabe
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    //list of fragments to page
    private Map<String, Fragment> fragments;

    public CustomPagerAdapter(FragmentManager fragmentManager) {
        //TODO add loading fragment if SIZE == 0
        super(fragmentManager);
        fragments = new LinkedHashMap<String, Fragment>();
    }

    public void addFragment(String key, Fragment fragment) {
        fragments.put(key, fragment);
        super.notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int index) {
        String key = (String) fragments.keySet().toArray()[index];
        return fragments.get(key);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position<0 || position>getCount()-1) {
            return "";
        }
        Fragment item = getItem(position);
        if (item instanceof TitledFragment){
            return ((TitledFragment) item).getTitle();
        }
        throw new IllegalArgumentException("Can only get page title for TitledFragment");
    }
}
