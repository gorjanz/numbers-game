package com.ngame.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ngame.R;
import com.ngame.utils.OnSwipeTouchListener;

import fr.castorflex.android.flipimageview.library.FlipImageView;

public class MainActivity extends Activity {

	private Integer currentDigit1;
	private Integer currentDigit2;
	private Integer currentDigit3;
	private Integer currentDigit4;
	private Integer currentDigit5;

	private FlipImageView flipView1;
	private FlipImageView flipView2;
	private FlipImageView flipView3;
	private FlipImageView flipView4;
	private FlipImageView flipView5;

	private Button allUp;
	private Button allDown;
	
	private Drawable[] drawables;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		initializeDrawables();
		initializeFlipViews("12345");
		initializeButtons();
	}

	private void initializeFlipViews(String num) {

		currentDigit1 = Integer.parseInt(num.charAt(0) + "");
		currentDigit2 = Integer.parseInt(num.charAt(1) + "");
		currentDigit3 = Integer.parseInt(num.charAt(2) + "");
		currentDigit4 = Integer.parseInt(num.charAt(3) + "");
		currentDigit5 = Integer.parseInt(num.charAt(4) + "");

		Context ctx = getApplicationContext();
		
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		
		flipView1 = (FlipImageView) findViewById(R.id.flipView1);
		flipView1.setDrawable(drawables[currentDigit1]);
		flipView2 = (FlipImageView) findViewById(R.id.flipView2);
		flipView2.setDrawable(drawables[currentDigit2]);
		flipView3 = (FlipImageView) findViewById(R.id.flipView3);
		flipView3.setDrawable(drawables[currentDigit3]);
		flipView4 = (FlipImageView) findViewById(R.id.flipView4);
		flipView4.setDrawable(drawables[currentDigit4]);
		flipView5 = (FlipImageView) findViewById(R.id.flipView5);
		flipView5.setDrawable(drawables[currentDigit5]);
		
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width/5,height/3);
		parms.gravity = Gravity.CENTER_VERTICAL;
		 
		flipView1.setLayoutParams(parms);
		flipView2.setLayoutParams(parms);
		flipView3.setLayoutParams(parms);
		flipView4.setLayoutParams(parms);
		flipView5.setLayoutParams(parms);

		OnSwipeTouchListener listener1 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
				flip(1, true);
			}

			public void onSwipeBottom() {
				Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
				flip(1, false);
			}

		};
		OnSwipeTouchListener listener2 = new OnSwipeTouchListener(ctx){
			public void onSwipeTop() {
				Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
				flip(2, true);
			}

			public void onSwipeBottom() {
				Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
				flip(2, false);
			}

		};
		OnSwipeTouchListener listener3 = new OnSwipeTouchListener(ctx){
			public void onSwipeTop() {
				Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
				flip(3, true);
			}

			public void onSwipeBottom() {
				Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
				flip(3, false);
			}

		};
		OnSwipeTouchListener listener4 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
				flip(4, true);
			}

			public void onSwipeBottom() {
				Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
				flip(4, false);
			}

		};
		OnSwipeTouchListener listener5 = new OnSwipeTouchListener(ctx){
			public void onSwipeTop() {
				Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
				flip(5, true);
			}

			public void onSwipeBottom() {
				Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
				flip(5, false);
			}

		};

		flipView1.setOnTouchListener(listener1);
		flipView2.setOnTouchListener(listener2);
		flipView3.setOnTouchListener(listener3);
		flipView4.setOnTouchListener(listener4);
		flipView5.setOnTouchListener(listener5);
		
		flipView1.requestLayout();
		flipView2.requestLayout();
		flipView3.requestLayout();
		flipView4.requestLayout();
		flipView5.requestLayout();

	}

	private void initializeDrawables() {

		drawables = new Drawable[10];
		drawables[0] = getResources().getDrawable(R.drawable.zero);
		drawables[1] = getResources().getDrawable(R.drawable.one);
		drawables[2] = getResources().getDrawable(R.drawable.two);
		drawables[3] = getResources().getDrawable(R.drawable.three);
		drawables[4] = getResources().getDrawable(R.drawable.four);
		drawables[5] = getResources().getDrawable(R.drawable.five);
		drawables[6] = getResources().getDrawable(R.drawable.six);
		drawables[7] = getResources().getDrawable(R.drawable.seven);
		drawables[8] = getResources().getDrawable(R.drawable.eight);
		drawables[9] = getResources().getDrawable(R.drawable.nine);

	}
	
	private void initializeButtons(){
		
		allUp = (Button) findViewById(R.id.buttonUp);
		allDown = (Button) findViewById(R.id.buttonDown);
		
		allUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (int i = 1; i <= 5; i++) {
					flip(i, true);
				}
			}
			
		});
		
		allDown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (int i = 1; i <= 5; i++) {
					flip(i, false);
				}

			}
		});

		
	}
	
	private void flip(int whichDigit, boolean up){
		switch (whichDigit) {
		case 1:
			if(up){
				currentDigit1++;
				if(currentDigit1>9)
					currentDigit1=0;
			}else{
				currentDigit1--;
				if(currentDigit1<0)
					currentDigit1=9;
			}
			if(flipView1.isFlipped()){
				flipView1.setDrawable(drawables[currentDigit1]);
			}else{
				flipView1.setFlippedDrawable(drawables[currentDigit1]);
			}
			flipView1.toggleFlip();
			break;
			
		case 2:
			if(up){
				currentDigit2++;
				if(currentDigit2>9)
					currentDigit2=0;
			}else{
				currentDigit2--;
				if(currentDigit2<0)
					currentDigit2=9;
			}
			if(flipView2.isFlipped()){
				flipView2.setDrawable(drawables[currentDigit2]);
			}else{
				flipView2.setFlippedDrawable(drawables[currentDigit2]);
			}
			flipView2.toggleFlip();
			break;

		case 3:
			if(up){
				currentDigit3++;
				if(currentDigit3>9)
					currentDigit3=0;
			}else{
				currentDigit3--;
				if(currentDigit3<0)
					currentDigit3=9;
			}
			if(flipView3.isFlipped()){
				flipView3.setDrawable(drawables[currentDigit3]);
			}else{
				flipView3.setFlippedDrawable(drawables[currentDigit3]);
			}
			flipView3.toggleFlip();

			break;

		case 4:
			if(up){
				currentDigit4++;
				if(currentDigit4>9)
					currentDigit4=0;
			}else{
				currentDigit4--;
				if(currentDigit4<0)
					currentDigit4=9;
			}
			if(flipView4.isFlipped()){
				flipView4.setDrawable(drawables[currentDigit4]);
			}else{
				flipView4.setFlippedDrawable(drawables[currentDigit4]);
			}
			flipView4.toggleFlip();

			break;


		default:
			if(up){
				currentDigit5++;
				if(currentDigit5>9)
					currentDigit5=0;
			}else{
				currentDigit5--;
				if(currentDigit5<0)
					currentDigit5=9;
			}
			if(flipView5.isFlipped()){
				flipView5.setDrawable(drawables[currentDigit5]);
			}else{
				flipView5.setFlippedDrawable(drawables[currentDigit5]);
			}
			flipView5.toggleFlip();
			
			break;
		}
		
		//check if game-over
	}

}
