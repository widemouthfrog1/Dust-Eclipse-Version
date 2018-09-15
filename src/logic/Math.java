package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for heavy mathematical computations in Dust
 * @author Karl Bennett
 */
public class Math {
	private static final double PI = 3.14159265358979323846264338;

	/**
	 * @param point1
	 * 			The first point on line 1
	 * @param point2
	 * 			The second point on line 1
	 * @param point3
	 * 			The first point on line 2
	 * @param point4
	 * 			The second point on line 2
	 * @return The intersection, in the form of a vector, between 2 infinite lines given 2 points on each line.
	 * 			Edge cases: Returns null if the 2 lines are parallel.
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
	 * 			Edge cases: Returns false if the line segments are parallel
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
	/**
	 * 
	 * @param point1
	 * 		The first point on the line.
	 * @param point2
	 * 		The last point on the line.
	 * @return
	 * 		An array containing the a, b, and c coefficients for the line defined by point1 and point2.
	 */
	public static float[] abc(Vector point1, Vector point2) {
		float a, b, c;
		float[] table;

		a = -(point2.y() - point1.y());
		b = point2.x() - point1.x();
		c = point1.x()*point2.y() - point1.y()*point2.x();

		table = new float[3];
		table[0] = a;
		table[1] = b;
		table[2] = c;
		return table;
	}
	
	/**
	 * Calculates the k value of a point and line
	 * @param abc
	 * 		A float array containing the abc coefficients of the line
	 * @param pos
	 * 		The point
	 * @return
	 * 		A float that if positive, means the point pos is above the line defined by abc, if negative, means the point is below the line, and if 0, the point is on the line
	 */
	public static float k(float[] abc, Vector pos) {
		  return abc[0]*pos.x() + abc[1]*pos.y() + abc[2];
		}
	/**
	 * Given a list of points, finds the vertices
	 * @param points
	 * @param angle
	 * 		The angle the angle of change must be greater than to be considered a vertex. If null will use default value of 4*PI/9
	 * @return
	 * 		A list of the vertices
	 */
	public static List<Vector> findVertices(List<Vector> points, Double angle) {
		if(angle == null) {
			angle = 4*PI/9;
		}
		  if (points.size() < 5) {
		    return null;
		  }
		  List<Vector> vertices = new ArrayList<Vector>();
		  ArrayList<Vector> simplified = new ArrayList<Vector>();
		  ArrayList<Vector> simplifiedPoints = new ArrayList<Vector>();
		  simplifiedPoints.add(points.get(0));
		  for (int i = 0; i < points.size(); i += 4) {
		    int next = i+4;
		    while (next >= points.size()) {
		      next--;
		    }
		    simplified.add(new DVector(points.get(next).x() - points.get(i).x(), points.get(next).y() - points.get(i).y())); //approximates the shape/line created by the user
		    simplifiedPoints.add(points.get(next).copy());
		  }
		  vertices.add(simplifiedPoints.get(0));//add the first vertex
		  for (int i = 0; i < simplified.size()-1; i++) {
			  //the smaller the angle between them the closer they are to being the same
			  if(simplified.get(i).angleBetween(simplified.get(i+1)) > angle) {
				  //if the next one also passes it is likely the true vertex is the intersection between line i and line i+2
				  if(i+2 != simplified.size() && simplified.get(i+1).angleBetween(simplified.get(i+2)) > angle){
					  vertices.add(intersection(simplifiedPoints.get(i), simplifiedPoints.get(i+1), simplifiedPoints.get(i+2), simplifiedPoints.get(i+3)));
				  }else {
					  //otherwise the vertex is assumed to be the last point on line i
					  vertices.add(simplifiedPoints.get(i));
				  }
			  }
		  }
		  vertices.add(simplifiedPoints.get(simplifiedPoints.size()-1));//add the last vertex
		  return vertices;
		}
	/**
	 * Returns true if the point point is left of the vector made from vectorPoint1 to vectorPoint2
	 * @param point
	 * @param vectorPoint1
	 * @param vectorPoint2
	 * @return
	 */
	public static boolean leftOfVector(Vector point, Vector vectorPoint1, Vector vectorPoint2 ) {
		return (vectorPoint2.x()-vectorPoint1.x())*(point.y()-vectorPoint1.y()) - (vectorPoint2.y()-vectorPoint1.y())*(point.x()-vectorPoint1.x()) >= 0;
	}
	
	/**
	 * Removes duplicate vectors in an array of vectors
	 * @param vectors
	 */
	public static void removeDuplicateVectors(ArrayList<Vector> vectors) {
		  Vector vector1;
		  Vector vector2;
		  int i = 1;
		  while (i < vectors.size()) {
			  vector1 = vectors.get(i-1);
			  vector2 = vectors.get(i);
		    while (vector1.equals(vector2)) {
		    	vectors.remove(i);
		      if (i < vectors.size()) {
		    	  vector2 = vectors.get(i);
		      } else {
		        break;
		      }
		    }
		    i++;
		  }
		}
}
