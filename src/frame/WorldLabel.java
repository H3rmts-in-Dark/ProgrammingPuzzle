package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class WorldLabel extends JLabel {

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// TODO Mergen, damit da hier der Code steht
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

}
