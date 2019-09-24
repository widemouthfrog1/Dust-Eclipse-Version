package game.object.rune;

import java.util.ArrayList;

import logic.Vector;
import processing.core.PApplet;

public abstract class Rune {
	protected Vector offset;
	protected ArrayList<Vector> points;
	public Rune(ArrayList<Vector> points){
		this.points = points;
	}
	public abstract void draw(PApplet app);
	
	/**
	 * Offsets this rune by the specified amount
	 * @param amount
	 */
	public void offset(Vector amount) {
		offset = amount;
	}
		
	
	
	
}
