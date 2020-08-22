package tasks;

import java.awt.Point;

import logic.Main;
import logic.Task;
import world.Tile;

public class ChangeTile extends Task {

	final private Tile newTile;
	final private Point point;

	protected ChangeTile(Double timeDifference,Tile tile,Point point) {
		super(timeDifference);
		newTile = tile;
		this.point = point;
	}

	@Override
	public void runCode() {
		Main.world.setTile((int)point.getX(),(int)point.getY(),newTile);
	}
}