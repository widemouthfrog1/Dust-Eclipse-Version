package game;

import logic.DVector;
import processing.core.*;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("game.Main");

	}

	Game game;

	public void settings() {
		size(1000, 700);

	}

	public void setup() {
		game = new Game(width, height);

	}

	public void draw() {
		background(0);
		game.setMouse(new DVector(this.mouseX, this.mouseY));
		game.updatePosition();
		game.draw(this);

	}

	public void mousePressed() {
		if (mouseButton == RIGHT) {
			game.pressed(Game.Control.RIGHTMOUSE);
		}
		if (mouseButton == LEFT) {
			game.pressed(Game.Control.LEFTMOUSE);
		}
	}

	public void mouseReleased() {

		if (mouseButton == RIGHT) {
			game.released(Game.Control.RIGHTMOUSE);
		}
		if (mouseButton == LEFT) {
			game.released(Game.Control.LEFTMOUSE);
		}
	}

	public void keyPressed() {
		game.pressed(key);
	}

	public void keyReleased() {
		game.released(key);
	}
}
