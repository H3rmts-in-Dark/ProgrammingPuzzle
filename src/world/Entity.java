package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import logic.Main;

/**
 * Die Grundklasse aller Entities. Um als ein Entity klassifiziert zu werden,
 * muss das Objekt / Lebewesen sich bewegen können.
 */
public class Entity {

	public static enum Rotation {
		up, down, left, right
	}

	// TODO Entity Type einfügen (sobald Entitys existieren)
	public static enum EntityType {
	}

	private Rotation rotation;

	private Point position;

	public void setPosition(Point newPosition) {
		position = newPosition;
		Main.world.getTile(position.x, position.y).onSteppedUpon(this);
	}

	public void interact() {
		Tile tile = null;
		switch (rotation) {
		case down:
			if (position.y < Main.world.getHeight() - 1)
				tile = Main.world.getTile(position.x, position.y - 1);
			break;
		case left:
			if (position.x > 0)
				tile = Main.world.getTile(position.x - 1, position.y);
			break;
		case right:
			if (position.x < Main.world.getWidth() - 1)
				tile = Main.world.getTile(position.x + 1, position.y);
			break;
		case up:
			if (position.y > 0)
				tile = Main.world.getTile(position.x, position.y - 1);
			break;
		}
		if (tile.getInteractable())
			tile.onInteract(this);
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.drawOval((int) (getPosition().getX() * Main.tilewidth), (int) (getPosition().getY() * Main.tilewidth),
				Main.tilewidth, Main.tilewidth);
	}

}
