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

	private HashMap<Layers,Imageholder> images;

	protected Tile(Boolean passable, Boolean interactable) {
		this.interactable = interactable;
		this.passable = passable;
		images = new HashMap<Layers,Imageholder>();
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

	public BufferedImage getImage(Layers layer) {
		return images.get(layer).getActualImg();
	}

	public void setImage(Layers layer,Imageholder imageholder) {
		this.images.put(layer,imageholder);
	}
	
	public void addImage(Map<Layers,Imageholder> map) {
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

	public void draw(Graphics2D g2, Layers layer) {
		if (hasLayer(layer)) {
			g2.drawImage(getImage(layer),(int)(getPosition().getX() * Main.tilewitdh),(int)(getPosition().getY() * Main.tilewitdh),Main.tilewitdh,Main.tilewitdh,null);
			
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.CYAN);
			g2.drawRect((int)(getPosition().getX() * Main.tilewitdh),(int)(getPosition().getY() * Main.tilewitdh),Main.tilewitdh,Main.tilewitdh);
		}
	}

}