package game;

import logic.Vector;

public interface Entity {

	Vector velocity();

	Vector position();
	Vector absolutePosition();

	void setPosition(Vector position);
	void setAbsolutePosition(Vector absolutePosition);

	void setAcceleration(Vector mult);

	void updatePosition();

	void updatePositionWithoutDrag();
	
}
