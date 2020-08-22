package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import logic.Main;
import world.World;

public class WorldLabel extends JLabel {

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		for (int x = 0; x < Main.world.getWidth(); x++) {
			for (int y = 0; y < Main.world.getHeight(); y++) {
				Main.world.getTile(x, y).draw(g2, World.Layers.Floor);
			}
		}

		for (int x = 0; x < Main.world.getWidth(); x++) {
			for (int y = 0; y < Main.world.getHeight(); y++) {
				Main.world.getTile(x, y).draw(g2, World.Layers.Floordecoration);
			}
		}

		for (int x = 0; x < Main.world.getWidth(); x++) {
			for (int y = 0; y < Main.world.getHeight(); y++) {
				Main.world.getTile(x, y).draw(g2, World.Layers.Objects);
			}
		}

		for (int i = 0; i < Main.world.getEntitylistLength(); i++) {
			Main.world.getEntity(i).draw(g2);
		}

		for (int x = 0; x < Main.world.getWidth(); x++) {
			for (int y = 0; y < Main.world.getHeight(); y++) {
				Main.world.getTile(x, y).draw(g2, World.Layers.Effects);
			}
		}
	}

}
