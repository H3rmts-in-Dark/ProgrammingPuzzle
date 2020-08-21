package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class WorldLabel extends JLabel {

	public WorldLabel() {
		// draws the world
		/*
		 * Sollte das nicht eigentlich unten stehen? Und wofür braucht das überhaupt
		 * einen Konstruktor wenn da nix drinsteht? -Jan
		 */
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

}
