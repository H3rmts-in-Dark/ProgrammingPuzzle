package frame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;

public class WorldSelectionWindow extends CustomWindow {

	private static final int SCALEFACTOR = 50;
	private static final String title = "Worldselection";

	public WorldSelectionWindow() {
		super(SCALEFACTOR * 8, SCALEFACTOR * 9, new Point(0, 0), title);
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());

		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				// draw((SCALEFACTOR / 2) * (1 + 2 * x), (SCALEFACTOR / 2) * (1 + 2 * y),
				// SCALEFACTOR, SCALEFACTOR)
				// nicht richtige funktion, aber die wird dann in etwa benutzt
			}
		}
		return image;
	}

	// um die ausgewählte welt herauszufinden:
	// for(int y = 0; y < 5; y++) {
	// 	for(int x = 0; x < 5; x++) {
	// 		new Rectangle((SCALEFACTOR / 2) * (1 + 2 * x), (SCALEFACTOR / 2) * (1 + 2 * y),
	// 			SCALEFACTOR, SCALEFACTOR).contains(<Mouseposition>)
	// 	}
	// }

	@Override
	public void drawCursor(Graphics2D g2, Point point) {
	}
}