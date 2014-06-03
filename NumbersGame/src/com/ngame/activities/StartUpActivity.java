package com.ngame.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ngame.R;

public class StartUpActivity extends Activity {

	private ImageView classicMode;
	private ImageView timeBattleMode;
	private ImageView randomMode;
	private ImageView settings;
	private ImageView achievments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.start_screen);

		initViews();

	}

	private void initViews() {

		classicMode = (ImageView) findViewById(R.id.classicMode);
		timeBattleMode = (ImageView) findViewById(R.id.timeBattleMode);
		randomMode = (ImageView) findViewById(R.id.randomMode);
		settings = (ImageView) findViewById(R.id.settings);
		achievments = (ImageView) findViewById(R.id.achievments);

		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width / 3, height / 5);
		parms.gravity = Gravity.CENTER;
		parms.bottomMargin = 10;
		parms.topMargin = 10;

		classicMode.setLayoutParams(parms);
		timeBattleMode.setLayoutParams(parms);
		randomMode.setLayoutParams(parms);

		LinearLayout.LayoutParams smallParams = new LinearLayout.LayoutParams(width / 8, height / 10);
		parms.leftMargin = 6;
		parms.rightMargin = 6;
		parms.gravity = Gravity.CENTER;
		settings.setLayoutParams(smallParams);
		achievments.setLayoutParams(smallParams);

		classicMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent launchClassicModeGame = new Intent(
						getApplicationContext(), MainActivity.class);
				startActivity(launchClassicModeGame);
			}
		});

		timeBattleMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		randomMode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		achievments.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		classicMode.requestLayout();
		timeBattleMode.requestLayout();
		randomMode.requestLayout();
		settings.requestLayout();
		achievments.requestLayout();
	}

}
