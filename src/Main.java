import processing.core.*;

public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("Main");
		
	}
	
	Player player;
	
	public void settings(){
		size(1000, 700);
		
    }

    public void setup(){
    	player = new Player(this, new PVector(width/2, height/2));
    }

    public void draw(){
    	background(0);
    	player.draw();
    }

}
