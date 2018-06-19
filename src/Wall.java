import java.util.ArrayList;

import processing.core.*;

public interface Wall {
	
	/**
	 * Returns a copy of the defining points of the wall
	 * @return
	 */
	public ArrayList<PVector> points();
	
	/**
	 * Moves the wall by an amount relative to it's original position
	 * @param amount
	 */
	public void move(PVector amount);
	
	/**
	 * Moves the wall to a specific location
	 * @param position
	 */
	public void setPosition(PVector position);
	
	/**
	 * Draws the wall to the app
	 */
	public void draw(PApplet app, PVector offset);
	
	public boolean playerAtFront();
	
	/**
	 * Returns a clone of this wall
	 * @return
	 */
	public Wall clone();
	
	public boolean inBounds(PApplet app, PVector position);
	
	public PVector getIntersection(PVector position, PVector velocity);

	public void setPlayerSide(PApplet app, PVector position);
	
	public PVector getNormal(PApplet app, PVector position, PVector velocity);

	public float bounciness();
}
