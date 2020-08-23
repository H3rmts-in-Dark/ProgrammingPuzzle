package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import logic.Main;

public class Entity {

	private Point position;

	public void setPosition(Point newPosition) {
		position = newPosition;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.drawOval((int)(getPosition().getX() * Main.tilewitdh),(int)(getPosition().getY() * Main.tilewitdh),Main.tilewitdh,Main.tilewitdh);
	}

}
