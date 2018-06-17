import java.util.ArrayList;

import processing.core.*;

public abstract class AbstractWall implements Wall {
	ArrayList<PVector> points = new ArrayList<PVector>();
	
	AbstractWall(ArrayList<PVector> points){
		this.points = points;
	}
	
	AbstractWall(float x1, float y1, float x2, float y2){
		this.points.add(new PVector(x1, y1));
		this.points.add(new PVector(x2, y2));
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Default points, returns an ArrayList containing copies of the first and last point of the wall
	 */
	@Override
	public ArrayList<PVector> points() {
		if(points.size() < 2) {
			return null;
		}
		ArrayList<PVector> definingPoints = new ArrayList<PVector>();
		definingPoints.add(new PVector(this.points.get(0).x, this.points.get(0).y));
		definingPoints.add(new PVector(this.points.get(this.points.size()-1).x, this.points.get(this.points.size()-1).y));
		return definingPoints;
	}

	@Override
	public void move(PVector amount) {
		for(PVector point : this.points) {
			point.add(amount);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Default setPosition, moves the first point in the wall to the position (and all other points relatively)
	 */
	@Override
	public void setPosition(PVector position) {
		for(PVector point : this.points) {
			point.add(PVector.sub(this.points.get(0), position));
		}
	}

	@Override
	public void draw(PApplet app) {
		app.stroke(255);
		app.strokeWeight(3);
		app.line(this.points.get(0).x, this.points.get(0).y, this.points.get(this.points.size()-1).x, this.points.get(this.points.size()-1).y);

	}

	@Override
	public Wall clone() {
		return this;
	}

}
