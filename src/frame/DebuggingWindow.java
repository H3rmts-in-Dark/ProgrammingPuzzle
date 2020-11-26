package frame;

import java.awt.BasicStroke;
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
import abstractclasses.Task;
import logic.Debugger;
import logic.MainControl;

public class DebuggingWindow extends CustomWindow {

	String fpsav, tpsav, executiontimeav, tasks, cpu, gametick;
	Set<Entry<String, Integer>> tasktypes;

	OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public DebuggingWindow() {
		super(300, 400, new Point(20, 20), "Debugging", (int) (TPS * 0.2));
		fpsav = "";
		tpsav = "";
		executiontimeav = "";
		tasks = "";
	}

	private void loadValues() {
		fpsav = Double.toString(Debugger.getFPS());
		tpsav = Double.toString(Debugger.getTPS());
		executiontimeav = Double.toString(Debugger.getExecutionTime());
		tasks = Integer.toString(Debugger.getTaskSize());
		tasktypes = Debugger.getTasktypes();
		cpu = Double.toString(Math.round((os.getProcessCpuLoad() * 100) * 100.0) / 100.0);
		gametick = Long.toString(MainControl.getGameTicker().getTick());
	}

	@Override
	public BufferedImage draw() {
		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		loadValues();
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());

		g2.setFont(new Font("Arial", Font.BOLD, 16));
		g2.setColor(Color.BLACK);

		Integer x = 0;

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));

		g2.drawRect(x += 10, 10, 20, 20);
		g2.drawLine(x + 4, 13, x + 16, 13);
		g2.drawLine(x + 4, 27, x + 16, 27);
		g2.drawLine(x + 4, 14, x + 8, 20);
		g2.drawLine(x + 16, 14, x + 12, 20);
		g2.drawLine(x + 8, 20, x + 4, 26);
		g2.drawLine(x + 12, 20, x + 16, 26);

		g2.setColor(Color.RED);
		g2.drawLine(x += 30, 10, x + 20, 30);
		g2.drawLine(x + 20, 10, x, 30);

		g2.setColor(Color.BLACK);
		g2.drawRect(x, 10, 20, 20);
		g2.setColor(new Color(0, 0, 0, 130));
		g2.fillOval(x + 3, 13, 14, 14);
		g2.setColor(Color.BLACK);
		g2.drawOval(x + 3, 13, 14, 14);
		g2.drawLine(x + 10, 20, x + 12, 16);
		g2.drawLine(x + 10, 20, x + 6, 22);

		g2.setColor(Color.BLACK);
		g2.drawRect(x += 30, 10, 20, 20);
		g2.setColor(new Color(0, 0, 0, 130));
		g2.fillRect(x + 2, 12, 15, 15);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(x + 2, 12, 15, 15);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(x + 5, 15, x + 15, 15);
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(x + 4, 17, x + 8, 17);
		g2.drawLine(x + 10, 17, x + 14, 17);
		g2.drawLine(x + 4, 19, x + 10, 19);
		g2.drawLine(x + 4, 21, x + 14, 21);
		g2.drawLine(x + 4, 23, x + 8, 23);
		g2.drawLine(x + 10, 23, x + 14, 23);
		g2.drawLine(x + 4, 25, x + 10, 25);
		g2.drawLine(x + 12, 25, x + 14, 25);

		Integer height = 30;

		g2.drawString("FPS:" + fpsav + " / " + FPS, 10, height += 25);
		g2.drawString("TPS:" + tpsav + " / " + TPS, 10, height += 25);
		g2.drawString("TPSDelay:" + Integer.toString(1000 / TPS) + "ms", 10, height += 25);
		g2.drawString("FPSDelay:" + Integer.toString(1000 / FPS) + "ms", 10, height += 25);

		g2.drawString("CPU:" + cpu + "%", 10, height += 25);

		g2.drawString("Executiontilme:" + executiontimeav + "ms", 10, height += 25);
		g2.drawString("Tasks:" + tasks, 10, height += 25);

		for (Entry<String, Integer> entry : tasktypes) {
			g2.drawString(entry.getKey() + ":" + entry.getValue(), 10, height += 25);
		}

		g2.drawString("Gametick:" + gametick, 10, height += 25);

		g2.dispose();
		return image;
	}

	@Override
	public void drawCursor(Graphics2D g2, Point point) {
	}

	@Override
	public void mousePressed(Point point) {
		if (point.x > 10 && point.x < 30 && point.y > 10 && point.y < 30) {
			new Task(1, -1) {

				@Override
				public void runCode() {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
					}
				}

				@Override
				public void onEnd() {

				}

				@Override
				public String getName() {
					return "Delay";
				}
			};
		} else if (point.x > 40 && point.x < 60 && point.y > 10 && point.y < 30) {
			MainControl.getGameTicker().reset();
		} else if (point.x > 70 && point.x < 90 && point.y > 10 && point.y < 30) {
			MainControl.getGameTicker().sysoutTasks();
		}
	}
}
