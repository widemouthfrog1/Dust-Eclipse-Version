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
		game.update(new DVector(this.mouseX, this.mouseY));
		game.draw(this);

	}

	public void mousePressed() {
		if (mouseButton == RIGHT) {
			game.press("RIGHTMOUSE");
		}
		if (mouseButton == LEFT) {
			game.press("LEFTMOUSE");
		}
	}

	public void mouseReleased() {

		if (mouseButton == RIGHT) {
			game.release("RIGHTMOUSE");
		}
		if (mouseButton == LEFT) {
			game.release("LEFTMOUSE");
		}
	}

	public void keyPressed() {
		game.press(""+key);
	}

	public void keyReleased() {
		game.release(""+key);
	}
}
