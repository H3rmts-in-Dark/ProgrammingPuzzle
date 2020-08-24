package world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

	public final Boolean interactable;

	private Boolean passable;

	private HashMap<world.World.Layers, Imageholder> images;

	protected Tile(Boolean passable, Boolean interactable) {
		this.interactable = interactable;
		this.passable = passable;
		images = new HashMap<world.World.Layers, Imageholder>();
	}

	public boolean hasLayer(world.World.Layers layer) {
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
		return logic.Main.world.getTilePoint(this);
	}
<<<<<<< HEAD
	
	public Imageholder getImageholder(Layers layer) {
		return images.get(layer);
	}
=======

	/**
	 * Wenn der Spieler mit dem Tile zu interagiert wird diese Methode aufgerufen
	 */
	public abstract void onInteract();

	/**
	 * Wird ausgelöst, wenn der Spieler das Tile betritt
	 */
	public abstract void onSteppedUpon();
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6

	public void draw(Graphics2D g2, World.Layers layer) {
		if (hasLayer(layer)) {
			g2.drawImage(getImage(layer), (int) (getPosition().getX() * logic.Main.tilewidth),
					(int) (getPosition().getY() * logic.Main.tilewidth), logic.Main.tilewidth, logic.Main.tilewidth,
					null);

			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.CYAN);
			g2.drawRect((int) (getPosition().getX() * logic.Main.tilewidth),
					(int) (getPosition().getY() * logic.Main.tilewidth), logic.Main.tilewidth, logic.Main.tilewidth);
		}
	}

}