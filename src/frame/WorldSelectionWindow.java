package frame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;

public class WorldSelectionWindow extends CustomWindow {

	public WorldSelectionWindow() {
		super("Worldselection");
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
		return image;
	}

	@Override
	public void drawCursor(Graphics2D g2, Point point) {
	}
}