package com.ngame.factories;

import com.ngame.models.Level;
import com.ngame.utils.EndOfLevelException;


public abstract class LevelFactory {
	
	public static final String WINNER_SALUTE = "You have reached the end of the game... Congratulations!";
	public static final String SOLVED_LEVEL_SALUTE = "Level Solved!!! Congratulations on solving the level using minimum number of transformations";
	public static final String NO_MORE_LEVES_SALUTE = "You have reached level mastery... Try your skills at \"Time Battle\" mode !";
	
	public abstract Level getLevel(int i) throws EndOfLevelException;

}
