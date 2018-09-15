package game;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import logic.Vector;
import processing.core.PApplet;
import processing.core.PShape;

public class JaggedLine extends AbstractProjectile {
	
	Vector length;
	JaggedLine(ArrayList<Vector> points) {
		super(points);
		length = Vector.sub(this.points.get(0),points.get(points.size()-1));
	}


	@Override
	public void draw(PApplet app) {
		PShape shape = app.createShape();
	    shape.beginShape();
	    for (int i = 0; i < points.size()-1; i++) {
	      shape.vertex(points.get(i).x(), points.get(i).y());
	    }
	    shape.endShape();
	    shape.setStroke(app.color(255));
	    shape.setFill(false);
	    app.shape(shape);

	    points.get(points.size()-1).add(length);
	    Vector removed = points.remove(points.size()-1);
	    points.add(0, removed);

	}
	
	public static boolean checkLine(ArrayList<Vector> points) {
		return true;
	}
	
	public static List<Vector> isBolt(ArrayList<Vector> points) {
		List<Vector> bolt = new ArrayList<Vector>();
		if(points.size() < 4) {
			return bolt;
		}
		for(int i = 0; i < points.size()-4;i++) {
			if(
					!(logic.Math.leftOfVector(points.get(i+2),points.get(i),points.get(i+1)) &&
					!logic.Math.leftOfVector(points.get(i+3),points.get(i+1),points.get(i+2)) ||
					!logic.Math.leftOfVector(points.get(i+2),points.get(i),points.get(i+1)) &&
					logic.Math.leftOfVector(points.get(i+3),points.get(i+1),points.get(i+2)))
					
			) {
				break;
			}
			if(bolt.isEmpty()) {
				bolt.add(points.get(i));
				bolt.add(points.get(i+1));
				bolt.add(points.get(i+2));
				bolt.add(points.get(i+3));
			}else {
				bolt.add(points.get(i+3));
			}
		}
		return bolt;
	}


	public static List<Method> getChecks(){
		List<Method> checks = new ArrayList<Method>();
		try {
			checks.add(JaggedLine.class.getDeclaredMethod("isBolt", ArrayList.class));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return checks;
	}


}
