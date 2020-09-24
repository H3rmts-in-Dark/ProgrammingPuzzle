package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import logic.Main;
import world.World;

public class WorldLabel extends JComponent {

	private float zoom = 1;
	private boolean redrawRequired = false;

	/**
	 * Zeichnet die ganze Welt indem es Layer für Layer alle übereinanderzeichnet,
	 * diese Reihenfolge: Floor, Floordecoration, Objects, Entities, Effects
	 * 
	 * wird vom gameticker aufgerufen
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());

		g2.drawImage(paintWorld(getHeight(), getWidth()), 0, 0, (int) (getWidth() * zoom), (int) (getHeight() * zoom),
				null);

		redrawRequired = false;
	}

	/**
	 * Draws the entire world on one BufferedImage
	 * 
	 * @param height
	 * @param width
	 * @return
	 */
	private BufferedImage paintWorld(int height, int width) {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) bi.getGraphics();

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

		return bi;
	}

	/**
	 * sets redrawRequired to true
	 */
	public void needRedraw() {
		redrawRequired = true;
	}

	/**
	 * Returns wether a redraw is needed
	 * 
	 * @return
	 */
	public boolean needsRedraw() {
		return redrawRequired;
	}

	/**
	 * set Zoom to 1 * zoom
	 * 
	 * @param zoom
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	/**
	 * resets the zoom back to 1
	 */
	public void resetZoom() {
		this.zoom = 1;
	}
}
