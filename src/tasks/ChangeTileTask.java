package tasks;

import java.awt.Point;

import logic.Task;
import world.Tile;
import world.World;

public class ChangeTileTask extends Task {

	final private Tile newTile;
	final private Point point;
	private final World world;
	
	public ChangeTileTask(Integer tickDifference,Tile tile,Point point,World world) {
		super(tickDifference);
		this.newTile = tile;
		this.point = point;
		this.world = world;
	}

	@Override
	public void runCode() {
		world.setTile((int) point.getX(), (int) point.getY(), newTile);
	}
}