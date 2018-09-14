package game;

import java.util.ArrayList;

import logic.Vector;
import processing.core.PApplet;

public interface Projectile {
	int getDamage();
	Vector getPos();
	boolean onScreen(PApplet app);
	ArrayList<Vector> getPoints();
	void draw(PApplet app);
}
