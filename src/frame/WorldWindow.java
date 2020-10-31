package frame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;
import abstractclasses.Entity;
import abstractclasses.Tile;
import logic.Layers;
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
		super(world.getWidth() * TILEHEIGHTWIDHT + SIDEBARWIDTH * 2, world.getHeight() * TILEHEIGHTWIDHT + TOPBARWIDTH,
				"World");
		this.world = world;
		zoom = 1f;
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setStroke(new BasicStroke(2));

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Floor))
					g2.drawImage(
							temptile.getImage(Layers.Floor).getScaledInstance((int) (TILEHEIGHTWIDHT * zoom),
									(int) (TILEHEIGHTWIDHT * zoom), Scaler),
							(int) (temptile.getDrawX(0) * zoom), (int) (temptile.getDrawY(0) * zoom), null);
				if (temptile.hasLayer(Layers.Cable))
					g2.drawImage(
							temptile.getImage(Layers.Cable).getScaledInstance((int) (TILEHEIGHTWIDHT * zoom),
									(int) (TILEHEIGHTWIDHT * zoom), Scaler),
							(int) (temptile.getDrawX(0) * zoom), (int) (temptile.getDrawY(0) * zoom), null);
				if (temptile.hasLayer(Layers.Objects))
					g2.drawImage(
							temptile.getObjektImage().getScaledInstance((int) (TILEHEIGHTWIDHT * zoom),
									(int) (TILEHEIGHTWIDHT * zoom), Scaler),
							(int) (temptile.getDrawX(temptile.getRelativedrawX()) * zoom),
							(int) (temptile.getDrawY(temptile.getRelativedrawY()) * zoom), null);
			}
		}
		for (Entity entity : world.getEntitys()) {
			g2.drawImage(
					entity.getImage().getScaledInstance((int) (TILEHEIGHTWIDHT * zoom), (int) (TILEHEIGHTWIDHT * zoom),
							Scaler),
					(int) (entity.getDrawX(entity.getRelativedrawX()) * zoom),
					(int) (entity.getDrawY(entity.getRelativedrawY()) * zoom), null);
		}
		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x, y);
				if (temptile.hasLayer(Layers.Effects))
					g2.drawImage(
							temptile.getImage(Layers.Effects).getScaledInstance((int) (TILEHEIGHTWIDHT * zoom),
									(int) (TILEHEIGHTWIDHT * zoom), Scaler),
							(int) (temptile.getDrawX(0) * zoom), (int) (temptile.getDrawY(0) * zoom), null);
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
		} else {
			new DescriptionWindow(tile,
					new Point((int) point.getX() + getX() + CORNERWIDTH, (int) point.getY() + getY() + TOPBARWIDTH));
		}

	}

	@Override
	public void mouseWheelMoved(Integer direction) {
		setZoom(getZoom() + direction * 0.2f);
		triggerFullRepaint();
	}

	public Tile getTile(Point point) {
		return world.getTile((int) (point.x / (TILEHEIGHTWIDHT * zoom)), (int) (point.y / (TILEHEIGHTWIDHT * zoom)));
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