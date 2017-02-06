package com.prashantsolanki.blackshift.trans.ui;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prashantsolanki.blackshift.trans.R;

import butterknife.ButterKnife;


/**
 * Created by prsso on 30-01-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public String ARG_ANIM_START_X = "animStartX",ARG_ANIM_START_Y = "animStartY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isAuthNeeded())
            verifyAuth();
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        animStartX = getIntent().getIntExtra(ARG_ANIM_START_X,-1);
        animStartY = getIntent().getIntExtra(ARG_ANIM_START_Y,-1);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }



    public FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    public void logEvent(String s, Bundle bundle){
        mFirebaseAnalytics.logEvent(s, bundle);
    }


    public abstract boolean isAuthNeeded();

    @LayoutRes
    public abstract int getLayoutRes();
    @IdRes
    public abstract int getLayoutBaseViewIdRes();
    /*User login check*/

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        if(isAuthNeeded())
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if(isAuthNeeded())
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    void verifyAuth() {
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    findViewById(getLayoutBaseViewIdRes()).setVisibility(View.VISIBLE);
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.user_not_logged_in),
                            Toast.LENGTH_SHORT).show();
                    findViewById(getLayoutBaseViewIdRes()).setVisibility(View.INVISIBLE);
                    startActivityForResult(new Intent(getApplicationContext(), LogInActivity.class), 439);
                    BaseActivity.this.finish();
                }
            }
        };
        // [END auth_state_listener]
    }
// [END on_stop_remove_listener]

    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    /*Common Animation Stuff*/

    int animStartX =-1, animStartY = -1;

    void animateRevealShow(final View myView, int cx, int cy) {
        // previously invisible view
        // get the center for the clipping circle
        if(cx == -1)
            cx = myView.getMeasuredWidth() / 2;
        if(cy == -1)
            cy = myView.getMeasuredHeight() / 2;

        // get the final radius for the clipping circle
        int finalRadius =Math.max(myView.getWidth(), myView.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        anim.start();

        // make the view visible and start the animation
        myView.setVisibility(View.VISIBLE);
    }

}