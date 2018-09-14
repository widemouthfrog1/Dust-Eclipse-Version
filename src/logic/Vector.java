package logic;

public interface Vector {
	
	public float x();
	public float y();
	
	public void set(Vector v);
	public void set(float x, float y);
	
	public float mag();
	public float magSq();
	
	public Vector add(Vector v);
	public static Vector add(Vector v1, Vector v2) {
		return v1.copy().add(v2);
	}
	
	public Vector sub(Vector v);
	public static Vector sub(Vector v1, Vector v2) {
		return v1.copy().sub(v2);
	}
	
	public Vector mult(float s);
	public static Vector mult(Vector v, float s) {
		return v.copy().mult(s);
	}
	public Vector div(float s);
	public static Vector div(Vector v, float s) {
		return v.copy().div(s);
	}
	
	public float dot(Vector v);
	public static float dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}
	
	public float dist(Vector v);
	
	public Vector cross(Vector v);
	public static Vector cross(Vector v1, Vector v2) {
		return v1.copy().cross(v2);
	}
	
	public Vector normalise();
	
	public Vector limit(float max);
	
	public Vector setMag(float mag);
	
	public float heading();
	
	public Vector rotate(float angle);
	public static Vector rotate(Vector v, float angle) {
		return v.copy().rotate(angle);
	}
	
	public Vector copy();
	
	public float angleBetween(Vector v);
	public static float angleBetween(Vector v1, Vector v2) {
		return v1.angleBetween(v2);
	}
	
	public float[] array();
	
	@Override
	public boolean equals(Object o);
}
