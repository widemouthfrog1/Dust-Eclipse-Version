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
    	char[] pressableButtons = {'w', 'a', 's', 'd'};
    	setupMaps(pressableButtons);
    	levels.add(new Level(this));
    }

    public void draw(){
    	background(0);
    	PVector acceleration = new PVector(0,0);
    	for(char c : buttonsReleased.keySet()) {
    		if(buttonsReleased.get(c)) {
        		buttonsReleased.put(c, false);
        		buttonsPressed.put(c, false);
        	}
    	}
    	
    	if(buttonsPressed.get('w')) {
    		acceleration.add(new PVector(0,1));
    	}
    	if(buttonsPressed.get('a')) {
    		acceleration.add(new PVector(1,0));
    	}
    	if(buttonsPressed.get('s')) {
    		acceleration.add(new PVector(0,-1));
    	}
    	if(buttonsPressed.get('d')) {
    		acceleration.add(new PVector(-1,0));
    	}
    	
    	Level level = levels.get(0);
    	player.setAcceleration(acceleration);
    	HashMap<Wall, Boolean> before = level.playerAtFrontMap();
    	player.updatePos();
    	HashMap<Wall, Boolean> after = level.playerAtFrontMap();
    	for(Wall wall : before.keySet()) {
    		if(before.get(wall) != after.get(wall)) {
    			//sudo code:
    			//if in bounds of wall
    				//set position of player to where they would have crossed the wall
    				//change acceleration to negate velocity in small amount of time (probably 1 tick)
    		}
    	}
    	player.draw();
    	
    	level.offset(player.position());
    	level.draw(this);
    	
    	
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
    /**
     * Adds buttons to buttonsPressed and buttonsReleased maps
     */
    private void setupMaps(char[] chars) {
    	for(char c : chars) {
    		buttonsPressed.put(c, false);
    		buttonsReleased.put(c, false);
    	}
    }
}
