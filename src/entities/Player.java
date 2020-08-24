package entities;

import java.awt.Point;

import logic.Main;
import world.Tile;

public class Player extends world.Entity {

	public enum DirectionOfView {
		right, left, up, down
	}

	public DirectionOfView direction;
	public final Point normalRelativeDrawing = new Point(0, 0);
	private final double pixelPerTick = Main.tilewidth / 8;

	public void walk() {
		Tile t = getTileInFront();
		if (t.getPassable()) {
			switch (direction) {
			case right:
				setPosition(new Point((int) getPosition().getX() + 1, (int) getPosition().getY()));
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX() - Main.tilewidth),
						(int) (getRelativeDrawing().getY())));
				t.onSteppedUpon();
				break;
			case left:
				setPosition(new Point((int) getPosition().getX() - 1, (int) getPosition().getY()));
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX() + Main.tilewidth),
						(int) (getRelativeDrawing().getY())));
				t.onSteppedUpon();
				break;
			case up:
				setPosition(new Point((int) getPosition().getX(), (int) (getPosition().getY() - 1)));
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX()),
						(int) (getRelativeDrawing().getY() + Main.tilewidth)));
				t.onSteppedUpon();
				break;
			case down:
				setPosition(new Point((int) getPosition().getX(), (int) (getPosition().getY() + 1)));
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX()),
						(int) (getRelativeDrawing().getY() - Main.tilewidth)));
				t.onSteppedUpon();
				break;
			}
		}
	}

	public void interact() {
		getTileInFront().onInteract();
	}

	@Override
	public void onTick() {
		// Das Macht das Gehen möglich, es wird immer um 1/8 von Main.tilewidth
		// verschoben sodass das Gehen von einem Tile zum nächsten genau 0.8 Sekunden
		// dauert
		if ((getRelativeDrawing().getX() - normalRelativeDrawing.getX()) != 0) {
			if ((getRelativeDrawing().getX() - normalRelativeDrawing.getX()) > pixelPerTick) {
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX() - pixelPerTick),
						(int) (getRelativeDrawing().getY())));
			} else if ((getRelativeDrawing().getX() - normalRelativeDrawing.getX()) < pixelPerTick) {
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX() + pixelPerTick),
						(int) (getRelativeDrawing().getY())));
			}
		}
		if ((getRelativeDrawing().getY() - normalRelativeDrawing.getY()) != 0) {
			if ((getRelativeDrawing().getY() - normalRelativeDrawing.getY()) > pixelPerTick) {
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX()),
						(int) (getRelativeDrawing().getY() - pixelPerTick)));
			} else if ((getRelativeDrawing().getY() - normalRelativeDrawing.getY()) < pixelPerTick) {
				setRelativeDrawing(new Point((int) (getRelativeDrawing().getX()),
						(int) (getRelativeDrawing().getY() + pixelPerTick)));
			}
		}
	}

	private Tile getTileInFront() {
		switch (direction) {
		case down:
			return logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() + 1);
		case left:
			return logic.Main.world.getTile((int) getPosition().getX() - 1, (int) getPosition().getY());
		case right:
			return logic.Main.world.getTile((int) getPosition().getX() + 1, (int) getPosition().getY());
		case up:
			return logic.Main.world.getTile((int) getPosition().getX(), (int) getPosition().getY() - 1);
		}
		return null;
	}

}
