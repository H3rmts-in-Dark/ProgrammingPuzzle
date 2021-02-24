package world;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import Enums.Height;
import abstractclasses.CustomWindow;
import abstractclasses.Entity;
import abstractclasses.Tile;
import tiles.Computer;
import tiles.Schalter;


public class WorldWindow extends CustomWindow {

	private float zoom;
	private Point mouse;
	private World world;

	/**
	 * calles by World
	 * 
	 * @param world this
	 */
	public WorldWindow(World world) {
		super((world.getWidth() + 1) * DEFAULTIMAGEWIDHTHEIGHT,(world.getHeight() + 2) * DEFAULTIMAGEWIDHTHEIGHT,
			"World");
		this.world = world;
		this.mouse = new Point(0,0);
		zoom = 1f;
	}

	@Override
	public BufferedImage getImage() {
		if (world == null)
			return null;

		double widhei = (int) (DEFAULTIMAGEWIDHTHEIGHT * zoom);
		double widh = (int) Math.ceil(Math.min(world.getWidth() * widhei,getWidth()) / widhei);
		double heig = (int) Math.ceil(Math.min(world.getHeight() * widhei,getHeight() - widhei) / widhei);

		BufferedImage image = new BufferedImage((int) Math.ceil(widh * DEFAULTIMAGEWIDHTHEIGHT),
			(int) Math.ceil((heig + 1) * DEFAULTIMAGEWIDHTHEIGHT),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2 = image.createGraphics();

		for (int x = 0; x < widh; x++) {
			for (int y = 0; y < heig; y++) {
				g2.drawImage(world.getTile(x,y).getFloorImage(),x * DEFAULTIMAGEWIDHTHEIGHT,
					(y + 1) * DEFAULTIMAGEWIDHTHEIGHT,null);
			}
		}
		try {
			int x = getTile(new Point(mouse.x,mouse.y)).getPosition().x;
			int y = getTile(new Point(mouse.x,mouse.y)).getPosition().y + 1;

			g2.drawImage(Images.getImage("rsc/Gui/Cross/" + CROSS_COLOR + " middle.png"),(x * DEFAULTIMAGEWIDHTHEIGHT),
				(y * DEFAULTIMAGEWIDHTHEIGHT),null);

			g2.drawImage(Images.getImage("rsc/Gui/Cross/" + CROSS_COLOR + " horizontal.png"),
				(int) (mouse.x / zoom) - 82,(y * DEFAULTIMAGEWIDHTHEIGHT) - 2,null);
			g2.drawImage(Images.getImage("rsc/Gui/Cross/" + CROSS_COLOR + " side.png"),x * DEFAULTIMAGEWIDHTHEIGHT - 2,
				(int) (mouse.y / zoom) - 82,null);
			g2.drawImage(Images.getImage("rsc/Gui/Cross/" + CROSS_COLOR + " horizontal.png"),
				(int) (mouse.x / zoom) - 82,(y + 1) * DEFAULTIMAGEWIDHTHEIGHT - 2,null);
			g2.drawImage(Images.getImage("rsc/Gui/Cross/" + CROSS_COLOR + " side.png"),
				(x + 1) * DEFAULTIMAGEWIDHTHEIGHT - 2,(int) (mouse.y / zoom) - 82,null);
		} catch (NullPointerException e) {
		}

		for (int x = 0; x < widh; x++)
			for (int y = 0; y < heig; y++) {
				Tile temp = world.getTile(x,y);
				g2.drawImage(temp.getCableImage(),x * DEFAULTIMAGEWIDHTHEIGHT,(y + 1) * DEFAULTIMAGEWIDHTHEIGHT,null);
				g2.drawImage(temp.getObjektImage(),x * DEFAULTIMAGEWIDHTHEIGHT + temp.getRelativedrawX(),
					(y + 1) * DEFAULTIMAGEWIDHTHEIGHT + temp.getRelativedrawY(),null);
			}

		for (Entity entity : world.getEntitylist()) {
			g2.drawImage(entity.getImage(),entity.getPixelPosition().x + entity.getRelativedrawX(),
				entity.getPixelPosition().y + DEFAULTIMAGEWIDHTHEIGHT + entity.getRelativedrawY()
					- Height.getint(entity.getHeight()),
				null);
		}
		for (int x = 0; x < widh; x++)
			for (int y = 0; y < heig; y++)
				g2.drawImage(world.getTile(x,y).getEffectsImage(),x * DEFAULTIMAGEWIDHTHEIGHT,
					(y + 1) * DEFAULTIMAGEWIDHTHEIGHT,null);

		g2.dispose();

		return Images.bufferedImage(
			image.getScaledInstance((int) (image.getWidth() * zoom),(int) (image.getHeight() * zoom),Scaler));
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

	@SuppressWarnings("unused")
	@Override
	public void mousePressed(Point point) {
		Tile tile = getTile(point);
		if (tile == null)
			return;
		if (tile instanceof Schalter)
			tile.onInteract(null);
		else if (tile instanceof Computer)
			tile.onInteract(null);
		else {
			new DescriptionTileWindow(tile,new Point(point.x + getX(),point.y + getY()));
			if (world.getEntityAt(tile) != null)
				new DescriptionEntityWindow(world.getEntityAt(tile),new Point(point.x + getX(),point.y + getY()));
		}
	}

	@Override
	public void mouseWheelMoved(int direction) {
		setZoom(getZoom() + direction * 0.05f);
	}

	@Override
	public void mouseMoved(Point point) {
		mouse = point;
	}

	public Tile getTile(Point point) {
		try {
			return world.getTile((int) (point.x / (DEFAULTIMAGEWIDHTHEIGHT * zoom)),
				(int) (point.y / (DEFAULTIMAGEWIDHTHEIGHT * zoom)) - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

}
