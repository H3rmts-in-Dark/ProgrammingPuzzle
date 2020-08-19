package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Mainmenubackground extends JLabel{
	
	public Mainmenubackground() {
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0,0,getWidth(),getHeight());
		try {g2.drawImage(ImageIO.read(new File("BG" + ".png")),0,0,getWidth(),getHeight(),null);
		} catch (IOException e) {}
		try {g2.drawImage(ImageIO.read(new File("Title" + ".png")),50,20,500,120,null);
		} catch (IOException e) {}
	}

}
