import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import processing.core.*;


public class Main extends PApplet{

	public static void main(String[] args) {
		PApplet.main("Main");
		
	}
	
	Player player;
	PVector centre;
	Map<Character, Boolean> buttonsPressed = new HashMap<Character, Boolean>();
	Map<Character, Boolean> buttonsReleased = new HashMap<Character, Boolean>();
	ArrayList<Level> levels = new ArrayList<Level>();
	
	
	public void settings(){
		size(1000, 700);
		
    }

    public void setup(){
    	centre = new PVector(width/2, height/2);
    	player = new Player(this, centre.copy());
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
    		acceleration.add(new PVector(0,-1));
    	}
    	if(buttonsPressed.get('a')) {
    		acceleration.add(new PVector(-1,0));
    	}
    	if(buttonsPressed.get('s')) {
    		acceleration.add(new PVector(0,1));
    	}
    	if(buttonsPressed.get('d')) {
    		acceleration.add(new PVector(1,0));
    	}
    	
    	Level level = levels.get(0);
    	player.setAcceleration(acceleration);
    	level.updatePlayerAtFrontMap(this, player.position());
    	HashMap<Wall, Boolean> before = level.playerAtFrontMap();
    	player.updatePos();
    	level.updatePlayerAtFrontMap(this, player.position());
    	HashMap<Wall, Boolean> after = level.playerAtFrontMap();
    	for(Wall wall : before.keySet()) {
    		if(before.get(wall) != after.get(wall)) {
    			
    			if(wall.inBounds(this, player.position())) {
    				//require negative velocity because the player is at it's new position (behind the wall) and we need to find the intersection of it's old position and current velocity
    				//which is equivalent to new position minus current velocity
    				player.setPosition(PVector.sub(wall.getIntersection(player.position().add(centre.copy()), player.velocity().mult(-1)), centre.copy()));
    				PVector normal = wall.getNormal(this, player.position(), player.velocity()); //velocity is here in case I want a curved wall in the future
    				//player.setAcceleration(proj(wall normal) player.velocity.mult(-1*wall.bounciness())
    				//Readable explanation: set the player's acceleration to the projection of the player's velocity on the normal to the wall and multiply that vector the wall bounciness and then inverse the vector
    				//in other words find the component of the player's velocity going in the direction of the wall's normal. For a player going at a velocity parallel to that normal, this should be the entire velocity of the player
    				player.setAcceleration(normal.mult(-1*wall.bounciness()*(PVector.dot(player.velocity(), normal))/(normal.magSq())));
    				player.updatePos();
    			}
    		}
    	}
    	player.draw();
    	
    	level.offset(player.position().mult(-1));
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
