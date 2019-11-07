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
			game.press("right_mouse");
		}
		if (mouseButton == LEFT) {
			game.press("left_mouse");
		}
	}

	public void mouseReleased() {

		if (mouseButton == RIGHT) {
			game.release("right_mouse");
		}
		if (mouseButton == LEFT) {
			game.release("left_mouse");
		}
	}

	public void keyPressed() {
		game.press(""+key);
	}

	public void keyReleased() {
		game.release(""+key);
	}
}
