package frame;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;
import abstractclasses.Tile;
import logic.Animations;
import tiles.Computer;
import world.World;


public class WorldWindow extends CustomWindow {

	private Float zoom;
	private World world;

	/**
	 * calles by World
	 * 
	 * @param world this
	 */
	public WorldWindow(World world) {
		super(world.getWidth() * TILEHEIGHTWIDHT + SIDEBARWIDTH * 2,world.getHeight() * TILEHEIGHTWIDHT + TOPBARWIDTH,
				"World",(int) (TPS * 0.1));
		this.world = world;
		zoom = 1f;
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x,y);
				if (((int) (temptile.getDrawX(0) * zoom)) < getImageborders().getWidth()
						&& ((int) (temptile.getDrawY(0) * zoom)) < getImageborders().getHeight())
					temptile.updateimage();
				temptile.draw(g2,zoom);
			}
		}
		/*
		 * for (Entity entity : world.getEntitys()) { if (((int) (entity.getDrawX(0) * zoom)) <
		 * getImageborders().getWidth() && ((int) (entity.getDrawY(0) * zoom)) <
		 * getImageborders().getHeight()) { entity.draw(g2,zoom); } }
		 */
		g2.dispose();
		return image;
	}

	/**
	 * rounds imput zoom to 2 decimal digits and assigns it to zoom
	 */
	public void setZoom(Float zoom) {
		Float test = (float) (Math.round(zoom * 100.0) / 100.0);
		if (test > MINZOOM && test < MAXZOOM)
			this.zoom = test;
	}

	public Float getZoom() {
		return zoom;
	}

	/**
	 * resets the zoom back to 1
	 */
	public void resetZoom() {
		this.zoom = 1f;
	}

	@Override
	public void mousePressed(Point point) {
		Tile tile = getTile(point);
		if (tile instanceof Computer) {
			tile.triggerAnimation(Animations.interactanimation);
		} else {
			new DescriptionWindow(tile,
					new Point((int) point.getX() + getX() + CORNERWIDTH,(int) point.getY() + getY() + TOPBARWIDTH));
		}

	}

	@Override
	public void mouseWheelMoved(Integer direction) {
		setZoom(getZoom() + direction * 0.2f);
		triggerFullRepaint();
	}

	public Tile getTile(Point point) {
		return world.getTile((int) (point.x / (TILEHEIGHTWIDHT * zoom)),(int) (point.y / (TILEHEIGHTWIDHT * zoom)));
	}

}
