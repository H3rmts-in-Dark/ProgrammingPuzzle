package tasks;

import abstractclasses.Entity;
import abstractclasses.Task;
import abstractclasses.Tile;
import frame.Frame;
import world.World.Layers;

public class ChangeImageTask extends Task {

	final private Tile tile;
	final private Entity entity;
	final private Layers layer;

	public ChangeImageTask(Integer tickDifference, Tile tile, Layers layer, Integer loop) {
		super(tickDifference, loop);
		this.tile = tile;
		this.layer = layer;
		entity = null;
	}

	public ChangeImageTask(Integer tickDifference, Entity entity, Layers layer, Integer loop) {
		super(tickDifference, loop);
		this.entity = entity;
		this.layer = layer;
		tile = null;
	}

	public Tile getTile() {
		return tile;
	}

	public Entity getEntity() {
		return entity;
	}

	@Override
	public void runCode() {
		try {
			tile.nextImage(layer);
			entity.nextImage();
		} catch (NullPointerException e) {
		}
		Frame.repaint();
	}

	@Override
	public String extratoString() {
		return (tile != null ? (" /" + tile.getName()) : "") + (tile != null ? (" /" + tile.getName()) : "") + " /"
				+ layer;
	}
}