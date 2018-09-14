package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import logic.DVector;
import logic.Vector;
import processing.core.PApplet;
import processing.core.PShape;

public class Game {
	ArrayList<Level> levels = new ArrayList<Level>();
	ArrayList<Rune> runes = new ArrayList<Rune>();
	Level currentLevel;
	public Player player;
	Vector center;
	ArrayList<Vector> currentDrawing = new ArrayList<Vector>();
	Vector mousePosition;
	RuneHandler viableRunes = new RuneHandler();
	
	public enum Control {UP, RIGHT, DOWN, LEFT, LEFTMOUSE, RIGHTMOUSE}
	public Map<Control, Character> controlToCharacter = new HashMap<Control, Character>();
	public Map<Character, Control> characterToControl = new HashMap<Character, Control>();
	private Map<Control, Boolean> buttonsPressed = new HashMap<Control, Boolean>();
	private Map<Control, Boolean> buttonsReleased = new HashMap<Control, Boolean>();
	public void setControlsToDefault() {
		controlToCharacter.clear();
		characterToControl.clear();
		controlToCharacter.put(Control.UP, 'w');
		characterToControl.put('w', Control.UP);
		buttonsPressed.put(Control.UP, false);
		buttonsReleased.put(Control.UP, false);
		controlToCharacter.put(Control.RIGHT, 'd');
		characterToControl.put('d', Control.RIGHT);
		buttonsPressed.put(Control.RIGHT, false);
		buttonsReleased.put(Control.RIGHT, false);
		controlToCharacter.put(Control.DOWN, 's');
		characterToControl.put('s', Control.DOWN);
		buttonsPressed.put(Control.DOWN, false);
		buttonsReleased.put(Control.DOWN, false);
		controlToCharacter.put(Control.LEFT, 'a');
		characterToControl.put('a', Control.LEFT);
		buttonsPressed.put(Control.LEFT, false);
		buttonsReleased.put(Control.LEFT, false);
		buttonsPressed.put(Control.LEFTMOUSE, false);
		buttonsReleased.put(Control.LEFTMOUSE, false);
		buttonsPressed.put(Control.RIGHTMOUSE, false);
		buttonsReleased.put(Control.RIGHTMOUSE, false);
		
	}
	
	public void setMouse(Vector position) {
		this.mousePosition = position;
	}
	
	public boolean pressed(Control button) {
		if(!controlToCharacter.containsKey(button) && !button.equals(Control.LEFTMOUSE) && !button.equals(Control.RIGHTMOUSE)) {
			return false;
		}
		buttonsPressed.put(button, true);
		return true;
	}
	
	public boolean released(Control button) {
		if(!controlToCharacter.containsKey(button) && !button.equals(Control.LEFTMOUSE) && !button.equals(Control.RIGHTMOUSE)) {
			return false;
		}
		buttonsReleased.put(button, true);
		return true;
	}
	
