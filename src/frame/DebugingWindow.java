package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import abstractclasses.CustomWindow;
import logic.Debuger;

public class DebugingWindow extends CustomWindow {

	String fpsav;
	String tpsav;
	String executiontimeav;

	public DebugingWindow() {
		super(200, 400, new Point(20, 20), "Debunging");
		fpsav = "";
		tpsav = "";
		executiontimeav = "";

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
					fpsav = Double.toString(Debuger.getFps());
					tpsav = Double.toString(Debuger.getTps());
					executiontimeav = Integer.toString(Debuger.getExecutiontime());
				}
			}
		}.start();
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());

		g2.setFont(new Font("Arial", Font.BOLD, 18));
		g2.setColor(Color.BLACK);
		g2.drawString("Fps:", 10, 20);
		g2.drawString(fpsav, 50, 20);

		g2.drawString("Tps:", 10, 50);
		g2.drawString(tpsav, 50, 50);

		g2.drawString("Executiontilme:", 10, 80);
		g2.drawString(executiontimeav, 10, 100);

		g2.drawString("Tpsdelay:", 10, 130);
		g2.drawString(Integer.toString(1000 / tps), 10, 150);

		g2.dispose();
		return image;
	}

}
