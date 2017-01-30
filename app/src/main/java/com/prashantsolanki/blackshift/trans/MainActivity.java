package com.prashantsolanki.blackshift.trans;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    int animX =-1,animY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyAuth();
        setContentView(R.layout.activity_main);

        // Check if we're running on Android 5.0 or higher
        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.translate).setOnTouchListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Call some material design APIs here
            Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
            sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    Toast.makeText(getApplicationContext(),"END",Toast.LENGTH_SHORT).show();
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
        }

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
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    void verifyAuth(){
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
                    findViewById(R.id.activity_main).setVisibility(View.VISIBLE);
                } else {
                    // User is signed out
                    Toast.makeText(getApplicationContext(),
                            "You are not logged in. Please log in.",
                            Toast.LENGTH_SHORT).show();
                    findViewById(R.id.activity_main).setVisibility(View.INVISIBLE);
                    startActivityForResult(new Intent(getApplicationContext(), LogInActivity.class),439);
                }
            }
        };
        // [END auth_state_listener]
    }
    // [END on_stop_remove_listener]
}
