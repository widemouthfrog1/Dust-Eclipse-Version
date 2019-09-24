package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import game.*;
import game.object.stationary.Wall;
import logic.*;


class MovementTests {

	@Test
	void horizontalMovement() {
		Level level = new Level(new ArrayList<Wall>());//create empty level
		Game game = new Game(0,0);
		game.player.setAcceleration(new DVector(1,0));
		for(int i = 0; i < 25; i++) {
			game.updatePosition(level, true);
		}
		assertTrue(105.1 > game.player.position().x());
		assertTrue(104.9 < game.player.position().x());
	}
	
	@Test
	void verticalMovement() {
		Level level = new Level(new ArrayList<Wall>());//create empty level
		Game game = new Game(0,0);
		game.player.setAcceleration(new DVector(0,1));
		for(int i = 0; i < 25; i++) {
			game.updatePosition(level, true);
		}
		assertTrue(105.1 > game.player.position().y());
		assertTrue(104.9 < game.player.position().y());
	}

}
