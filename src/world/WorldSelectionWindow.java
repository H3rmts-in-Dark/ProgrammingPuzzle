package world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;

public class WorldSelectionWindow extends CustomWindow {

	public WorldSelectionWindow() {
		super("Worldselection");
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
		return image;
	}
}