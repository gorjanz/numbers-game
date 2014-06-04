package com.ngame.factories;

import com.ngame.models.Level;
import com.ngame.utils.EndOfLevelException;


public abstract class LevelFactory {
	
	public static final String WINNER_SALUTE = "You have reached the end of the game... You sir are a great man";
	public static final String SOLVED_LEVEL_SALUTE = "WIN!!! with use of minimum number of transformations";
	
	public abstract Level getLevel(int i) throws EndOfLevelException;

}
