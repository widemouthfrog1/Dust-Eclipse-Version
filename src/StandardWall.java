import java.util.ArrayList;

import processing.core.PVector;

public class StandardWall extends AbstractWall {
	
	StandardWall(ArrayList<PVector> points){
		super(points);
	}
	StandardWall(float x1, float y1, float x2, float y2){
		super(x1, y1, x2, y2);
	}
	public Wall clone() {
		AbstractWall wall = new StandardWall(this.points());
		wall.setPlayerAtFront(this.playerAtFront);
		return wall;
	}
}
