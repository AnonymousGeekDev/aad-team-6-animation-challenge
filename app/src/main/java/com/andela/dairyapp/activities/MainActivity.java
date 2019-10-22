package com.andela.dairyapp.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andela.dairyapp.R;

public class MainActivity extends AppCompatActivity {

    private ImageView mAppLogo;
    private TextView mWelcomeText;
    private ProgressBar mLoadingProgressAnim;
    public static final int ANIMATION_DURATION = 1500;
    private ObjectAnimator mLogoFadeInAnimator;
    private ObjectAnimator mLogoDownwardMovementAnimator;
    private ObjectAnimator mTextFadeInAnimator;
    private ObjectAnimator mTextDownwardMovementAnimator;
    private ViewPropertyAnimator mScaleAndBounceAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppLogo = findViewById(R.id.app_logo);
        mWelcomeText = findViewById(R.id.intro_message);
        mLoadingProgressAnim = findViewById(R.id.progressBar);


        mAppLogo.setVisibility(View.GONE);
        mWelcomeText.setVisibility(View.GONE);
        mLoadingProgressAnim.setVisibility(View.VISIBLE);


        launchInitialAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toHomeActivity = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(toHomeActivity);
                finish();
            }
        }, 3500);


    }


    private void launchInitialAnimation() {
        mLogoFadeInAnimator = ObjectAnimator.ofFloat(mAppLogo, "alpha", 0.0f, 1.0f);
        mLogoFadeInAnimator.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator());

        // This animation is used for moving the logo downwards
        mLogoDownwardMovementAnimator = ObjectAnimator.ofFloat(mAppLogo, "translationY", 0f, 150f);
        mLogoDownwardMovementAnimator.setDuration(ANIMATION_DURATION)
                .setInterpolator(new AccelerateDecelerateInterpolator());

        mScaleAndBounceAnimator = mAppLogo.animate();

        //The animation used for 'zooming' the logo in, while adding a bounce effect.
        //Using @ViewPropertyAnimator for conciseness purposes, and for compatibility testing.
        //TODO Write instrumentation tests for the animators
        mScaleAndBounceAnimator.scaleX(1.5f)
                .scaleY(1.5f)
                .setInterpolator(new BounceInterpolator())
                .setDuration(ANIMATION_DURATION);

        mTextFadeInAnimator = ObjectAnimator.ofFloat(mWelcomeText, "alpha", 0.0f, 1.0f);
        mTextFadeInAnimator.setDuration(ANIMATION_DURATION);

        mTextDownwardMovementAnimator = ObjectAnimator.ofFloat(mWelcomeText, "translationY", 0f, 100f);
        mTextDownwardMovementAnimator.setDuration(ANIMATION_DURATION);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(mLogoFadeInAnimator, mLogoDownwardMovementAnimator, mTextFadeInAnimator, mTextDownwardMovementAnimator);
        mScaleAndBounceAnimator.start();
        set.start();

        mAppLogo.setVisibility(View.VISIBLE);
        mWelcomeText.setVisibility(View.VISIBLE);


    }
}
