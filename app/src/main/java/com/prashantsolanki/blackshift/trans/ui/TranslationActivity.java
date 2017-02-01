package com.prashantsolanki.blackshift.trans.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.prashantsolanki.blackshift.trans.R;
import com.prashantsolanki.blackshift.trans.TranslationsViewPagerAdapter;

import butterknife.BindView;

public class TranslationActivity extends BaseActivity {

    /*TODO: Create a startActivity static method.*/

    @BindView(R.id.activity_translation)
    View bgReveal;

    @BindView(R.id.viewpager_translations)
    ViewPager viewPager;

    @BindView(R.id.input_text)
    EditText inputEditText;

    TranslationsViewPagerAdapter viewPagerAdapter;



    @Override
    public boolean isAuthNeeded() {
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_translation;
    }

    @Override
    public int getLayoutBaseViewIdRes() {
        return R.id.activity_translation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPagerAdapter = new TranslationsViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        //TODO: Check API
        if(getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)!=null) {
            inputEditText.setText(getIntent()
                    .getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT));
            inputEditText.setSelection(inputEditText.length());
            viewPagerAdapter.setInputText(inputEditText.getText().toString());
            //setResult();
        }

        /*
         * This listens to layout changes and report when bgReveal has been inflated.
         * */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bgReveal.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    animateRevealShow(bgReveal, animStartX, animStartY);
                }
            });
        }

        final int color1 = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
        final int color2 = ContextCompat.getColor(this, android.R.color.holo_green_light);
        final int color3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        final int[] colorList = new int[]{color1, color2, color3};
        final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ArgbEvaluator evaluator = new ArgbEvaluator();
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                bgReveal.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bgReveal.setBackgroundColor(color1);
                        break;
                    case 1:
                        bgReveal.setBackgroundColor(color2);
                        break;
                    case 2:
                        bgReveal.setBackgroundColor(color3);
                        break;
                }
                setResult();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(0);
            }
        });

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewPagerAdapter.setInputText(s.toString());
            }
        });

    }

    void setResult(){

        String result = ((TranslationOutputFragment)viewPagerAdapter.getRegisteredFragment(viewPager.getCurrentItem())).getOutputText().trim();
        if(!result.isEmpty()&&result.length()!=0){
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_PROCESS_TEXT, result);
            setResult(RESULT_OK,intent);
            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        exitReveal(bgReveal, animStartX, animStartY);
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
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                TranslationActivity.super.onBackPressed();
                myView.setVisibility(View.INVISIBLE);
            }
        });

        // start the animation
        anim.start();
    }

}
