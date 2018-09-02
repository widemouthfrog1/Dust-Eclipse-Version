package game;
import java.util.ArrayList;

import logic.Vector;

public class StandardWall extends AbstractWall {
	
	
	public StandardWall(ArrayList<Vector> points){
		super(points);
	}
	public StandardWall(float x1, float y1, float x2, float y2){
		super(x1, y1, x2, y2);
	}
	public Wall clone() {
		AbstractWall wall = new StandardWall(this.points());
		return wall;
	}
	@Override
	public float bounciness() {
		return 1f;
	}
}
