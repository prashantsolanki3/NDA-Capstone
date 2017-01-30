package com.prashantsolanki.blackshift.trans;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    int animX =-1,animY = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
