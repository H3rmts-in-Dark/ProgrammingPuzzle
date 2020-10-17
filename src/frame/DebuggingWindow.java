package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.management.OperatingSystemMXBean;

import abstractclasses.CustomWindow;
import logic.Debugger;

public class DebuggingWindow extends CustomWindow {

	String fpsav, tpsav, executiontimeav, tasks, cpu;
	Set<Entry<String, Integer>> tasktypes;
	
	OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public DebuggingWindow() {
		super(300, 400, new Point(20, 20), "Debugging");
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
					fpsav = Double.toString(Debugger.getFPS());
					tpsav = Double.toString(Debugger.getTPS());
					executiontimeav = Double.toString(Debugger.getExecutionTime());
					tasks = Integer.toString(Debugger.getTaskSize());
					tasktypes = Debugger.getTasktypes();
					cpu = Double.toString(Math.round((os.getProcessCpuLoad() * 100) * 100.0) / 100.0);
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

		g2.drawString("FPS:" + fpsav + " / " + FPS, 10, height += 25);
		g2.drawString("TPS:" + tpsav + " / " + TPS, 10, height += 25);
		g2.drawString("TPSDelay:" + Integer.toString(1000 / TPS) + "ms", 10, height += 25);
		g2.drawString("FPSDelay:" + Integer.toString(1000 / FPS) + "ms", 10, height += 25);

		g2.drawString("CPU:" + cpu + "%", 10, height += 25);

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