	public void setControl(Control control, Character button) {
		controlToCharacter.put(control, button);
		characterToControl.put(button, control);
	}
	public boolean pressed(Character button) {
		if(!characterToControl.containsKey(button)) {
			return false;
		}
		buttonsPressed.put(characterToControl.get(button), true);
		return true;
	}
	public boolean released(Character button) {
		if(!characterToControl.containsKey(button)) {
			return false;
		}
		buttonsReleased.put(characterToControl.get(button), true);
		return true;
	}
	//------------------------------------------------------------------------------------------------------------------------
	//Constructor
	public Game(int width, int height){
		viableRunes.addRune(JaggedLine.class, JaggedLine.getChecks());
		center = new DVector(width/2, height/2);
		levels.add(new Level(center));
		currentLevel = levels.get(0);
    	player = new Player(center.copy());
    	setControlsToDefault();
	}
	/**
	 * Updates the position of the player in the level without changing the acceleration
	 * Mainly exists for testing purposes
	 * @param level
	 * @param drag
	 * 			boolean that states whether drag calculations are active
	 */
	public void updatePosition(Level level, boolean drag) {
		for(Control c : buttonsReleased.keySet()) {
			
    		if(buttonsReleased.get(c)) {
    			if(c.equals(Control.LEFTMOUSE)) {
    				this.runes.add(viableRunes.getRune(currentDrawing));
    				this.currentDrawing.clear();
    			}
        		buttonsReleased.put(c, false);
        		buttonsPressed.put(c, false);
        	}
    	}
		if(buttonsPressed.get(Control.LEFTMOUSE)) {
			this.currentDrawing.add(new DVector(mousePosition.x(), mousePosition.y()));
		}
	    level.updatePlayerAtFrontMap(player.absolutePosition());
	    HashMap<Wall, Boolean> before = level.playerAtFrontMap();
	    if(drag) {
	    	player.updatePosition();
	    }else {
	    	player.updatePositionWithoutDrag();
	    }
	    level.updatePlayerAtFrontMap(player.absolutePosition());
	    HashMap<Wall, Boolean> after = level.playerAtFrontMap();
	    for(Wall wall : before.keySet()) {
	    	if(before.get(wall) != after.get(wall)) {//if passed over wall
	    		calculateWallCollisions(wall);
	    	}
	    }
	    level.offset(player.position().mult(-1));
	}
	
	


	public void updatePosition() {
		for(Control c : buttonsReleased.keySet()) {
			
    		if(buttonsReleased.get(c)) {
    			if(c.equals(Control.LEFTMOUSE)) {
    				this.runes.add(viableRunes.getRune(currentDrawing));
    				this.currentDrawing = new ArrayList<Vector>();
    			}
        		buttonsReleased.put(c, false);
        		buttonsPressed.put(c, false);
        	}
    	}
		if(buttonsPressed.get(Control.LEFTMOUSE)) {
			this.currentDrawing.add(new DVector(mousePosition.x(), mousePosition.y()));
		}
		Vector acceleration = new DVector(0,0);
    	setAcceleration(acceleration);
	    currentLevel.updatePlayerAtFrontMap(player.absolutePosition());
	    HashMap<Wall, Boolean> before = currentLevel.playerAtFrontMap();
	    player.updatePosition();
	    currentLevel.updatePlayerAtFrontMap(player.absolutePosition());
	    HashMap<Wall, Boolean> after = currentLevel.playerAtFrontMap();
	    
	    for(Wall wall : before.keySet()) {
	    	if(before.get(wall) != after.get(wall)) {//if passed over wall
	    		calculateWallCollisions(wall);
	    	}
	    }
	    currentLevel.offset(player.position().mult(-1));
	}
	
	public void handleDrawing() {
		
	}
	
	public void setAcceleration(Vector acceleration) {
    	
		if(buttonsPressed.get(Control.UP)) {
    		acceleration.add(new DVector(0,-1));
    	}
    	if(buttonsPressed.get(Control.LEFT)) {
    		acceleration.add(new DVector(-1,0));
    	}
    	if(buttonsPressed.get(Control.DOWN)) {
    		acceleration.add(new DVector(0,1));
    	}
    	if(buttonsPressed.get(Control.RIGHT)) {
    		acceleration.add(new DVector(1,0));
    	}
    	player.setAcceleration(acceleration);
    }
	
	public void calculateWallCollisions(Wall wall) {
		if(wall.inBounds(player.absolutePosition(), player.velocity())) {
			wall.handleCollisions(player, center);
		}
	}
	
	
	public void draw(PApplet app) {
		Level level = levels.get(0);
		player.draw(app);
    	level.draw(app);
    	for(Rune rune : this.runes) {
    		rune.draw(app);
    	}
    	PShape currentDrawing = app.createShape();
    	currentDrawing.beginShape();
	    for (int i = 0; i < this.currentDrawing.size()-1; i++) {
	    	currentDrawing.vertex(this.currentDrawing.get(i).x(), this.currentDrawing.get(i).y());
	    }
	    currentDrawing.endShape();
	    currentDrawing.setStroke(app.color(255));
	    currentDrawing.setFill(false);
	    app.shape(currentDrawing);
	}
	
	
}
