import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.*;


public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("Main");
		
	}
	
	Player player;
	
	Map<Character, Boolean> buttonsPressed = new HashMap<Character, Boolean>();
	Map<Character, Boolean> buttonsReleased = new HashMap<Character, Boolean>();
	ArrayList<Level> levels = new ArrayList<Level>();
	
	public void settings(){
		size(1000, 700);
		
    }

    public void setup(){
    	player = new Player(this, new PVector(width/2, height/2));
    	setupMaps();
    	levels.add(new Level(this));
    }

    public void draw(){
    	background(0);
    	PVector acceleration = new PVector(0,0);
    	if(buttonsReleased.get('w')) {
    		buttonsReleased.put('w', false);
    		buttonsPressed.put('w', false);
    	}
    	if(buttonsReleased.get('a')) {
    		buttonsReleased.put('a', false);
    		buttonsPressed.put('a', false);
    	}
    	if(buttonsReleased.get('s')) {
    		buttonsReleased.put('s', false);
    		buttonsPressed.put('s', false);
    	}
    	if(buttonsReleased.get('d')) {
    		buttonsReleased.put('d', false);
    		buttonsPressed.put('d', false);
    	}
    	
    	if(buttonsPressed.get('w')) {
    		acceleration.add(new PVector(0,1));
    	}
    	if(buttonsPressed.get('a')) {
    		acceleration.add(new PVector(-1,0));
    	}
    	if(buttonsPressed.get('s')) {
    		acceleration.add(new PVector(0,-1));
    	}
    	if(buttonsPressed.get('d')) {
    		acceleration.add(new PVector(1,0));
    	}
    	
    	player.setAcceleration(acceleration);
    	levels.get(0).draw(this);
    	player.draw();
    	
    	
    }
    
    public void keyPressed() {
    	if(isValidKey(key)) {
    		buttonsPressed.put(key, true);
    	}
    }
    
    public void keyReleased() {
    	if(isValidKey(key)) {
    		buttonsReleased.put(key, true);
    	}
    }
    
    private boolean isValidKey(Character key) {
    	for(Character c :buttonsPressed.keySet()) {
    		if(key == c) {
    			return true;
    		}
    	}
    	
    	return false;
    }
    private void setupMaps() {
    	buttonsPressed.put('w', false);
    	buttonsPressed.put('a', false);
    	buttonsPressed.put('s', false);
    	buttonsPressed.put('d', false);
    	
    	buttonsReleased.put('w', false);
    	buttonsReleased.put('a', false);
    	buttonsReleased.put('s', false);
    	buttonsReleased.put('d', false);
    }
}
