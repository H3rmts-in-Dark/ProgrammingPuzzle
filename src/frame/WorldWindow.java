package frame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;
import abstractclasses.Entity;
import abstractclasses.Tile;
import tiles.Computer;
import world.World;
import world.World.Layers;

public class WorldWindow extends CustomWindow {

	private Float zoom;
	private World world;

	/**
	 * calles by World
	 * 
	 * @param world this
	 */
	public WorldWindow(World world) {
		super(world.getWidth() * DEFAULTTILEWIDTH + SIDEBARWIDTH * 2,
				world.getHeight() * DEFAULTTILEWIDTH + TOPBARWIDTH, "World");
		this.world = world;
		zoom = 1f;
	}

	@Override
	public BufferedImage draw() {

		// creates worldimage
		BufferedImage image = getWorldImage();

		// resize Image
		BufferedImage scaledimage;
		scaledimage = (getZoom() > 3.0)
				? getBufferedImage(image.getScaledInstance((int) (image.getWidth() * zoom),
						(int) (image.getHeight() * zoom), Image.SCALE_FAST))
				: getBufferedImage(image.getScaledInstance((int) (image.getWidth() * zoom),
						(int) (image.getHeight() * zoom), Image.SCALE_SMOOTH));
		return scaledimage;
	}

	/**
	 * Draws the entire world on one BufferedImage
	 * 
	 * @param height
	 * @param width
	 * @return
	 */
	private BufferedImage getWorldImage() {
		BufferedImage image = new BufferedImage(world.getWidth() * DEFAULTTILEWIDTH,
				world.getHeight() * DEFAULTTILEWIDTH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Floor))
					g2.drawImage(temptile.getImage(Layers.Floor), temptile.getX() * DEFAULTTILEWIDTH,
							temptile.getY() * DEFAULTTILEWIDTH, null);
			}
		}

		for (int x = 0; x < world.getWidth(); x++)
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Cable))
					g2.drawImage(temptile.getImage(Layers.Cable), temptile.getX() * DEFAULTTILEWIDTH,
							temptile.getY() * DEFAULTTILEWIDTH, null);
			}

		for (int x = 0; x < world.getWidth(); x++)
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Objects))
					g2.drawImage(temptile.getObjektImage(), temptile.getX() * DEFAULTTILEWIDTH,
							temptile.getY() * DEFAULTTILEWIDTH, null);
			}

		for (int i = 0; i < world.getEntitylistLength(); i++) {
			Entity tempentity = world.getEntity(i);
			g2.drawImage(tempentity.getImage(), tempentity.getPixelX() * DEFAULTTILEWIDTH,
					tempentity.getPixelY(), null);
		}

		for (int x = 0; x < world.getWidth(); x++)
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Effects))
					g2.drawImage(temptile.getImage(Layers.Effects), temptile.getX() * DEFAULTTILEWIDTH,
							temptile.getY() * DEFAULTTILEWIDTH, null);
			}

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
			tile.triggerAnimation(INTERACTANIMATION);
		}
		/*
		 * else { new DescriptionWindow(tile, new Point((int) point.getX() + getX() +
		 * cornerwidht, (int) point.getY() + getY() + topbarwhidht)); }
		 */
	}

	@Override
	public void mouseWheelMoved(Integer direction) {
		setZoom(getZoom() + direction * 0.2f);
		setrepaintfull();
	}

	public Tile getTile(Point point) {
		return world.getTile((int) (point.x / (DEFAULTTILEWIDTH * zoom)), (int) (point.y / (DEFAULTTILEWIDTH * zoom)));
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage getBufferedImage(Image img) {
		BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D g2 = image.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();

		return image;
	}

	@Override
	public void drawCursor(Graphics2D g, Point point) {
		g.setColor(Color.GREEN);
		g.drawOval(point.x - 4, point.y - 4, 8, 8);
	}
}