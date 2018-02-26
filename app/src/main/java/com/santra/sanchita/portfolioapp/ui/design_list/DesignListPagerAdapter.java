package com.santra.sanchita.portfolioapp.ui.design_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.santra.sanchita.portfolioapp.ui.design_item.DesignItemFragment;
import com.santra.sanchita.portfolioapp.utils.Constants;

/**
 * Created by sanchita on 13/12/17.
 */

public class DesignListPagerAdapter extends FragmentStatePagerAdapter {

    private int NUM_ITEMS = 10;

    private DesignItemFragment currentFragment;

    public DesignListPagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void setCount(int count) {
        NUM_ITEMS = count;
    }

    public DesignItemFragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public Fragment getItem(int position) {

        DesignItemFragment returnableFragment = new DesignItemFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, position);
        returnableFragment.setArguments(args);

        return returnableFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((DesignItemFragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
