package world;

import java.awt.Point;

public abstract class Entity {

	/**
	 * Die position, an die sich dieses Entity befindet
	 */
	private Point position;

	/**
	 * setzt die Position des Entitys
	 * 
	 * @param newPosition
	 */
	public void setPosition(Point newPosition) {
		position = newPosition;
	}

	/**
	 * gibt die Position des Entitys zurück
	 * 
	 * @return Point
	 */
	public Point getPosition() {
		return position;
	}

}
