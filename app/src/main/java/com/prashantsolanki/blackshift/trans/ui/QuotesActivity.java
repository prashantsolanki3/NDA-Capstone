package com.prashantsolanki.blackshift.trans.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.prashantsolanki.blackshift.trans.R;
import com.prashantsolanki.blackshift.trans.adapters.QuotesViewPagerAdapter;

import butterknife.BindView;

public class QuotesActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.quotes_image)
    ImageView quotesImage;

    @BindView(R.id.activity_quotes)
    View bgReveal;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.viewpager_quotes)
    ViewPager viewPager;

    QuotesViewPagerAdapter viewPagerAdapter;

    @Override
    public boolean isAuthNeeded() {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_quotes;
    }

    @Override
    public int getLayoutBaseViewIdRes() {
        return R.id.activity_quotes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        quotesImage.setImageDrawable(new IconDrawable(this, MaterialIcons.md_format_quote)
                .colorRes(android.R.color.black)
                .sizeDp(64));

        viewPagerAdapter = new QuotesViewPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPager);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            /*
            * This listens to layout changes and report when bgReveal has been inflated.
            * */

/*            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    animateRevealShow(appBarLayout,-1,-1);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        getWindow().getSharedElementEnterTransition().removeListener(this);

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
            });*/

            bgReveal.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    viewPager.setAdapter(viewPagerAdapter);
                    animateRevealShow(bgReveal,animStartX,animStartY);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        exitReveal(bgReveal,animStartX,animStartY);
    }

    void exitReveal(final View myView, int cx, int cy) {
        // previously visible view
        // get the center for the clipping circle
        if(cx ==-1 )
            cx = findViewById(R.id.toolbar_layout).getMeasuredWidth() / 2;

        if(cy ==-1)
            cy = findViewById(R.id.toolbar_layout).getMeasuredHeight() / 2;

        // get the initial radius for the clipping circle
        int initialRadius = myView.getWidth();

        // create the animation (the final radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 56);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                QuotesActivity.super.onBackPressed();
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }

}
