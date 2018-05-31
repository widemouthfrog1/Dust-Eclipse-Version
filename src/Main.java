import processing.core.*;

public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("Main");
		
	}
	
	Player player;
	
	public void settings(){
		size(600, 600);
		player = new Player(this, new PVector(width/2, height/2));
    }

    public void setup(){
    }

    public void draw(){
    	background(0);
    	player.draw();
    }

}
