package com.ngame.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ngame.R;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.about);
		
		initializeViews();
		
	}
	
	private void initializeViews(){
		
		ImageView logo = (ImageView) findViewById(R.id.logoImage);
		ImageView backButton = (ImageView) findViewById(R.id.exitAboutScreen);
		
		// get screen size measures
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width/3, height/4);
		params.gravity = Gravity.CENTER_HORIZONTAL;
		params.bottomMargin = 10;
		logo.setLayoutParams(params);
		
		params = new LinearLayout.LayoutParams(width/8, height/10);
		params.gravity = Gravity.RIGHT;
		params.rightMargin = 5;
		params.topMargin = 5;
		backButton.setLayoutParams(params);
		
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/Origicide.ttf");

		((TextView)findViewById(R.id.howToPlay)).setTypeface(tf);
		((TextView)findViewById(R.id.creators)).setTypeface(tf);
		((TextView)findViewById(R.id.aboutTheGame)).setTypeface(tf);
		
		((TextView)findViewById(R.id.aboutTheGameExplained)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "https://github.com/gorjanz/numbers-game";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);				
			}
		});
		
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		backButton.requestLayout();
		logo.requestLayout();
		
		
	}

}
