package com.prashantsolanki.blackshift.trans;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.prashantsolanki.blackshift.trans.ui.QuotesListFragment;

/**
 * Created by prsso on 29-01-2017.
 */

public class QuotesViewPagerAdapter extends FragmentStatePagerAdapter {

    public QuotesViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

     @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return QuotesListFragment.newInstance("yoda");
            case 1:
                return QuotesListFragment.newInstance("pirate");
            default:
                return QuotesListFragment.newInstance("yoda");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 1: return "pirate";
            case 0:
            default: return "yoda";
        }
    }

    /*Playing*/

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}
