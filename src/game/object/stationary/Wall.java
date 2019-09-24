package game.object.stationary;
import java.util.ArrayList;

import game.object.entity.Entity;
import logic.Vector;
import processing.core.*;

public interface Wall {
	
	/**
	 * Returns a copy of the defining points of the wall
	 * @return
	 */
	public ArrayList<Vector> points();
	
	/**
	 * Moves the wall by an amount relative to it's original position
	 * @param amount
	 */
	public void move(Vector amount);
	
	/**
	 * Moves the wall to a specific location
	 * @param position
	 */
	public void setPosition(Vector position);
	
	/**
	 * Draws the wall to the app
	 */
	public void draw(PApplet app, Vector offset);
	
	/**
	 * Returns a clone of this wall
	 * @return
	 */
	public Wall clone();
	
	public boolean inBounds(Vector position, Vector Velocity);
	
	public Vector getIntersection(Vector position, Vector velocity);

	public boolean getPlayerSide(Vector absolutePosition);
	
	public Vector getNormal(Entity e);
	
	public void handleCollisions(Entity e, Vector centre);

	public float bounciness();
}
