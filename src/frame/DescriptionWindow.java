package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;
import abstractclasses.Tile;

public class DescriptionWindow extends CustomWindow {

	private Tile tile;

	public DescriptionWindow(Tile tile, Point point) {
		super(200, 300, point, "Description of " + tile.getName());
		this.tile = tile;
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setFont(new Font("Default", Font.BOLD, 20));
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(tile.getDescription(), 10, 30);
		g2.dispose();
		return image;
	}

	@Override
	public void drawCursor(Graphics2D g2, Point point) {
	}
}
