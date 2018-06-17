import java.util.HashMap;
import java.util.Map;

import processing.core.*;


public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("Main");
		
	}
	
	
	Map<Character, Boolean> buttonsPressed = new HashMap<Character, Boolean>();
	Map<Character, Boolean> buttonsReleased = new HashMap<Character, Boolean>();
	
	public void settings(){
		size(1000, 700);
		
    }

    public void setup(){
    	setupMaps();
    	this.rectMode(CORNER);
    }

    public void draw(){
    	background(0);
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
    	if(buttonsReleased.get('e')) {
    		buttonsReleased.put('e', false);
    		buttonsPressed.put('e', false);
    	}
    	
    	if(buttonsPressed.get('s')) {
    		fill(255, 0, 0);
    		noStroke();
    		this.rect(100, 50, 50, 50);
    	}
    	if(buttonsPressed.get('w')) {
    		fill(0, 255, 0);
    		noStroke();
    		this.rect(100, 0, 50, 50);
    	}
    	if(buttonsPressed.get('a')) {
    		fill(0, 0, 255);
    		noStroke();
    		this.rect(50, 50, 50, 50);
    	}
    	if(buttonsPressed.get('d')) {
    		fill(255);
    		noStroke();
    		this.rect(150, 50, 50, 50);
    	}
    	
    	if(buttonsPressed.get('e')) {
    		fill(255, 255, 0);
    		noStroke();
    		this.rect(150, 0, 50, 50);
    	}
    	
    }
    
    public void keyPressed() {
    	PApplet.print(key);
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
    	buttonsPressed.put('e', false);
    	
    	buttonsReleased.put('w', false);
    	buttonsReleased.put('a', false);
    	buttonsReleased.put('s', false);
    	buttonsReleased.put('d', false);
    	buttonsReleased.put('e', false);
    }
}
