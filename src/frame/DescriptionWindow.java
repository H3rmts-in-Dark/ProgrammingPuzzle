package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import world.Tile;

public class DescriptionWindow extends CustomWindow {
	
	private Tile tile;
	
	public DescriptionWindow (Tile tile) {
		super(200,300,new Point(600,200),"Description of " + tile.getName());
		this.tile = tile;
	}

	@Override
	BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setFont(new Font("Default", Font.BOLD, 20));
		g2.setColor(Color.DARK_GRAY);
		g2.drawString(tile.getDescription(),30, image.getHeight() / 2);
		g2.dispose();
		return image;
	}
	
}
