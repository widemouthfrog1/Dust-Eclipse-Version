import processing.core.PApplet;

public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("Main");
		
	}
	
	public void settings(){
		size(300,300);
    }

    public void setup(){
    	fill(120,50,240);
    }

    public void draw(){
    	ellipse(width/2,height/2,second(),second());
    }

}
