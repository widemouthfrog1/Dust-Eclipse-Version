import processing.core.*;
import java.util.ArrayList;

public class Level {
	ArrayList<Wall> walls = new ArrayList <Wall>();
	private static int levelCounter = 0;
	private int level;
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
	
	public void draw(PApplet app) {
		for(Wall wall : this.walls) {
			wall.draw(app);
		}
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
	}
}
