package com.ngame.factories;

import com.ngame.models.Level;
import com.ngame.utils.EndOfLevelException;


public abstract class LevelFactory {
	
	public abstract Level getLevel(int i) throws EndOfLevelException;

}
