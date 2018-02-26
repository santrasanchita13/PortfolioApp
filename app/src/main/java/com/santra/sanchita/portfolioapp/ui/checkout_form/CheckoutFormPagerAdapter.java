package com.santra.sanchita.portfolioapp.ui.checkout_form;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form1.CheckoutForm1Fragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form2.CheckoutForm2Fragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form3.CheckoutForm3Fragment;
import com.santra.sanchita.portfolioapp.ui.checkout_form.checkout_form4.CheckoutForm4Fragment;
import com.santra.sanchita.portfolioapp.utils.Constants;

/**
 * Created by sanchita on 19/12/17.
 */

public class CheckoutFormPagerAdapter extends FragmentStatePagerAdapter {

    private int NUM_ITEMS = 4;

    private Fragment currentFragment;

    public CheckoutFormPagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment returnableFragment;

        switch (position) {
            case 0:
                returnableFragment = new CheckoutForm1Fragment();
                break;
            case 1:
                returnableFragment = new CheckoutForm2Fragment();
                break;
            case 2:
                returnableFragment = new CheckoutForm3Fragment();
                break;
            default:
                returnableFragment = new CheckoutForm4Fragment();
                break;
        }

        Bundle args = new Bundle();
        args.putInt(Constants.POSITION, position);
        returnableFragment.setArguments(args);

        return returnableFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
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
