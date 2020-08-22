package tasks;

import java.awt.Point;

import logic.Main;
import logic.Task;
import tiles.Tile;

public class ChangeTile extends Task {

	private Tile newTile;
	private Point point;

	protected ChangeTile(Double timeDifference,Tile tile,Point pos) {
		super(timeDifference);
		newTile = tile;
	}

	@Override
	public void runCode() {
		Main.world.setTile((int)point.getX(),(int)point.getY(),newTile);
	}
}