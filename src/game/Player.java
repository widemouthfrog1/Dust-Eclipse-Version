package game;
import java.util.ArrayList;

import logic.DVector;
import logic.Vector;
import processing.core.*;

/**
 * The Player Object for Dust
 * 
 * @author Karl Bennett
 *
 */

public class Player implements Entity{
	private ArrayList<Vector> points = new ArrayList<Vector>();
	private Vector screenPosition;
	private Vector position;
	private Vector velocity;
	private Vector acceleration;
	public static float DRAGWEIGHT = 0.2f;
	
	
	
	public Player(Vector position){
		this.screenPosition = position;
		this.position = new DVector(0,0);
		this.velocity = new DVector(0,0);
		this.acceleration = new DVector(0,0);
		setupShape();
	}
	
	/**
	 * Draws the player on the screen
	 */
	public void draw(PApplet app) {
		calculatePosition(app.mouseX, app.mouseY);
		app.strokeWeight(6);
		drawShape(app);
		app.strokeWeight(3);
	}
	
	/**
	 * Sets the drag weight component for velocity calculations, for example, if the player is on ice the drag weight should be lower
	 * @param drag
	 */
	public void drag(float drag) {
		DRAGWEIGHT = drag;
	}
	
	/**
	 * Returns the position of the player relative to the world but the middle of the screen is not taken into account (to get the true position, you must add new PVector(app.width/2, app.height/2))
	 * @return
	 * 		Used to move everything but the player
	 */
	public Vector position() {
		return position.copy();
	}
	
	public Vector absolutePosition() {
		return position();
	}
	
	public void setAbsolutePosition(Vector absolutePosition) {
		this.setPosition(absolutePosition);
	}
	
	/**
	 * Sets the acceleration of the player
	 * @param acceleration
	 */
	public void setAcceleration(Vector acceleration) {
		this.acceleration = acceleration;
	}
	
	/**
	 * Sets the velocity of the player
	 * @param velocity
	 */
	public void setVeclocity(Vector velocity) {
		this.velocity = velocity;
	}
	
	/**
	 * Updates the velocity vector and then the position vector
	 */
	public void updatePosition() {
		velocity.add(Vector.sub(this.acceleration, this.drag()));
		position.add(velocity);
	}
	
	public void updatePositionWithoutDrag() {
		velocity.add(this.acceleration);
		position.add(velocity);
	}
	
	/**
	 * Set the player's position to this position
	 * @param position
	 */
	public void setPosition(Vector position) {
		this.position = position;
	}
	
	public void calculatePosition(int mouseX, int mouseY) {
		reset();
		scalePoints(2);
		rotatePoints(new DVector(screenPosition.x() - mouseX, screenPosition.y() - mouseY).heading());
		rotatePoints((float)(Math.PI/2));
		translateByPos();
	}
	
	/**
	 * Adds the points required to create the shape of the player to the player's points list
	 */
	private void setupShape() {
		points.add(new DVector(0, -5));
		points.add(new DVector(5, -8));
		points.add(new DVector(5, 0));
		points.add(new DVector(0, 10));
		points.add(new DVector(-5, 0));
		points.add(new DVector(-5, -8));
	}
	
	/**
	 * Resets the points in the player's points list to be what they were at setup
	 */
	private void reset() {
		points.set(0, new DVector(0, -5));
		points.set(1, new DVector(5, -8));
		points.set(2, new DVector(5, 0));
		points.set(3, new DVector(0, 10));
		points.set(4, new DVector(-5, 0));
		points.set(5, new DVector(-5, -8));
	}
	
	/**
	 * Applies a scale matrix on player's point list
	 * @param scale
	 */
	private void scalePoints(float scale) {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, points.get(i).mult(scale));
		}
	}
	
	/**
	 * Applies a rotation matrix on player's point list
	 * @param angle
	 */
	private void rotatePoints(float angle) {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, new DVector((float)(points.get(i).x()*Math.cos(angle) - points.get(i).y()*Math.sin(angle)), (float)(points.get(i).x()*Math.sin(angle) + points.get(i).y()*Math.cos(angle))));
		}
	}
	
	/**
	 * Applies a translation matrix on player's point list equal to the position vector
	 */
	private void translateByPos() {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, new DVector(points.get(i).x() +screenPosition.x(), points.get(i).y() +screenPosition.y()));
		}
	}
	
	/**
	 * Returns the drag component for velocity calculations
	 * @return	
	 */
	private Vector drag() {
		return velocity.copy().mult(DRAGWEIGHT);
	}
	
	/**
	 * Creates a shape object made up of the player's point list and parses it to the app to draw
	 */
	private void drawShape(PApplet app) {
		PShape shape = app.createShape();
		shape.beginShape();
		for (int i = 0; i < points.size(); i++) {
			shape.vertex(points.get(i).x(), points.get(i).y());
		}
		shape.endShape(PShape.CLOSE);
		shape.setFill(false);
		shape.setStroke(app.color(255));
		//shape.setStroke(color(255, (int)2*health));
		app.shape(shape);
	}

	public Vector velocity() {
		return this.velocity.copy();
	}
	public Vector acceleration() {
		return this.acceleration.copy();
	}
}
