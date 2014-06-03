package com.ngame.factories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import android.content.Context;
import android.util.Log;

import com.ngame.models.Level;
import com.ngame.utils.EndOfLevelException;

/**
 * Class used to load levels marked with difficulty 3
 * @author Gorjan
 *
 */
public class Level3Factory extends LevelFactory {

	private static final String TAG = "Level3Factory";

	private ArrayList<Level> levels;
	private Context ctx;

	public Level3Factory(Context c) {
		ctx = c;
		Level newLvl;
		levels = new ArrayList<>();
		try {
			InputStream is = ctx.getResources().getAssets()
					.open("levels/level3.txt");
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			String row = reader.readLine();

			while (row != null) {

				if (row.equalsIgnoreCase("extra"))
					break;
				String[] nums = row.split(":");
				LinkedList<String> steps = new LinkedList<>();
				for (int i = 2; i < nums.length; i++) {
					steps.add(nums[i]);
				}
				newLvl = new Level(nums[0], nums[1], steps);
				levels.add(newLvl);

				row = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "Error in reading levels from level3.txt");
		}
	}

	@Override
	public Level getLevel(int i) throws EndOfLevelException {
		Level returnLevel = null;
		try {
			returnLevel = levels.get(i);
		} catch (IndexOutOfBoundsException e) {
			throw new EndOfLevelException("level with index:" + i + "asked");
		}
		return returnLevel;
	}

}