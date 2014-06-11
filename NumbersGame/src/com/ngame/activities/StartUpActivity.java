package com.ngame.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.ngame.R;
import com.ngame.factories.LevelFactory;

public class StartUpActivity extends Activity {

	private ImageView classicMode;
	private ImageView timeBattleMode;
	private ImageView leaderbord;
	private ImageView achievments;
	private ImageView exitGameButton;
	
	private boolean classicModeDisabled;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start_screen);
		
		initViews();

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

		classicMode = (ImageView) findViewById(R.id.classicMode);
		timeBattleMode = (ImageView) findViewById(R.id.timeBattleMode);
		leaderbord = (ImageView) findViewById(R.id.leaderbord);
		achievments = (ImageView) findViewById(R.id.achievments);
		exitGameButton = (ImageView) findViewById(R.id.exitGame);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/Origicide.ttf");
		
		TextView tv1 = (TextView) findViewById(R.id.classicModeTV);
		tv1.setTypeface(tf);
		TextView tv2 = (TextView) findViewById(R.id.timeBattleModeTV);
		tv2.setTypeface(tf);
		
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

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
		parms.leftMargin = 15;
		parms.rightMargin = 15;
		parms.gravity = Gravity.CENTER;
		leaderbord.setLayoutParams(smallParams);
		achievments.setLayoutParams(smallParams);
		exitGameButton.setLayoutParams(smallParams);
		
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

		leaderbord.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), LEADERBOARD_ID), REQUEST_LEADERBOARD);

			}
		});

		achievments.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		
		exitGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				finish();
			}
		});

		classicMode.requestLayout();
		timeBattleMode.requestLayout();
		leaderbord.requestLayout();
		achievments.requestLayout();
		exitGameButton.requestLayout();
		tv1.requestLayout();
		tv2.requestLayout();
	}

}
