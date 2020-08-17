package frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class WorldLabel extends JLabel{
	
	public WorldLabel() {
		//draws the world
	}
	
	@Override
	public void paintComponents(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0,0,getWidth(),getHeight());
	}
	
}
