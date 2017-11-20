package com.willard5991.colorfull;

/**
 * Created by Mitchell on 11/15/2017.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter{

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position){

        switch(position){
            case 0:
                CalendarFragment tab1 = new CalendarFragment();
                return tab1;
            case 1:
                ChartsFragment tab2 = new ChartsFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
        return mNumOfTabs;
    }

}
