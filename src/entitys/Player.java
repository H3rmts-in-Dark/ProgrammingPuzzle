package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Rotations;

public class Player extends Entity {

	public Player(Point position, Rotations r) {
		super(false, position, 0, 0, r);
	}

	@Override
	public void loadAnimation() {
		// TODO loadAnimation muss noch eingef�gt werden!!!
	}

	@Override
	public void onInteract(Entity entity) {
	}

}

class PlayerCommand {

	/**
	 * Der Spieler, der mit diesem playerCommand gesteuert wird.
	 */
	private Player player;

	public PlayerCommand(Player player) {
		this.player = player;
	}

	public void interact(Entity entity) {
		player.onInteract(entity);
	}

	public void walk() {
		player.move();
	}

	public Point getPosition() {
		return player.getPosition();
	}

	public Integer getX() {
		return player.getPosition().x;
	}

	public Integer getY() {
		return player.getPosition().x;
	}

	public Tile getTile() {
		return player.getTileInFront();
	}

	public Integer getHeight() {
		return player.getHeight();
	}
}