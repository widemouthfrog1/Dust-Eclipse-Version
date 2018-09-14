package logic;

import java.math.BigDecimal;

import processing.core.PVector;

public class DVector implements Vector {
	PVector vector;
	public DVector(float x, float y){
		vector = new PVector(x,y);
	}

	@Override
	public void set(Vector v) {
		vector.set(v.x(), v.y());
	}

	@Override
	public void set(float x, float y) {
		vector.set(x, y);
	}

	@Override
	public float mag() {
		return vector.mag();
	}

	@Override
	public float magSq() {
		return vector.magSq();
	}

	@Override
	public Vector add(Vector v) {
		PVector sum = vector.add(new PVector(v.x(),v.y()));
		return new DVector(sum.x, sum.y);
	}

	@Override
	public Vector sub(Vector v) {
		PVector subtraction = vector.sub(new PVector(v.x(),v.y()));
		return new DVector(subtraction.x, subtraction.y);
	}

	@Override
	public Vector mult(float s) {
		vector.mult(s);
		return this.copy();
	}

	@Override
	public Vector div(float s) {
		vector.div(s);
		return this.copy();
	}

	@Override
	public float dist(Vector v) {
		return vector.dist(new PVector(v.x(), v.y()));
	}

	@Override
	public Vector cross(Vector v) {
		vector.cross(new PVector(v.x(), v.y()));
		return this.copy();
	}

	@Override
	public Vector normalise() {
		vector.normalize();
		return this.copy();
	}

	@Override
	public Vector limit(float max) {
		vector.limit(max);
		return this.copy();
	}

	@Override
	public Vector setMag(float mag) {
		vector.setMag(mag);
		return this.copy();
	}

	@Override
	public float heading() {
		return vector.heading();
	}

	@Override
	public Vector rotate(float angle) {
		vector.rotate(angle);
		return this.copy();
	}

	@Override
	public float angleBetween(Vector v) {
		return PVector.angleBetween(vector, new PVector(v.x(), v.y()));
	}

	@Override
	public float[] array() {
		return vector.array();
	}

	@Override
	public float x() {
		return vector.x;
	}

	@Override
	public float y() {
		return vector.y;
	}

	@Override
	public Vector copy() {
		return new DVector(vector.x,vector.y);
	}

	@Override
	public float dot(Vector v) {
		return vector.dot(new PVector(v.x(), v.y()));
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof DVector)) {
			return false;
		}
		DVector d = (DVector) o;
		return this.x() == d.x() && this.y() == d.y();
	}
	
	@Override
	public String toString() {
		return "["+this.x()+", "+this.y()+"]";
	}
	
	

}
