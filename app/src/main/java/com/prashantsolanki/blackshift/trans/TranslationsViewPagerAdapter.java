package com.prashantsolanki.blackshift.trans;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by prsso on 29-01-2017.
 */

public class TranslationsViewPagerAdapter extends FragmentStatePagerAdapter {

    public TranslationsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private String inputText;

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return TranslationOutputFragment.newInstance("yoda",inputText);
            case 1:
                return TranslationOutputFragment.newInstance("pirate",inputText);
            default:
                return TranslationOutputFragment.newInstance("yoda",inputText);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
}
