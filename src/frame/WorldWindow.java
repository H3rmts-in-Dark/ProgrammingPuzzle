package frame;

import java.awt.Color;
import java.awt.Graphics;
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
	private Point click;

	/**
	 * calles by World
	 * 
	 * @param world this
	 */
	public WorldWindow(World world) {
		super(world.getWidth() * defaulttilewidth + sidebarwhidht * 2,
				world.getHeight() * defaulttilewidth + topbarwhidht, "World");
		this.world = world;
		zoom = 1f;
		click = new Point(0, 0);
	}

	@Override
	public BufferedImage draw() {
		if (world.isEmty()) {
			return null;
		}
		// creates worldimage
		BufferedImage image = getWorldimage();

		// rezise Image
		BufferedImage scaledimage = getBufferedImage(image.getScaledInstance((int) (image.getWidth() * zoom),
				(int) (image.getHeight() * zoom),Scaler));

		Graphics g = scaledimage.getGraphics();
		g.setColor(Color.GREEN);
		g.drawOval(click.x - 4, click.y - 4, 8, 8);
		g.dispose();

		return scaledimage;
	}

	/**
	 * Draws the entire world on one BufferedImage
	 * 
	 * @param height
	 * @param width
	 * @return
	 */
	private BufferedImage getWorldimage() {
		BufferedImage image = new BufferedImage(world.getWidth() * defaulttilewidth,
				world.getHeight() * defaulttilewidth, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Floor))
					g2.drawImage(temptile.getImage(Layers.Floor), temptile.getX() * defaulttilewidth,
							temptile.getY() * defaulttilewidth, null);
			}
		}

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Cable))
					g2.drawImage(temptile.getImage(Layers.Cable), temptile.getX() * defaulttilewidth,
							temptile.getY() * defaulttilewidth, null);
			}
		}

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Objects))
					g2.drawImage(temptile.getImage(Layers.Objects), temptile.getX() * defaulttilewidth,
							temptile.getY() * defaulttilewidth, null);
			}
		}

		for (int i = 0; i < world.getEntitylistLength(); i++) {
			Entity tempentity = world.getEntity(i);
			g2.drawImage(tempentity.getImage(), tempentity.getX() * defaulttilewidth,
					tempentity.getY() * defaulttilewidth, null);
		}

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Effects))
					g2.drawImage(temptile.getImage(Layers.Effects), temptile.getX() * defaulttilewidth,
							temptile.getY() * defaulttilewidth, null);
			}
		}
		g2.dispose();

		return image;
	}

	/**
	 * rounds imput zoom to 2 decimal digits and assigns it to zoom
	 */
	public void setZoom(Float zoom) {
		Float test = (float) (Math.round(zoom * 100.0) / 100.0);
		if (test > MinZoom && test < MaxZoom)
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
	public void Mousemoved(Point point) {
		click = point;
	}

	@Override
	public void clicked(Point point) {
		Tile tile = getTile(point);

		if (tile instanceof Computer)
			tile.triggerObjektAnimation(tile.getObjektanimation(1));
		else {
			new DescriptionWindow(tile,
					new Point((int) point.getX() + getX() + cornerwidht, (int) point.getY() + getY() + topbarwhidht));
		}
	}

	@Override
	public void mouseWheelMoved(Integer direction) {
		setZoom(getZoom() + direction * 0.2f);
	}

	public Tile getTile(Point point) {
		return world.getTile((int) (point.x / (defaulttilewidth * zoom)), (int) (point.y / (defaulttilewidth * zoom)));
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

		// Return the buffered image
		return image;
	}

}