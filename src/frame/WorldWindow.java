package frame;


import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;
import abstractclasses.Entity;
import abstractclasses.Task;
import abstractclasses.Tile;
import logic.Animations;
import tasks.WindowRepaintTask;
import tiles.Computer;
import world.Images;
import world.World;


public class WorldWindow extends CustomWindow {

	private float zoom;
	private World world;

	private BufferedImage drawimage;

	/**
	 * calles by World
	 * 
	 * @param world this
	 */
	public WorldWindow(World world) {
		super(world.getWidth() * TILEHEIGHTWIDHT + SIDEBARWIDTH * 2,
				(world.getHeight() + 1) * TILEHEIGHTWIDHT + TOPBARWIDTH,"World",-1);
		this.world = world;
		zoom = 1f;

		drawimage = new BufferedImage(world.getWidth() * TILEHEIGHTWIDHT * 5,world.getHeight() * TILEHEIGHTWIDHT * 5,
				BufferedImage.TYPE_INT_ARGB);
		
		new Task(30, -1) {
			
			@Override
			public void runCode() {
				renewImage(null);
			}
			
		};
	}

	@Override
	public Image draw() {
		return drawimage;
		
	}

	public void renewImage(Object tile_entity) {
		if (tile_entity instanceof Tile) {
			Tile tile = (Tile) tile_entity;
			BufferedImage newim = drawimage;
			Graphics2D g2 = newim.createGraphics();
			for (int y = tile.getPosition().x; y < world.getHeight() - 1; y++) {
				world.getTile(tile.getPosition().x,y).draw(g2);
			}
			g2.dispose();
			drawimage = newim;
		} else if (tile_entity instanceof Entity) {
			// TODO
		} else {
			BufferedImage image = new BufferedImage(world.getWidth() * TILEHEIGHTWIDHT,
					(world.getHeight() + 3) * TILEHEIGHTWIDHT,BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = image.createGraphics();

			for (int x = 0; x < world.getWidth(); x++) {
				for (int y = 0; y < world.getHeight(); y++) {
					world.getTile(x,y).draw(g2);
				}
			}

			for (Entity entity : world.getEntitys()) {
				entity.draw(g2);
			}

			g2.dispose();

			drawimage = Images.bufferedImage(
					image.getScaledInstance((int) (image.getWidth() * zoom),(int) (image.getHeight() * zoom),Scaler));
		}
		triggerFullRepaint();
	}

	/**
	 * rounds imput zoom to 2 decimal digits and assigns it to zoom
	 */
	public void setZoom(Float zoom) {
		Float test = (float) (Math.round(zoom * 100.0) / 100.0);
		if (test > MINZOOM && test < MAXZOOM)
			this.zoom = test;
		WindowRepaintTask.RepaintWindow(this);
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

		new WindowRepaintTask(3,this,false);
		new WindowRepaintTask(5,this,false);
	}

	public Tile getTile(Point point) {
		return world.getTile((int) (point.x / (TILEHEIGHTWIDHT * zoom)),(int) (point.y / (TILEHEIGHTWIDHT * zoom)) - 1);
	}

}
