package entitys;

import java.awt.Point;

import abstractclasses.Entity;
import abstractclasses.Tile;

public class Player extends Entity {

	protected Player(Boolean interactable, Point position) {
		super(interactable, position);
	}

	@Override
	public void loadAnimation() {
		// TODO loadAnimation muss noch eingefügt werden!!!
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
		// TODO Walk einfügen, es fehlt noch die Animation deswegen wird das auf später
		// verschoben
		if(player.getHeight() >= player.getTileInFront().getHeight()) {
			// TODO Muss die größer && gleich sein oder größer?
		}
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