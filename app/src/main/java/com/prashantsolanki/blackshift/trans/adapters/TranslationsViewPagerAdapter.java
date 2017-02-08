package com.prashantsolanki.blackshift.trans.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.prashantsolanki.blackshift.trans.ui.TranslationOutputFragment;

/**
 * Created by prsso on 29-01-2017.
 */

public class TranslationsViewPagerAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    private String inputText;
    private static final String SPEECH_YODA = "yoda";
    private static final String SPEECH_PIRATE = "pirate";

    public TranslationsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TranslationOutputFragment.newInstance(SPEECH_YODA, inputText);
            case 1:
                return TranslationOutputFragment.newInstance(SPEECH_PIRATE, inputText);
            default:
                return TranslationOutputFragment.newInstance(SPEECH_YODA, inputText);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    /*Playing*/

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
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
