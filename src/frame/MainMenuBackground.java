package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class MainMenuBackground extends JComponent {

	public MainMenuBackground() {
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
		try {
			g2.drawImage(ImageIO.read(new File("rsc/UI/BG.png")), 0, 0, getWidth(), getHeight(), null);
			g2.drawImage(ImageIO.read(new File("rsc/UI/Title.png")), 100, 20, 500, 120, null);
		} catch (IOException e) {
		}
	}

}
