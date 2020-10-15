package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.management.OperatingSystemMXBean;

import abstractclasses.CustomWindow;
import logic.Debuger;

public class DebugingWindow extends CustomWindow {

	String fpsav, tpsav, executiontimeav, tasks, cpu;
	Set<Entry<String, Integer>> tasktypes;
    OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public DebugingWindow() {
		super(300, 400, new Point(20, 20), "Debunging");
		fpsav = "";
		tpsav = "";
		executiontimeav = "";
		tasks = "";

		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
					fpsav = Double.toString(Debuger.getFps());
					tpsav = Double.toString(Debuger.getTps());
					executiontimeav = Double.toString(Debuger.getExecutiontime());
					tasks = Integer.toString(Debuger.getTaskSize());
					tasktypes = Debuger.getTasktypes();
					cpu = Double.toString(Math. round((os.getProcessCpuLoad()*100) * 100.0) / 100.0);
					setrepaintfull();
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

		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.setColor(Color.BLACK);

		Integer height = 0;

		g2.drawString("Fps:" + fpsav + " / " + fps, 10, height += 25);
		g2.drawString("Tps:" + tpsav + " / " + tps, 10, height += 25);
		g2.drawString("Tpsdelay:" + Integer.toString(1000 / tps) + "ms", 10, height += 25);
		g2.drawString("Fpsdelay:" + Integer.toString(1000 / fps) + "ms", 10, height += 25);
	
		g2.drawString("Cpu:" + cpu + "%", 10, height += 25);
		
		g2.drawString("Executiontilme:" + executiontimeav + "ms", 10, height += 25);
		g2.drawString("Tasks:" + tasks, 10, height += 25);

		synchronized (tasktypes) {
			for (Entry<String, Integer> entry : tasktypes) {
				g2.drawString(entry.getKey() + ":" + entry.getValue(), 10, height += 25);
			}
		}

		g2.dispose();
		return image;
	}

	@Override
	public void drawCursor(Graphics2D g2, Point point) {
		
	}
}
