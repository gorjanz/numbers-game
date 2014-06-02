package com.ngame.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ngame.R;
import com.ngame.utils.OnSwipeTouchListener;

import fr.castorflex.android.flipimageview.library.FlipImageView;

public class MainActivity extends Activity {
	
	private FlipImageView flipView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        flipView = (FlipImageView) findViewById(R.id.flipView1);
        flipView.setDrawable(getResources().getDrawable(R.drawable.one));
        flipView.setFlippedDrawable(getResources().getDrawable(R.drawable.two));

        flipView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeTop() {
                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
                //flipView.setDrawable(getResources().getDrawable(R.drawable.one));
                //flipView.setFlippedDrawable(getResources().getDrawable(R.drawable.two));
                flipView.toggleFlip();
                
            }
            public void onSwipeBottom() {
                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                flipView.toggleFlip();

            }

            
            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

        });
        
	}

	
	
}
