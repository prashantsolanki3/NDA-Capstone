package com.prashantsolanki.blackshift.trans.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.prashantsolanki.blackshift.trans.ui.QuotesListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prsso on 29-01-2017.
 */

public class QuotesViewPagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    List<String> speeches;

    public QuotesViewPagerAdapter(FragmentManager fm) {
        super(fm);
        speeches = new ArrayList<>();

        speeches.add("yoda");
        speeches.add("pirate");
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return QuotesListFragment.newInstance(speeches.get(position));
            case 1:
                return QuotesListFragment.newInstance(speeches.get(position));
            default:
                return QuotesListFragment.newInstance(speeches.get(0));
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    /*Playing*/

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return speeches.get(position);
            case 0:
            default:
                return speeches.get(0);
        }
    }

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
