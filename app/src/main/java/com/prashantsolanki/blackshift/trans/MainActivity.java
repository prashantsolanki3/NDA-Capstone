package com.prashantsolanki.blackshift.trans;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {

    int animX =-1,animY = -1;

    @BindView(R.id.translate)
    FloatingActionButton translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Check if we're running on Android 5.0 or higher
        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.translate).setOnTouchListener(this);

        translate.setBackgroundColor(android.R.color.holo_blue_bright);
        translate.setImageDrawable(new IconDrawable(this, MaterialIcons.md_swap_horiz).colorRes(android.R.color.black).sizeDp(96));

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Call some material design APIs here
            Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
            sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else {
            // Implement this feature without material design
        }*/

    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public int getLayoutBaseViewIdRes() {
        return R.id.activity_main;
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.translate){
            Intent intent =new Intent(getApplicationContext(),TranslationActivity.class);
            intent.putExtra("x",animX);
            intent.putExtra("y",animY);
            startActivity(intent);
        }


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        animX = (int) event.getRawX();
        animY = (int) event.getRawY();
        return false;
    }

}
