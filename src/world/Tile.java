package world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import logic.Main;
import world.World.Layers;

public abstract class Tile {

	public final Boolean interactable;

	private Boolean passable;

	private HashMap<World.Layers, Imageholder> images;

	protected Tile(Boolean passable, Boolean interactable) {
		this.interactable = interactable;
		this.passable = passable;
		images = new HashMap<World.Layers, Imageholder>();
	}

	public boolean hasLayer(World.Layers layer) {
		return !(images.get(layer) == null);
	}

	public Boolean getInteractable() {
		return interactable;
	}

	public Boolean getPassable() {
		return passable;
	}

	public BufferedImage getImage(World.Layers layer) {
		return images.get(layer).getActualImg();
	}

	public void setImage(World.Layers layer, Imageholder imageholder) {
		this.images.put(layer, imageholder);
	}

	public void addImage(Map<World.Layers, Imageholder> map) {
		images.putAll(map);
	}

	public void setPassable(Boolean passable) {
		this.passable = passable;
	}

	public Point getPosition() {
		return Main.world.getTilePoint(this);
	}
	
	public Imageholder getImageholder(Layers layer) {
		return images.get(layer);
	}

	public abstract void onInteract(Entity entity);

	public abstract void onSteppedUpon(Entity entity);

	public void draw(Graphics2D g2, World.Layers layer) {
		if (hasLayer(layer)) {
			g2.drawImage(getImage(layer), (int) (getPosition().getX() * Main.tilewidth),
					(int) (getPosition().getY() * Main.tilewidth), Main.tilewidth, Main.tilewidth,null);

			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.CYAN);
			g2.drawRect((int) (getPosition().getX() * Main.tilewidth),
					(int) (getPosition().getY() * Main.tilewidth), Main.tilewidth, Main.tilewidth);
		}
	}

}