package com.example.hoang.ss16;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by hoang on 4/30/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new FacebookFragment();
            case 1 : return new GoogleFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
