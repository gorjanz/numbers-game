package com.ngame.factories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.ngame.models.Level;
import com.ngame.utils.EndOfLevelException;

public class TimerLevelsFactory {

private static final String TAG = "TimerLevelsFactory";
	
	private ArrayList<Level> levels;
	private Context ctx;
	private int currentLevel;
	private int numLevels;
	
	public TimerLevelsFactory(Context c) {
		ctx = c;
		numLevels = 0;
		Level newLvl;
		currentLevel=-1;
		levels = new ArrayList<>();
		try {
			
			InputStream is = ctx.getResources().getAssets().open("levels/timerLevels.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String row = reader.readLine();
			while(row!=null){
				
				if(row.equalsIgnoreCase("extra"))
					break;
				numLevels++;
				String[] nums = row.split(":");
				LinkedList<String> steps = new LinkedList<>();
				for (int i = 3; i < nums.length; i++) {
					steps.add(nums[i]);
				}
				newLvl = new Level(nums[0], nums[1], steps);
				levels.add(newLvl);
				
				row = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error in reading levels from level1.txt");
		}
	}

	public Level getLevel(){
		Level returnLevel = null;
		try{
			Random r = new Random();
			int nextLevel = r.nextInt(numLevels);
			while(currentLevel==nextLevel){
				nextLevel = r.nextInt(numLevels);
			}
			currentLevel = nextLevel;
			returnLevel = levels.get(nextLevel);
		} catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return returnLevel;
	}


}
