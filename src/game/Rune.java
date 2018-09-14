package game;

import java.util.ArrayList;

import logic.Vector;
import processing.core.PApplet;

public abstract class Rune {
	
	protected ArrayList<Vector> points;
	public Rune(ArrayList<Vector> points){
		this.points = points;
	}
	public abstract void draw(PApplet app);
	
	
		
	
	
	
}
