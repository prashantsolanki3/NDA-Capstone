package com.prashantsolanki.blackshift.trans.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.prashantsolanki.blackshift.trans.R;
import com.prashantsolanki.blackshift.trans.model.Quote;
import com.prashantsolanki.blackshift.trans.viewholder.QuoteVh;

import butterknife.BindView;
import io.github.prashantsolanki3.snaplibrary.snap.adapter.SnapAdapter;

public class StarredActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.starred_image)
    ImageView starredImage;

    @BindView(R.id.activity_starred)
    View bgReveal;

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;


    SnapAdapter<Quote> snapAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public boolean isAuthNeeded() {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_starred;
    }

    @Override
    public int getLayoutBaseViewIdRes() {
        return R.id.activity_starred;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        starredImage.setImageDrawable(new IconDrawable(this, MaterialIcons.md_star)
                .colorRes(android.R.color.black)
                .sizeDp(64));

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        snapAdapter = new SnapAdapter<>(this, Quote.class,R.layout.item_quote,QuoteVh.class,recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(snapAdapter);

        FirebaseDatabase.getInstance()
                .getReference("/starred/"+ FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        snapAdapter.clear();

                        for(DataSnapshot snapshot:dataSnapshot.getChildren())
                            snapAdapter.add(snapshot.getValue(Quote.class));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            /*
            * This listens to layout changes and report when bgReveal has been inflated.
            * */

            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

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
            });

            bgReveal.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        exitReveal(appBarLayout, -1, -1);
    }

    void exitReveal(final View myView, int cx, int cy) {
        // previously visible view
        // get the center for the clipping circle
        if(cx ==-1 )
            cx = myView.getMeasuredWidth() / 2;

        if(cy ==-1)
            cy = myView.getMeasuredHeight() / 2;

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
                StarredActivity.super.onBackPressed();
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }
}
