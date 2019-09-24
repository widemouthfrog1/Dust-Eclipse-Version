package game.object.stationary;

import java.util.ArrayList;

import game.object.rune.Rune;
import logic.Vector;
import processing.core.PApplet;
import processing.core.PShape;

public class InactiveRune extends Rune {

	public InactiveRune(ArrayList<Vector> points) {
		super(points);
	}

	@Override
	public void draw(PApplet app) {
		PShape shape = app.createShape();
	    shape.beginShape();
	    for (int i = 0; i < points.size()-1; i++) {
	      shape.vertex(points.get(i).x()+offset.x(), points.get(i).y()+offset.y());
	    }
	    shape.endShape();
	    shape.setStroke(app.color(255));
	    shape.setFill(false);
	    app.shape(shape);
	}

}
