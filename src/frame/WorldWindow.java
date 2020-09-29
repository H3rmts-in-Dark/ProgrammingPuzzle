package frame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import world.Entity;
import world.Tile;
import world.World;
import world.World.Layers;

public class WorldWindow extends CustomWindow {

	private Float zoom = 1f;
	private static Integer tilewidth = 64;
	
	private World world;
	
	/**
	 * calles by World
	 * @param world this
	 */
	public WorldWindow(World world) {
		super(world.getWidth()*tilewidth+18,world.getHeight()*tilewidth+30,"World");
		this.world = world;
	}
	
	@Override
	BufferedImage draw() {
		if (world.isEmty()) {
			return null;
		}
		// creates worldimage 
		BufferedImage image = getWorldimage(); 
		
		// rezise Image
		BufferedImage scaledimage = getBufferedImage(image.getScaledInstance((int) (image.getWidth() * zoom),
				(int) (image.getHeight() * zoom),Image.SCALE_SMOOTH));
		
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
		BufferedImage image = new BufferedImage(world.getWidth()*tilewidth,world.getHeight()*tilewidth,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x,y);
				if (temptile.hasLayer(Layers.Floor)) 
					g2.drawImage(temptile.getImage(Layers.Floor), temptile.getX() * tilewidth,temptile.getY() * tilewidth,null);
			}
		}

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x,y);
				if (temptile.hasLayer(Layers.Floordecoration)) 
					g2.drawImage(temptile.getImage(Layers.Floordecoration), temptile.getX() * tilewidth,temptile.getY() * tilewidth,null);
			}
		}

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x,y);
				if (temptile.hasLayer(Layers.Objects)) 
					g2.drawImage(temptile.getImage(Layers.Objects), temptile.getX() * tilewidth,temptile.getY() * tilewidth,null);
			}
		}

		for (int i = 0; i < world.getEntitylistLength(); i++) {
			Entity tempentity = world.getEntity(i);
			g2.drawImage(tempentity.getImage(),tempentity.getX() * tilewidth,tempentity.getY() * tilewidth,null);
		}

		for (int x = 0; x < world.getWidth(); x++) {
			for (int y = 0; y < world.getHeight(); y++) {
				Tile temptile = world.getTile(x,y);
				if (temptile.hasLayer(Layers.Effects)) 
					g2.drawImage(temptile.getImage(Layers.Effects), temptile.getX() * tilewidth,temptile.getY() * tilewidth,null);
			}
		}
		return image;
	}

	
	/**
	 * rounds imput zoom to 2 decimal digits
	 * and assigns it to zoom
	 */
	public void setZoom(Float zoom) {
		this.zoom = (float) (Math.round(zoom * 100.0) / 100.0);
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
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage getBufferedImage(Image img)
	{
	    BufferedImage image = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D g2 = image.createGraphics();
	    g2.drawImage(img,0,0,null);
	    g2.dispose();

	    // Return the buffered image
	    return image;
	}
}

