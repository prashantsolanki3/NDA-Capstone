package com.prashantsolanki.blackshift.trans.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.prashantsolanki.blackshift.trans.R;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {

    int animX =-1,animY = -1;

    @BindView(R.id.translate)
    FloatingActionButton translate;
    @BindView(R.id.quotes)
    FloatingActionButton quotes;
    @BindView(R.id.starred)
    FloatingActionButton starred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Check if we're running on Android 5.0 or higher
        translate.setOnClickListener(this);
        translate.setOnTouchListener(this);
        quotes.setOnClickListener(this);
        quotes.setOnTouchListener(this);
        starred.setOnClickListener(this);
        starred.setOnTouchListener(this);

        translate.setImageDrawable(new IconDrawable(this, MaterialIcons.md_swap_horiz).colorRes(android.R.color.black));
        quotes.setImageDrawable(new IconDrawable(this,MaterialIcons.md_format_quote).colorRes(android.R.color.black));
        starred.setImageDrawable(new IconDrawable(this,MaterialIcons.md_star).colorRes(android.R.color.black));
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
    public boolean isAuthNeeded() {
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.translate){
            Intent intent =new Intent(getApplicationContext(),TranslationActivity.class);
            intent.putExtra(ARG_ANIM_START_X,animX);
            intent.putExtra(ARG_ANIM_START_Y,animY);
            startActivity(intent);
        }else if (id == R.id.quotes){
            Intent intent =new Intent(getApplicationContext(),QuotesActivity.class);
            intent.putExtra(ARG_ANIM_START_X,animX);
            intent.putExtra(ARG_ANIM_START_Y,animY);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this,
                            Pair.create((View) quotes,"quotes"));
            startActivity(intent,options.toBundle());
        }else if(id == R.id.starred){
            Intent intent =new Intent(getApplicationContext(),StarredActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this,
                            Pair.create((View) starred,"starred"));
            intent.putExtra(ARG_ANIM_START_X,animX);
            intent.putExtra(ARG_ANIM_START_Y,animY);
            startActivity(intent,options.toBundle());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        animX = (int) event.getRawX();
        animY = (int) event.getRawY();
        return false;
    }

}
