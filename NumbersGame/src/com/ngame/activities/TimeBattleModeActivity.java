package com.ngame.activities;

import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ngame.R;
import com.ngame.factories.Level1Factory;
import com.ngame.factories.Level2Factory;
import com.ngame.factories.Level3Factory;
import com.ngame.factories.Level4Factory;
import com.ngame.factories.Level5Factory;
import com.ngame.factories.LevelFactory;
import com.ngame.models.Level;
import com.ngame.utils.EndOfLevelException;
import com.ngame.utils.OnSwipeTouchListener;

import fr.castorflex.android.flipimageview.library.FlipImageView;

public class TimeBattleModeActivity extends Activity {

	public static final String TAG = "TimeBattleModeActivity";
	public static final String LEVELS_SOLVED = "LEVELS_SOLVED";
	public static final String TIME = "1:30";

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

	private TextView targetNumber;
	private TextView timerView;
	private ImageView backButton;
	private ImageView nextLevelButton;

	private int currentLevel;
	private int currentDifficulty;
	private int levelsSolved;
	private int movesUsed;

	private int screenWidth;
	private int screenHeight;

	private Button allUp;
	private Button allDown;
	private LevelFactory levelFactory;
	private Level playingLevel;

	private Drawable[] drawables;
	private CountDownTimer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.time_battle);

		Display display = getWindowManager().getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		initializeFlipViews();

		loadGameState();

		initializeDrawables();
		levelFactory = getFactory(currentDifficulty);
		playingLevel = null;
		nextLevel();
		initializeViews();

		loadUIState();
		
		timer = new CountDownTimer(90000, 1000) { // adjust the milli seconds here

	        public void onTick(long millisUntilFinished) {
	        	
	        	int minutes = (int) millisUntilFinished / 60000; 
	        	int seconds = (int) ((millisUntilFinished / 1000) - ((minutes*60000)/1000)); 
	        	timerView.setText(""+String.format("%d min, %d sec", 
	                        minutes,seconds));
	        }

	        public void onFinish() {
	           Toast.makeText(getApplicationContext(), "Congrats, you solved " + levelsSolved + " correctly!", Toast.LENGTH_LONG).show();
	           try {
				Thread.sleep(1000);
				finish();
			} catch (InterruptedException e) {
				e.printStackTrace();
				finish();
			}
	           
	        }
	     }.start();

		// setFlipViewsDrawables(playingLevel.getGameNum());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		saveGameState();
		saveUIState();

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		loadGameState();
		loadUIState();
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveGameState();
		saveUIState();
	}

	private void initializeFlipViews() {

		Context ctx = getApplicationContext();

		flipView1 = (FlipImageView) findViewById(R.id.flipView11);
		flipView2 = (FlipImageView) findViewById(R.id.flipView21);
		flipView3 = (FlipImageView) findViewById(R.id.flipView31);
		flipView4 = (FlipImageView) findViewById(R.id.flipView41);
		flipView5 = (FlipImageView) findViewById(R.id.flipView51);

		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(
				screenWidth / 5, screenHeight / 3);
		parms.gravity = Gravity.CENTER_VERTICAL;

		flipView1.setLayoutParams(parms);
		flipView2.setLayoutParams(parms);
		flipView3.setLayoutParams(parms);
		flipView4.setLayoutParams(parms);
		flipView5.setLayoutParams(parms);

		OnSwipeTouchListener listener1 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				flip(1, true);
				checkGameOver();
			}

			public void onSwipeBottom() {
				flip(1, false);
				checkGameOver();
			}

		};
		OnSwipeTouchListener listener2 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				flip(2, true);
				checkGameOver();
			}

			public void onSwipeBottom() {
				flip(2, false);
				checkGameOver();
			}

		};
		OnSwipeTouchListener listener3 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				flip(3, true);
				checkGameOver();
			}

			public void onSwipeBottom() {
				flip(3, false);
				checkGameOver();
			}

		};
		OnSwipeTouchListener listener4 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				flip(4, true);
				checkGameOver();
			}

			public void onSwipeBottom() {
				flip(4, false);
				checkGameOver();
			}

		};
		OnSwipeTouchListener listener5 = new OnSwipeTouchListener(ctx) {
			public void onSwipeTop() {
				flip(5, true);
				checkGameOver();
			}

			public void onSwipeBottom() {
				flip(5, false);
				checkGameOver();
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

	private void initializeViews() {

		allUp = (Button) findViewById(R.id.buttonUp1);
		allDown = (Button) findViewById(R.id.buttonDown1);
		targetNumber = (TextView) findViewById(R.id.targetNumberTextView1);
		timerView = (TextView) findViewById(R.id.timer);
		backButton = (ImageView) findViewById(R.id.back1);
		nextLevelButton = (ImageView) findViewById(R.id.nextLevel1);
		Bitmap nextLevelIcon = BitmapFactory.decodeResource(getResources(),
				R.drawable.back_button_icon);
		Matrix matrix = new Matrix();
		matrix.postRotate(180);
		nextLevelIcon = Bitmap.createBitmap(nextLevelIcon, 0, 0,
				nextLevelIcon.getWidth(), nextLevelIcon.getHeight(), matrix,
				true);
		nextLevelButton.setImageBitmap(nextLevelIcon);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				screenWidth / 5, screenHeight / 10);
		params.gravity = Gravity.LEFT;
		backButton.setLayoutParams(params);
		params.gravity = Gravity.RIGHT;
		nextLevelButton.setLayoutParams(params);

		allUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 1; i <= 5; i++) {
					flip(i, true);
				}
				checkGameOver();
			}

		});

		allDown.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 1; i <= 5; i++) {
					flip(i, false);
				}
				checkGameOver();
			}
		});

		backButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				timer.cancel();
				saveGameState();
				saveUIState();
				finish();

			}
		});

		nextLevelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				nextLevel();
				updateViews();
				// saveGameState();
			}
		});

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Origicide.ttf");
		targetNumber.setTypeface(tf);
		targetNumber.setText("Target: " + playingLevel.getTargetNum());
		timerView.setTypeface(tf);
		timerView.setText(TIME);
		
		targetNumber.requestLayout();
		backButton.requestLayout();
		nextLevelButton.requestLayout();
	}

	private void flip(int whichDigit, boolean up) {
		switch (whichDigit) {
		case 1:
			if (up) {
				currentDigit1++;
				if (currentDigit1 > 9)
					currentDigit1 = 0;
			} else {
				currentDigit1--;
				if (currentDigit1 < 0)
					currentDigit1 = 9;
			}
			if (flipView1.isFlipped()) {
				flipView1.setDrawable(drawables[currentDigit1]);
			} else {
				flipView1.setFlippedDrawable(drawables[currentDigit1]);
			}
			flipView1.toggleFlip();
			break;

		case 2:
			if (up) {
				currentDigit2++;
				if (currentDigit2 > 9)
					currentDigit2 = 0;
			} else {
				currentDigit2--;
				if (currentDigit2 < 0)
					currentDigit2 = 9;
			}
			if (flipView2.isFlipped()) {
				flipView2.setDrawable(drawables[currentDigit2]);
			} else {
				flipView2.setFlippedDrawable(drawables[currentDigit2]);
			}
			flipView2.toggleFlip();
			break;

		case 3:
			if (up) {
				currentDigit3++;
				if (currentDigit3 > 9)
					currentDigit3 = 0;
			} else {
				currentDigit3--;
				if (currentDigit3 < 0)
					currentDigit3 = 9;
			}
			if (flipView3.isFlipped()) {
				flipView3.setDrawable(drawables[currentDigit3]);
			} else {
				flipView3.setFlippedDrawable(drawables[currentDigit3]);
			}
			flipView3.toggleFlip();

			break;

		case 4:
			if (up) {
				currentDigit4++;
				if (currentDigit4 > 9)
					currentDigit4 = 0;
			} else {
				currentDigit4--;
				if (currentDigit4 < 0)
					currentDigit4 = 9;
			}
			if (flipView4.isFlipped()) {
				flipView4.setDrawable(drawables[currentDigit4]);
			} else {
				flipView4.setFlippedDrawable(drawables[currentDigit4]);
			}
			flipView4.toggleFlip();

			break;

		default:
			if (up) {
				currentDigit5++;
				if (currentDigit5 > 9)
					currentDigit5 = 0;
			} else {
				currentDigit5--;
				if (currentDigit5 < 0)
					currentDigit5 = 9;
			}
			if (flipView5.isFlipped()) {
				flipView5.setDrawable(drawables[currentDigit5]);
			} else {
				flipView5.setFlippedDrawable(drawables[currentDigit5]);
			}
			flipView5.toggleFlip();

			break;
		}

	}

	private void nextLevel() {
		currentLevel++;
		try {
			playingLevel = levelFactory.getLevel(currentLevel);

		} catch (EndOfLevelException e) {
			currentDifficulty++;
			levelFactory = getFactory(currentDifficulty);
			try {
				playingLevel = levelFactory.getLevel(currentLevel);
			} catch (EndOfLevelException e1) {
				Toast.makeText(getApplicationContext(),
						LevelFactory.WINNER_SALUTE, Toast.LENGTH_LONG).show();
			}

		}

		try {
			Thread.sleep(750);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setFlipViewsDrawables(playingLevel.getGameNum());
		movesUsed = 0;
	}

	private String flipToString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(currentDigit1));
		sb.append(Integer.toString(currentDigit2));
		sb.append(Integer.toString(currentDigit3));
		sb.append(Integer.toString(currentDigit4));
		sb.append(Integer.toString(currentDigit5));
		return sb.toString();
	}

	private void setFlipViewsDrawables(String num) {
		currentDigit1 = Integer.parseInt(num.charAt(0) + "");
		currentDigit2 = Integer.parseInt(num.charAt(1) + "");
		currentDigit3 = Integer.parseInt(num.charAt(2) + "");
		currentDigit4 = Integer.parseInt(num.charAt(3) + "");
		currentDigit5 = Integer.parseInt(num.charAt(4) + "");

		flipView1.setDrawable(drawables[currentDigit1]);
		flipView2.setDrawable(drawables[currentDigit2]);
		flipView3.setDrawable(drawables[currentDigit3]);
		flipView4.setDrawable(drawables[currentDigit4]);
		flipView5.setDrawable(drawables[currentDigit5]);

		flipView1.setFlippedDrawable(drawables[currentDigit1]);
		flipView2.setFlippedDrawable(drawables[currentDigit2]);
		flipView3.setFlippedDrawable(drawables[currentDigit3]);
		flipView4.setFlippedDrawable(drawables[currentDigit4]);
		flipView5.setFlippedDrawable(drawables[currentDigit5]);
	}

	private void updateViews() {
		targetNumber.setText("Target: " + playingLevel.getTargetNum());
	}

	private LevelFactory getFactory(int i) {
		LevelFactory factory = null;
		switch (i) {
		case 1:
			factory = new Level1Factory(getApplicationContext());
			break;

		case 2:
			factory = new Level2Factory(getApplicationContext());
			break;

		case 3:
			factory = new Level3Factory(getApplicationContext());
			break;

		case 4:
			factory = new Level4Factory(getApplicationContext());
			break;

		default:
			factory = new Level5Factory(getApplicationContext());
			break;
		}
		return factory;
	}

	private void checkGameOver() {

		movesUsed++;

		String flipViewsNumber = flipToString();
		if (flipViewsNumber.equals(playingLevel.getTargetNum())) {
			if (movesUsed == playingLevel.getMinMoves()) {
				Toast.makeText(getApplicationContext(),
						LevelFactory.SOLVED_LEVEL_SALUTE, Toast.LENGTH_LONG)
						.show();
				levelsSolved++;
			} else {
				Toast.makeText(getApplicationContext(),
						"Level solved with "
								+ (movesUsed - playingLevel.getMinMoves())
								+ " extra transformations!", Toast.LENGTH_LONG)
						.show();
				// Toast.makeText(getApplicationContext(),
				// playingLevel.getSolution(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private void saveGameState() {

		SharedPreferences.Editor prefsEditor = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext()).edit();
		prefsEditor.putInt(ClassicModeActivity.CURRENT_DIFFICULTY, currentDifficulty);
		prefsEditor.putInt(ClassicModeActivity.CURRENT_LEVEL, currentLevel);
		prefsEditor.putInt(LEVELS_SOLVED, levelsSolved);
		prefsEditor.putInt(ClassicModeActivity.CURRENT_NUMBER_OF_MOVES, movesUsed);
		prefsEditor.commit();

	}

	private void saveUIState() {

		SharedPreferences.Editor prefsEditor = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext()).edit();
		prefsEditor.putString(ClassicModeActivity.CURRENT_FLIP_NUMBER, flipToString());
		prefsEditor.commit();

	}

	private void loadGameState() {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		currentDifficulty = prefs.getInt(ClassicModeActivity.CURRENT_DIFFICULTY, 1);
		currentLevel = prefs.getInt(ClassicModeActivity.CURRENT_LEVEL, -1);
		levelsSolved = prefs.getInt(LEVELS_SOLVED, 0);
		movesUsed = prefs.getInt(ClassicModeActivity.CURRENT_NUMBER_OF_MOVES, 0);

	}

	private void loadUIState() {

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String fNum = prefs.getString(ClassicModeActivity.CURRENT_FLIP_NUMBER,
				playingLevel.getGameNum());

		currentDigit1 = Integer.parseInt(fNum.charAt(0) + "");
		currentDigit2 = Integer.parseInt(fNum.charAt(1) + "");
		currentDigit3 = Integer.parseInt(fNum.charAt(2) + "");
		currentDigit4 = Integer.parseInt(fNum.charAt(3) + "");
		currentDigit5 = Integer.parseInt(fNum.charAt(4) + "");

		flipView1.setDrawable(drawables[currentDigit1]);
		flipView2.setDrawable(drawables[currentDigit2]);
		flipView3.setDrawable(drawables[currentDigit3]);
		flipView4.setDrawable(drawables[currentDigit4]);
		flipView5.setDrawable(drawables[currentDigit5]);

		targetNumber.setText("Target: " + playingLevel.getTargetNum());

	}

}