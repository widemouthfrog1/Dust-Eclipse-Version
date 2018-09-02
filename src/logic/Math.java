package logic;
/**
 * A class for heavy mathematical computations in Dust
 * @author Karl Bennett
 */
public class Math {
	/**
	 * @param point1
	 * 			The first point on line 1
	 * @param point2
	 * 			The second point on line 1
	 * @param point3
	 * 			The first point on line 2
	 * @param point4
	 * 			The second point on line 2
	 * @return The intersection, in the form of a vector, between 2 infinite lines given 2 points on each line
	 */
	public static Vector intersection(Vector point1, Vector point2, Vector point3, Vector point4) {
		float x1 = point1.x();
		float x2 = point2.x();
		float x3 = point3.x();
		float x4 = point4.x();
		float y1 = point1.y();
		float y2 = point2.y();
		float y3 = point3.y();
		float y4 = point4.y();
		float denominator = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if(denominator == 0) {
			return null;
		}
		float ix = ((x1*y2-y1*x2)*(x3-x4)-(x1-x2)*(x3*y4-y3*x4))/denominator;
		float iy = ((x1*y2-y1*x2)*(y3-y4)-(y1-y2)*(x3*y4-y3*x4))/denominator;
		return new DVector(ix, iy);
	}
	/**
	 * 
	 * @param point1
	 * 			The first point of the first line segment
	 * @param point2
	 * 			The last point of the first line segment
	 * @param point3
	 * 			The first point of the second line segment
	 * @param point4
	 * 			The last point of the second line segment
	 * @param onFirstLine
	 * 			If you want to check if the intersection is on the first line segment, this should be true, otherwise false.
	 * @return True if, were you to find the intersection between the 2 lines created by these line segments, the intersection is between the first and last point of the chosen segment
	 */
	public static boolean intersectionOnSegment(Vector point1, Vector point2, Vector point3, Vector point4, boolean onFirstLine) {
		float x1 = point1.x();
		float x2 = point2.x();
		float x3 = point3.x();
		float x4 = point4.x();
		float y1 = point1.y();
		float y2 = point2.y();
		float y3 = point3.y();
		float y4 = point4.y();
		float denominator = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if(denominator == 0) {
			return false;
		}
		if(onFirstLine) {
			float t = ((x1-x3)*(y3-y4) - (y1-y3)*(x3-x4))/((x1-x2)*(y3-y4) - (y1-y2)*(x3-x4));
			return t <= 1 && t >= 0;
		}else {
			float u = -((x1-x2)*(y1-y3) - (y1-y2)*(x1-x3))/((x1-x2)*(y3-y4) - (y1-y2)*(x3-x4));
			return u <= 1 && u >= 0;
		}
	}
}
