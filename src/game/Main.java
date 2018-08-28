package game;
import processing.core.*;


public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("game.Main");
		
	}
	Game game;
	public void settings(){
		size(1000, 700);
		
    }

    public void setup(){
    	game = new Game(width, height);
    	
    }

    public void draw(){
    	background(0);
    	game.updatePosition();
    	game.draw(this);
    	
    	
    }
    public void keyPressed() {
    	game.pressed(key);
    }
    
    public void keyReleased() {
    	game.released(key);
    }
}
