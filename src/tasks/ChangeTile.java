package tasks;

import logic.Main;

public class ChangeTile extends logic.Task {

	private world.Tile newTile;

	protected ChangeTile(double timeDifference, world.Tile tile) {
		super(timeDifference);
		newTile = tile;
	}

	@Override
	public void runCode() {
		Main.world.setTile(newTile);
	}
}