package com.andela.dairyapp.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andela.dairyapp.R;
import com.andela.dairyapp.activities.auth.AuthActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private ImageView mAppLogo;
    private TextView mWelcomeText;
    private ProgressBar mLoadingProgressAnim;
    public static final int ANIMATION_DURATION = 1500;
    private ObjectAnimator mLogoFadeInAnimator;
    private ObjectAnimator mLogoDownwardMovementAnimator;
    private ObjectAnimator mTextFadeInAnimator;
    private ObjectAnimator mTextDownwardMovementAnimator;
    private ViewPropertyAnimator mScaleAndBounceAnimator;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mUser = mFirebaseAuth.getCurrentUser();



        setContentView(R.layout.activity_splash);
        mAppLogo = findViewById(R.id.app_logo);
        mWelcomeText = findViewById(R.id.intro_message);
        mLoadingProgressAnim = findViewById(R.id.progressBar);

        mAppLogo.setVisibility(View.GONE);
        mWelcomeText.setVisibility(View.GONE);
        mLoadingProgressAnim.setVisibility(View.VISIBLE);

        //Check if it is a returning user
        if (mUser != null){
            mWelcomeText.setText(getString(R.string.welcome_back_user));
        }

        launchInitialAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toHomeActivity = new Intent(SplashActivity.this, AuthActivity.class);
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

        mTextDownwardMovementAnimator = ObjectAnimator.ofFloat(mWelcomeText, "translationY", 0f, 200f);
        mTextDownwardMovementAnimator.setDuration(ANIMATION_DURATION);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(mLogoFadeInAnimator, mLogoDownwardMovementAnimator, mTextFadeInAnimator, mTextDownwardMovementAnimator);
        mScaleAndBounceAnimator.start();
        set.start();

        mAppLogo.setVisibility(View.VISIBLE);
        mWelcomeText.setVisibility(View.VISIBLE);


    }
}
