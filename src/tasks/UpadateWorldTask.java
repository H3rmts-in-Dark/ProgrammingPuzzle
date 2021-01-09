package tasks;


import java.util.ArrayList;

import abstractclasses.Entity;
import abstractclasses.Task;
import abstractclasses.Tile;
import frame.WorldWindow;


public class UpadateWorldTask extends Task {

	final WorldWindow window;
	private Tile tile;
	private Entity entity;

	static ArrayList<Tile> torepaint = new ArrayList<>();

	private UpadateWorldTask(int tickDifference,WorldWindow window,Tile tile,Entity entity) {
		super(1,1);
		this.window = window;
		this.tile = tile;
		this.entity = entity;
	}

	@Override
	public void runCode() {
		if (tile != null)
			window.renewImage(tile);
		else
			window.renewImage(entity);
	}

	@Override
	public void onEnd() {
		torepaint.remove(tile);
	}

	public static void UpdateWorld(WorldWindow window,Tile tile) {
		boolean add = true;
		for (Tile itile : torepaint) {
			if (itile.getPosition().y == tile.getPosition().y) 
				add = false;
		}
		if (add) {
			new UpadateWorldTask(1,window,tile,null);
			torepaint.add(tile);
		} else {
			System.out.println("nadd");
		}
	}

}
