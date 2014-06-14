package com.ngame.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.ngame.R;
import com.ngame.factories.LevelFactory;

public class StartUpActivity extends BaseGameActivity implements View.OnClickListener {

	private static final int REQUEST_LEADERBOARD = 666;
	protected static final int REQUEST_ACHIEVEMENTS = 333;
	private ImageView classicMode;
	private ImageView timeBattleMode;
	private ImageView leaderbord;
	private ImageView achievments;
	private ImageView exitGame;
	private ImageView about;
	
	private boolean classicModeDisabled;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start_screen);
		
		initViews();

		super.onCreate(savedInstanceState);
		
		findViewById(R.id.sign_in_bar).setVisibility(View.GONE);
		findViewById(R.id.sign_out_bar).setVisibility(View.GONE);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		classicModeDisabled = prefs.getBoolean(ClassicModeActivity.NO_MORE_LEVELS, false);
		if(classicModeDisabled){
			classicMode.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), LevelFactory.NO_MORE_LEVES_SALUTE, Toast.LENGTH_LONG).show();
				}
			});
		}

	}

	private void initViews() {

		// setup google-sign-in buttons
		findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
		
        // get view references
		classicMode = (ImageView) findViewById(R.id.classicMode);
		timeBattleMode = (ImageView) findViewById(R.id.timeBattleMode);
		leaderbord = (ImageView) findViewById(R.id.leaderbord);
		achievments = (ImageView) findViewById(R.id.achievments);
		exitGame = (ImageView) findViewById(R.id.exitGame);
		about = (ImageView) findViewById(R.id.about);
		
		// setup text-views font
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/Origicide.ttf");
		
		TextView tv1 = (TextView) findViewById(R.id.classicModeTV);
		tv1.setTypeface(tf);
		TextView tv2 = (TextView) findViewById(R.id.timeBattleModeTV);
		tv2.setTypeface(tf);
		
		// get screen size measures
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		// setup look of components based on screen size
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width / 3, height / 5);
		parms.gravity = Gravity.CENTER;
		parms.bottomMargin = 5;
		parms.topMargin = 5;
		
		classicMode.setLayoutParams(parms);
		timeBattleMode.setLayoutParams(parms);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, height/30);
		params.gravity = Gravity.CENTER;
		parms.bottomMargin = 15;
		parms.topMargin = 15;
		tv1.setLayoutParams(params);
		tv2.setLayoutParams(params);
		
		LinearLayout.LayoutParams smallParams = new LinearLayout.LayoutParams(width / 8, height / 10);
		
		parms.leftMargin = width/16;
		parms.rightMargin = width/16;
		//parms.gravity = Gravity.CENTER;
		about.setLayoutParams(smallParams);
		leaderbord.setLayoutParams(smallParams);
		achievments.setLayoutParams(smallParams);
		exitGame.setLayoutParams(smallParams);

		
		// setup on-click-listeners for every view
		classicMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent launchClassicModeGame = new Intent(
						getApplicationContext(), ClassicModeActivity.class);
				startActivity(launchClassicModeGame);
			}
		});

		timeBattleMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent launchTimeBattleModeGame = new Intent(
						getApplicationContext(), TimeBattleModeActivity.class);
				startActivity(launchTimeBattleModeGame);

			}
		});

		about.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent launchAboutSection = new Intent(
						getApplicationContext(), AboutActivity.class);
				startActivity(launchAboutSection);

			}
		});
		
		leaderbord.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSignedIn()) {
				    startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), getResources().getString(R.string.best_winning_run)), REQUEST_LEADERBOARD);
				}
				else {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.leaderboards_not_available), Toast.LENGTH_SHORT).show();
				}
				
			}
		});

		achievments.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSignedIn()) {
				    startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);
				}
				else {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.achievements_not_available), Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		exitGame.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				finish();
			}
		});

		// apply changes before next drawing of the layout
		classicMode.requestLayout();
		timeBattleMode.requestLayout();
		about.requestLayout();
		leaderbord.requestLayout();
		achievments.requestLayout();
		exitGame.requestLayout();
		tv1.requestLayout();
		tv2.requestLayout();
	}

	// Shows the "sign in" bar (explanation and button).
	private void showSignInBar() {
		findViewById(R.id.sign_in_bar).setVisibility(View.VISIBLE);
		findViewById(R.id.sign_out_bar).setVisibility(View.GONE);
	}
	
	// Shows the "sign out" bar (explanation and button).
	private void showSignOutBar() {
		findViewById(R.id.sign_in_bar).setVisibility(View.GONE);
		findViewById(R.id.sign_out_bar).setVisibility(View.VISIBLE);
	}

	@Override
	public void onSignInFailed() {
        // Sign-in has failed. So show the user the sign-in button
        // so they can click the "Sign-in" button.
        showSignInBar();
	}

	@Override
	public void onSignInSucceeded() {
        // Sign-in worked!
        showSignOutBar();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.sign_in_button:
            // start the sign-in flow
            beginUserInitiatedSignIn();
            break;
        case R.id.sign_out_button:
            // sign out.
            signOut();
            showSignInBar();
            break;
		}
		
	}
	
}
