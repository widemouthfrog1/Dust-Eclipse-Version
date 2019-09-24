package game.object.entity.projectile;

import java.util.ArrayList;

import game.object.rune.Rune;
import logic.Vector;
import processing.core.PApplet;

public abstract class AbstractProjectile extends Rune implements Projectile {
	
	
	protected AbstractProjectile(ArrayList<Vector> points){
		super(points);
	}
	@Override
	public int getDamage() {
		return 1;
	}

	@Override
	public Vector getPos() {
		return points.get(0);
	}

	@Override
	public boolean onScreen(PApplet app) {
		boolean onScreen = false;
	    for (int i = 0; i < points.size(); i++) {
	      Vector point = points.get(i);
	      if (point.x() > 0 && point.x() < app.width && point.y() > 0 && point.y() < app.height) {
	        return true;
	      }
	    }
	    return onScreen;
	}

	@Override
	public ArrayList<Vector> getPoints() {
		// TODO Auto-generated method stub
		return points;
	}


}
