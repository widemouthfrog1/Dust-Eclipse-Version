import java.util.ArrayList;

import processing.core.*;

public abstract class AbstractWall implements Wall {
	ArrayList<PVector> points = new ArrayList<PVector>();
	boolean playerAtFront;
	
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
	public void draw(PApplet app, PVector offset) {
		app.stroke(255);
		app.strokeWeight(3);
		app.line(this.points.get(0).x + offset.x, this.points.get(0).y + offset.y, this.points.get(this.points.size()-1).x + offset.x, this.points.get(this.points.size()-1).y + offset.y);
	}
	
	@Override
	public boolean playerAtFront() {
		return this.playerAtFront;
	}
	
	@Override
	public void setPlayerSide(PApplet app, PVector position) {
		float[] abc = abc(points().get(0), points().get(points.size()-1));    	
		this.playerAtFront = isPointInFront(app, position, abc);//if wall.a*player.x + wall.b*player.y + wall.c > 0, player counts as being in front of wall, otherwise, player is behind wall
	}
	
	@Override
	public boolean inBounds(PApplet app, PVector position) {
		PVector[] normal1 = normal(points().get(0), points().get(points.size()-1), points().get(0));
		PVector[] normal2 = normal(points().get(0), points().get(points.size()-1), points().get(points.size()-1));
		//if the point is in front of the normal to the wall at the first point and behind the normal to the wall at the last point or vice versa, return true 
		return isPointInFront(app, position, abc(normal1[0], normal1[1])) && !isPointInFront(app, position, abc(normal2[0], normal2[1])) || !isPointInFront(app, position, abc(normal1[0], normal1[1])) && isPointInFront(app, position, abc(normal2[0], normal2[1]));
	}
	
	protected boolean isPointInFront(PApplet app, PVector position, float[] abc) {
		return abc[0]*(position.x + app.width/2) + abc[1]*(position.y + app.height/2) + abc[2] > 0;
	}
	
	protected PVector[] normal(PVector p1, PVector p2, PVector middle) {
		PVector v = PVector.sub(p2, p1);
		return new PVector[] {PVector.add(middle,v.rotate(PApplet.PI)), PVector.add(middle,v.rotate(-PApplet.PI))};
	}
	
	protected float[] abc(PVector p1, PVector p2) {
		float x1 = p1.x;
		float y1 = p1.y;
		float x2 = p2.x;
		float y2 = p2.y;
		float a = y1 - y2;
		float b = x2 - x1;
		float c = x1*y2 - y1*x2;
		return new float[] {a,b,c};
	}
	
	protected void setPlayerAtFront(boolean playerAtFront) {
		this.playerAtFront = playerAtFront;
	}

	@Override
	public Wall clone() {
		return this;
	}

}
