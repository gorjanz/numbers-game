package com.ngame.models;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Class model for one level of the game
 * @author Gorjan
 *
 */
public class Level {

	private String gameNum;
	private String targetNum;
	private LinkedList<String> steps;
	private int minMoves;
	
	/**
	 * @param gameNumber the starting number of the level
	 * @param targetNumber the target number of the level
	 */
	public Level(String gameNumber, String targetNumber, LinkedList<String> steps) {
		gameNum = gameNumber;
		targetNum = targetNumber;
		this.steps = steps;
		minMoves = steps.size();
	}

	public LinkedList<String> getSolutionSteps(){
		return steps;
	}
	
	public int getMinMoves(){
		return minMoves;
	}
	
	public String getGameNum(){
		return gameNum;
	}
	
	public String getTargetNum(){
		return targetNum;
	}
	
	public String getSolution(){
		Collections.reverse(steps);
		StringBuilder sb = new StringBuilder();
		for (String step : steps) {
			sb.append(step + " -> ");
		}
		sb.append(targetNum);
		return sb.toString();
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj.getClass()!= Level.class)
			return false;
		Level l2 = (Level) obj;
		if(!gameNum.equals(l2.gameNum) || !targetNum.equals(l2.targetNum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Starting number: " + gameNum + " Target number: " + targetNum + " solved in " + minMoves + " steps: " + getSolution(); 
	}
	
}
