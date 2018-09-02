package game;
import processing.core.*;
import java.util.ArrayList;
import java.util.HashMap;

import logic.Vector;

public class Level {
	private ArrayList<Wall> walls = new ArrayList <Wall>();
	private HashMap<Wall, Boolean> playerAtFrontMap = new HashMap<Wall, Boolean>(); 
	private static int levelCounter = 0;
	private int level;
	private Vector offset;
	public Level(Vector center){
		level = levelCounter++;
		generate(center);
	}
	
	public Level(ArrayList<Wall> walls){
		level = levelCounter++;
		this.walls = walls;
	}
	
	public Level(Wall wall){
		level = levelCounter++;
		this.walls.add(wall);
	}
	
	public ArrayList<Wall> walls(){
		ArrayList<Wall> wallsClone = new ArrayList <Wall>();
		for(Wall wall : this.walls) {
			wallsClone.add(wall.clone());
		}
		return wallsClone;
	}
	
	public HashMap<Wall, Boolean> playerAtFrontMap(){
		HashMap<Wall, Boolean> clone = new HashMap<Wall, Boolean>();
		for(Wall key : playerAtFrontMap.keySet()) {
			clone.put(key, playerAtFrontMap.get(key));
		}
		return clone;
	}
	
	public void draw(PApplet app) {
		for(Wall wall : this.walls()) {
			wall.draw(app, this.offset);
		}
	}
	
	public void updatePlayerAtFrontMap(Vector absolutePosition) {
		for(Wall wall : this.walls) {
			this.playerAtFrontMap.put(wall, wall.getPlayerSide(absolutePosition));
		}
	}
	
	/**
	 * Sets the offset for the level which is used to move all objects in the level at once
	 * @param offset
	 */
	public void offset(Vector offset) {
		this.offset = offset;
	}
	
	/**
	 * Returns the number ID for this level
	 * @return
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Generates a new level
	 */
	private void generate(Vector center) {
		//this.walls.add(new StandardWall(-100,100,100,100));
		//this.walls.add(new StandardWall(100,100,100,-100));
		this.walls.add(new StandardWall(100,-100,-100,-100));
		this.walls.add(new StandardWall(200,-200,-200,-200));
		this.walls.add(new StandardWall(-100,-100,-100,100));
		this.walls.add(new StandardWall(-200, -200, 200, 200));
	}
}
