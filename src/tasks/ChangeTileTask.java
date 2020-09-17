package tasks;

import java.awt.Point;

import logic.Main;
import logic.Task;
import world.Tile;

public class ChangeTileTask extends Task {

	final private Tile newTile;
	final private Point point;
	
	public ChangeTileTask(Integer tickDifference,Tile tile,Point point) {
		super(tickDifference);
		newTile = tile;
		this.point = point;
	}

	@Override
	public void runCode() {
		Main.world.setTile((int) point.getX(), (int) point.getY(), newTile);
	}
}