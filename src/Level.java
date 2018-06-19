import processing.core.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Level {
	private ArrayList<Wall> walls = new ArrayList <Wall>();
	private HashMap<Wall, Boolean> playerAtFrontMap = new HashMap<Wall, Boolean>(); 
	private static int levelCounter = 0;
	private int level;
	private PVector offset = new PVector(0,0);
	Level(PApplet app){
		level = levelCounter++;
		generate(app);
	}
	
	Level(ArrayList<Wall> walls){
		level = levelCounter++;
		this.walls = walls;
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
	
	public void updatePlayerAtFrontMap(PApplet app, PVector position) {
		for(Wall wall : this.walls) {
			wall.setPlayerSide(app, position);
			this.playerAtFrontMap.put(wall, wall.playerAtFront());
		}
	}
	
	/**
	 * Sets the offset for the level which is used to move all objects in the level at once
	 * @param offset
	 */
	public void offset(PVector offset) {
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
	private void generate(PApplet app) {
		this.walls.add(new StandardWall(app.width/2-100, app.height/2+100, app.width/2+100, app.height/2+100));
		this.walls.add(new StandardWall(app.width/2+100, app.height/2+100, app.width/2+100, app.height/2-100));
		this.walls.add(new StandardWall(app.width/2+100, app.height/2-100, app.width/2-100, app.height/2-100));
		this.walls.add(new StandardWall(app.width/2-100, app.height/2-100, app.width/2-100, app.height/2+100));
		this.walls.add(new StandardWall(-100, -100, 100, 100));
	}
}
