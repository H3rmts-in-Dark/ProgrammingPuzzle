package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import logic.Main;

public abstract class Entity {

	private Point position;
	private Point relativedrawing = new Point(0, 0);

	public void setPosition(Point newPosition) {
		position = newPosition;
	}

	public Point getPosition() {
		return position;
	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.drawOval((int) (getPosition().getX() * Main.tilewidth + relativedrawing.getX()),
				(int) (getPosition().getY() * Main.tilewidth + relativedrawing.getY()), Main.tilewidth, Main.tilewidth);
	}

	public Point getRelativeDrawing() {
		return relativedrawing;
	}

	public void setRelativeDrawing(Point p) {
		relativedrawing = p;
	}

	public abstract void onTick();

}
