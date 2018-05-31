import java.util.ArrayList;

import processing.core.*;

public class Player {
	private PApplet app;
	private ArrayList<PVector> points = new ArrayList<PVector>();
	private PVector pos;
	
	Player(PApplet app, PVector pos){
		this.app = app;
		this.pos = pos;
		setupShape();
	}
	
	public void draw() {
		reset();
		scalePoints(2);
		rotatePoints(new PVector(pos.x - app.mouseX, pos.y - app.mouseY).heading());
		rotatePoints((float)(Math.PI/2));
		translateByPos();
		app.strokeWeight(6);
		drawShape();
		app.strokeWeight(3);
	}
	
	private void setupShape() {
		points.add(new PVector(0, -5));
		points.add(new PVector(5, -8));
		points.add(new PVector(5, 0));
		points.add(new PVector(0, 10));
		points.add(new PVector(-5, 0));
		points.add(new PVector(-5, -8));
	}
	
	private void reset() {
		points.set(0, new PVector(0, -5));
		points.set(1, new PVector(5, -8));
		points.set(2, new PVector(5, 0));
		points.set(3, new PVector(0, 10));
		points.set(4, new PVector(-5, 0));
		points.set(5, new PVector(-5, -8));
	}
	
	private void scalePoints(float scale) {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, points.get(i).mult(scale));
		}
	}
	
	private void rotatePoints(float angle) {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, new PVector((float)(points.get(i).x*Math.cos(angle) - points.get(i).y*Math.sin(angle)), (float)(points.get(i).x*Math.sin(angle) + points.get(i).y*Math.cos(angle))));
		}
	}
	
	private void translateByPos() {
		for (int i = 0; i < points.size(); i++) {
			points.set(i, new PVector(points.get(i).x +pos.x, points.get(i).y +pos.y));
		}
	}
	
	void drawShape() {
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
