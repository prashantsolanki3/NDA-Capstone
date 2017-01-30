package com.prashantsolanki.blackshift.trans;


import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

/**
 * Created by prsso on 30-01-2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());
    }

}