package com.prashantsolanki.blackshift.trans.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.prashantsolanki.blackshift.trans.R;
import com.prashantsolanki.blackshift.trans.TranslationsViewPagerAdapter;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class TranslationActivity extends BaseActivity {

    /*TODO: Create a startActivity static method.*/

    @BindView(R.id.activity_translation)
    View bgReveal;

    @BindView(R.id.viewpager_translations)
    ViewPager viewPager;

    @BindView(R.id.input_text)
    EditText inputEditText;

    TranslationsViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.mic_button)
    ImageView micButton;
    private final int REQ_CODE_SPEECH_INPUT = 100;



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
        micButton.setImageDrawable(new IconDrawable(this, MaterialIcons.md_mic).actionBarSize().colorRes(android.R.color.black));


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

        final int color1 = ContextCompat.getColor(this, android.R.color.holo_green_light);
        final int color2 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
        final int[] colorList = new int[]{color1, color2,color1};

        final Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ArgbEvaluator evaluator = new ArgbEvaluator();
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                bgReveal.setBackgroundColor(colorUpdate);
                float[] hsv = new float[3];
                Color.colorToHSV(colorUpdate, hsv);
                hsv[2] *= 0.8f;
                window.setStatusBarColor(Color.HSVToColor(hsv));
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
                    default:
                        bgReveal.setBackgroundColor(color1);
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
//            Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
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

    /**
     * Showing google speech input dialog
     * */
    @OnClick(R.id.mic_button)
    void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    inputEditText.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    protected void onPause() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        super.onPause();
    }
}
