package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.*;
import game.object.entity.Player;
import game.object.stationary.StandardWall;
import game.object.stationary.Wall;
import logic.*;


class WallCollisionTests {

	@Test
	void noVerticalMovementInVertialWallCollisions() {
		Vector center = new DVector(0,0);
		Level level = new Level(new StandardWall(10, -10, 10, 10));
		Game game = new Game((int)center.x(),(int)center.y());
		game.player.setAcceleration(new DVector(1,0));
		for(int i = 0; i < 10; i++) {
			game.player.setAcceleration(new DVector(1,0));
			game.update(level, true);
		}
		
		assertEquals(0, game.player.position().y());
	}
	@Test
	void noVerticalMovementInVertialWallCollisionsDifferentCenter() {
		Vector center = new DVector(10,15);
		Level level = new Level(new StandardWall(10, -10, 10, 10));
		Game game = new Game((int)center.x(),(int)center.y());
		game.player.setAcceleration(new DVector(1,0));
		for(int i = 0; i < 10; i++) {
			game.player.setAcceleration(new DVector(1,0));
			game.update(level, true);
		}
		
		assertEquals(0, game.player.position().y());
	}
	@Test
	void horizonalMovementInVertialWallCollisions() {
		Vector center = new DVector(0,0);
		Level level = new Level(new StandardWall(10, -10, 10, 10));
		Game game = new Game((int)center.x(),(int)center.y());
		
		for(int i = 0; i < 20; i++) {
			game.player.setAcceleration(new DVector(1,0));
			game.update(level, true);
			if(i > 10) {
				assertEquals(9.9, game.player.position().x(),1e-3);
			}
		}
	}
	
	@Test
	void collisionIntersectionVerticalWall() {
		Vector center = new DVector(0,0);
		Wall wall = new StandardWall(10, -10, 10, 10);
		Player player = new Player(center.copy());
		assertEquals(new DVector(10, 0),wall.getIntersection(player.position(), new DVector(20, 0)));
	}
	
	@Test
	void normalFromVerticalWall() {
		Vector center = new DVector(0,0);
		Wall wall = new StandardWall(10, -10, 10, 10);
		Player player = new Player(center.copy());
		assertEquals(new DVector(-1, 0), wall.getNormal(player));
	}
	
	@Test
	void forceFromVerticalWall() {
		Vector center = new DVector(0,0);
		Wall wall = new StandardWall(10, -10, 10, 10);
		Player player = new Player(center.copy());
		player.setVeclocity(new DVector(20,0));
		wall.handleCollisions(player, new DVector(0,0));
		assertEquals(new DVector(-20, 0).x(), player.acceleration().x(), 1e-5);
	}
	
	@Test
	void forceFromVerticalWallDifferentCenter() {
		Vector center = new DVector(10,15);
		Wall wall = new StandardWall(10, -10, 10, 10);
		Player player = new Player(center.copy());
		player.setVeclocity(new DVector(20,0));
		wall.handleCollisions(player, new DVector(0,0));
		assertEquals(new DVector(-20, 0).x(), player.acceleration().x(), 1e-5);
	}
	
	@Test
	void collisionIntersectionOnVerticalWall() {
		Vector center = new DVector(0,0);
		Wall wall = new StandardWall(10, -10, 10, 10);
		Player player = new Player(center.copy());
		player.setPosition(new DVector(10, 0));
		assertEquals(new DVector(10, 0),wall.getIntersection(player.position(), new DVector(20, 0)));
	}
	
	@Test
	void collisionIntersectionDiagonalWall() {
		Vector center = new DVector(0,0);
		Wall wall = new StandardWall(5, -12, 10, 8);
		Player player = new Player(center.copy());
		assertEquals(new DVector(8, 0),wall.getIntersection(player.position(), new DVector(20, 0)));
	}
	
	

}
