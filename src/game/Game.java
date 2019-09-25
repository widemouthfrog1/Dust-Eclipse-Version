package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import game.object.entity.Player;
import game.object.entity.projectile.JaggedLine;
import game.object.rune.Rune;
import game.object.rune.RuneHandler;
import game.object.stationary.InactiveRune;
import game.object.stationary.Wall;
import logic.DVector;
import logic.Vector;
import processing.core.PApplet;
import processing.core.PShape;

public class Game {
	ArrayList<Level> levels = new ArrayList<Level>();
	ArrayList<Rune> runes = new ArrayList<Rune>();
	Level currentLevel;
	public Player player;
	public Vector offset = new DVector(0,0);
	Vector center;
	ArrayList<Vector> currentDrawing = new ArrayList<Vector>();
	RuneHandler viableRunes = new RuneHandler();
	
	private ControlArray controls = new ControlArray();
	
	private void createControls() {
		controls.addControl("UP", "w");
		controls.addControl("DOWN", "s");
		controls.addControl("LEFT", "a");
		controls.addControl("RIGHT", "d");
		controls.addControl("LEFTMOUSE", "left mouse");
		controls.addControl("RIGHTMOUSE", "right mouse");
	}
	
	/**
	 * Sets states of all controls that were in state PRESSED or RELEASED to HELD or INACTIVE respectively.
	 */
	private void updateControls() {
		for(Control control : controls.getList()) {
			if(control.state() == Control.State.PRESSED) {
				control.setState(Control.State.HELD);
			}else if(control.state() == Control.State.RELEASED) {
				control.setState(Control.State.INACTIVE);
			}
		}
	}
	
	public boolean press(String button) {
		ArrayList<Control> controlList = controls.getList();
		for(int i = 0; i < controlList.size(); i++) {
			Control control = controlList.get(i);
			if(control.button().equals(button)) {
				control.setState(Control.State.PRESSED);
				return true;
			}
		}
		return false;
	}
	
	public boolean release(String button) {
		ArrayList<Control> controlList = controls.getList();
		for(int i = 0; i < controlList.size(); i++) {
			Control control = controlList.get(i);
			if(control.button().equals(button)) {
				control.setState(Control.State.RELEASED);
				return true;
			}
		}
		return false;
	}
	
	public void setControl(String name, String button) {
		ArrayList<Control> controlList = controls.getList();
		for(int i = 0; i < controlList.size(); i++) {
			Control control = controlList.get(i);
			if(control.name().equals(name)) {
				control.updateButton(button);
			}
		}
	}
	//------------------------------------------------------------------------------------------------------------------------
	//Constructor
	public Game(int width, int height){
		viableRunes.addRune(JaggedLine.class, JaggedLine.getChecks());
		center = new DVector(width/2, height/2);
		levels.add(new Level(center));
		currentLevel = levels.get(0);
    	player = new Player(center.copy());
    	createControls();
	}
	/**
	 * Updates the position of the player in the level without changing the acceleration
	 * Mainly exists for testing purposes
	 * @param level
	 * @param drag
	 * 			boolean that states whether drag calculations are active
	 */
	public void update(Level level, boolean drag, Vector mousePosition) {
		
		//On left mouse release
		if(controls.getControl("LEFTMOUSE").state().equals(Control.State.RELEASED)) {
			logic.Math.removeDuplicateVectors(currentDrawing);
			this.runes.add(new InactiveRune(this.currentDrawing));
			if(runes.get(runes.size()-1) == null) {
				runes.remove(runes.size()-1);
			}
			this.currentDrawing = new ArrayList<Vector>();
		}
		
		//On left mouse press or hold
		if(controls.getControl("LEFTMOUSE").state().equals(Control.State.PRESSED) || controls.getControl("LEFTMOUSE").state().equals(Control.State.HELD)) {
			//copy mouse position
			this.currentDrawing.add(new DVector(mousePosition.x(), mousePosition.y()));
		}
		
		//handle player collisions with any walls
		
		
	    Vector position1 = player.absolutePosition();
	    if(drag) {
	    	player.updatePosition();
	    }else {
	    	player.updatePositionWithoutDrag();
	    }
	    Vector position2 = player.absolutePosition();
	    this.wallCollision(position1, position2, level);
	    
	    level.offset(player.position().mult(-1));
	    
	    this.updateControls();
	}
	
	public boolean wallCollision(Vector position1, Vector position2, Level level) {
		HashMap<Wall, Boolean> before = level.pointInFrontOfWallMap(position1);
		HashMap<Wall, Boolean> after = level.pointInFrontOfWallMap(position1);
		for(Wall wall : before.keySet()) {
	    	if(before.get(wall) != after.get(wall)) {//if passed over wall
	    		if(wall.inBounds(player.absolutePosition(), player.velocity())) {
	    			wall.handleCollisions(player, center);
	    		}
	    		return true;
	    	}
	    }
		return false;
	}
	
	


	public void update(Vector mousePosition) {
		
		for(Vector point : this.currentDrawing) {
			point.sub(offset);
		}
		//On left mouse release
		if(controls.getControl("LEFTMOUSE").state().equals(Control.State.RELEASED)) {
			logic.Math.removeDuplicateVectors(currentDrawing);
			this.runes.add(new InactiveRune(this.currentDrawing));
			if(runes.get(runes.size()-1) == null) {
				runes.remove(runes.size()-1);
			}
			this.currentDrawing = new ArrayList<Vector>();
		}
		
		//On left mouse press or hold
		if(controls.getControl("LEFTMOUSE").state().equals(Control.State.PRESSED) || controls.getControl("LEFTMOUSE").state().equals(Control.State.HELD)) {
			//copy mouse position
			this.currentDrawing.add(new DVector(mousePosition.x(), mousePosition.y()));
		}
		
		Vector acceleration = new DVector(0,0);
    	setAcceleration(acceleration);
    	
	    Vector position1 = player.absolutePosition();
	    player.updatePosition();
	    Vector position2 = player.absolutePosition();
	    this.wallCollision(position1, position2, this.currentLevel);
	    
	    this.offset = player.position().mult(-1);
	    currentLevel.offset(offset);
	    for(Rune rune: runes) {
	    	rune.offset(offset);
	    }
	    
	    for(Vector point : this.currentDrawing) {
	    	point.add(offset);
	    }
	    
	    this.updateControls();
	}
	
	public void setAcceleration(Vector acceleration) {
    	
		if(controls.getControl("UP").state().equals(Control.State.PRESSED) || controls.getControl("UP").state().equals(Control.State.HELD)) {
    		acceleration.add(new DVector(0,-1));
    	}
    	if(controls.getControl("LEFT").state().equals(Control.State.PRESSED) || controls.getControl("UP").state().equals(Control.State.HELD)) {
    		acceleration.add(new DVector(-1,0));
    	}
    	if(controls.getControl("DOWN").state().equals(Control.State.PRESSED) || controls.getControl("UP").state().equals(Control.State.HELD)) {
    		acceleration.add(new DVector(0,1));
    	}
    	if(controls.getControl("RIGHT").state().equals(Control.State.PRESSED) || controls.getControl("UP").state().equals(Control.State.HELD)) {
    		acceleration.add(new DVector(1,0));
    	}
    	player.setAcceleration(acceleration);
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
