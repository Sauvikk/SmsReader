package com.example.souvik.pfmUi.Activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.souvik.pfmUi.R;
import com.example.souvik.pfmUi.font.MaterialDesignIconsTextView;


public class SplashScreensActivity extends Activity {
	private ImageView splashImage;
	private MaterialDesignIconsTextView mLogo;
	private TextView welcomeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        Log.d("SPLASH", "SCREEN");
		super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash_screen);
		
		splashImage = (ImageView) findViewById(R.id.splash_image);
		mLogo = (MaterialDesignIconsTextView) findViewById(R.id.logo);
		welcomeText = (TextView) findViewById(R.id.welcome_text);
		splashImage.setBackgroundColor(getResources().getColor(R.color.material_green_A700));
		logoAnimation();
		titleAnimation();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.d("IN RUN", "IN RUN");
				Intent i = new Intent(SplashScreensActivity.this, WizardActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finish();
			}
		}, 3500);
   }


	private void logoAnimation() {
        Log.d("ANIMATION", "2");
		mLogo.setAlpha(1.0F);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
		mLogo.startAnimation(anim);
	}
	
	private void titleAnimation() {
        Log.d("ANIMATION", "3");
		final ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(welcomeText, "alpha", 0.0F, 1.0F);
		alphaAnimation.setStartDelay(1700);
		alphaAnimation.setDuration(500);
        alphaAnimation.start();
    }
}
