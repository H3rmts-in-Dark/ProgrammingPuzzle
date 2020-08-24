package tasks;

import java.awt.Point;

import logic.Main;
import logic.Task;
import world.Tile;

public class ChangeTile extends Task {

	final private Tile newTile;
	final private Point point;

<<<<<<< HEAD
	public ChangeTile(Integer tickDifference,Tile tile,Point point) {
		super(tickDifference,false);
=======
	public ChangeTile(Double timeDifference, Tile tile, Point point) {
		super(timeDifference, false);
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
		newTile = tile;
		this.point = point;
	}

	@Override
	public void runCode() {
		Main.world.setTile((int) point.getX(), (int) point.getY(), newTile);
	}
}