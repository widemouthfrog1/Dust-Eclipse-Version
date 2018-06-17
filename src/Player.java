import java.util.ArrayList;

import processing.core.*;

/**
 * The Player Object for Dust
 * 
 * @author Karl Bennett
 *
 */

public class Player {
	private PApplet app;
	private ArrayList<PVector> points = new ArrayList<PVector>();
	private PVector screenPosition;
	private PVector position;
	private PVector velocity;
	private PVector acceleration;
	private float dragWeight = 1;
	
	
	Player(PApplet app, PVector position){
		this.app = app;
		this.screenPosition = position;
		this.position = new PVector(0,0);
		this.velocity = new PVector(0,0);
		this.acceleration = new PVector(0,0);
		setupShape();
	}
	
	/**
	 * Draws the player on the screen
	 */
	public void draw() {
		reset();
		scalePoints(2);
		rotatePoints(new PVector(screenPosition.x - app.mouseX, screenPosition.y - app.mouseY).heading());
		rotatePoints((float)(Math.PI/2));
		updatePos();
    	//PApplet.print(velocity + "\n");
		translateByPos();
		app.strokeWeight(6);
		drawShape();
		app.strokeWeight(3);
	}
	
	/**
	 * Sets the drag weight component for velocity calculations, for example, if the player is on ice the drag weight should be lower
	 * @param drag
	 */
	public void drag(float drag) {
		this.dragWeight = drag;
	}
	
	/**
	 * Returns the position of the player relative to the world
	 * @return
	 * 		Used to move everything but the player
	 */
	public PVector position() {
		return position;
	}
	
	/**
	 * Sets the acceleration of the player
	 * @param acceleration
	 */
	public void setAcceleration(PVector acceleration) {
		this.acceleration = acceleration;
	}
	
	/**
	 * Adds the points required to create the shape of the player to the player's points list
	 */
	private void setupShape() {
		points.add(new PVector(0, -5));
		points.add(new PVector(5, -8));
		points.add(new PVector(5, 0));
		points.add(new PVector(0, 10));
		points.add(new PVector(-5, 0));
		points.add(new PVector(-5, -8));
	}
	
	/**
	 * Resets the points in the player's points list to be what they were at setup
	 */
	private void reset() {
		points.set(0, new PVector(0, -5));
		points.set(1, new PVector(5, -8));
		points.set(2, new PVector(5, 0));
		points.set(3, new PVector(0, 10));
		points.set(4, new PVector(-5, 0));
		points.set(5, new PVector(-5, -8));
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
			points.set(i, new PVector((float)(points.get(i).x*Math.cos(angle) - points.get(i).y*Math.sin(angle)), (float)(points.get(i).x*Math.sin(angle) + points.get(i).y*Math.cos(angle))));
		}
	}
	
	/**
	 * Applies a translation matrix on player's point list equal to the position vector
	 */
	private void translateByPos() {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, new PVector(points.get(i).x +screenPosition.x, points.get(i).y +screenPosition.y));
		}
	}
	
	/**
	 * Updates the velocity vector and then the position vector
	 */
	private void updatePos() {
		velocity.add(acceleration.setMag(acceleration.mag() - drag()));
		position.add(velocity);
	}
	
	/**
	 * Returns the drag component for velocity calculations
	 * @return	
	 */
	private float drag() {
		return velocity.mag()*dragWeight;
	}
	
	/**
	 * Creates a shape object made up of the player's point list and parses it to the app to draw
	 */
	private void drawShape() {
		PShape shape = app.createShape();
		shape.beginShape();
		for (int i = 0; i < points.size(); i++) {
			shape.vertex(points.get(i).x, points.get(i).y);
		}
		shape.endShape(PShape.CLOSE);
		shape.setFill(false);
		shape.setStroke(app.color(255));
		//shape.setStroke(color(255, (int)2*health));
		app.shape(shape);
	}
}
