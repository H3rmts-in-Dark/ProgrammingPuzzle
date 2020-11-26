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

public class DebuggingWindow extends CustomWindow {

	String fpsav, tpsav, executiontimeav, tasks, cpu;
	Set<Entry<String, Integer>> tasktypes;

	OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public DebuggingWindow() {
		super(300, 400, new Point(20, 20), "Debugging",(int)(TPS * 0.5));
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

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(10, 10, 20, 20);
		g2.drawLine(14, 13, 26, 13);
		g2.drawLine(14, 27, 26, 27);
		g2.drawLine(14, 14, 18, 20);
		g2.drawLine(26, 14, 22, 20);
		g2.drawLine(18, 20, 14, 26);
		g2.drawLine(22, 20, 26, 26);

		Integer height = 30;

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

	@Override
	public void mousePressed(Point point) {
		if (point.x > 10 && point.x < 30 && point.y > 10 && point.y < 30) {
			new Task(1,-1) {
				
				@Override
				public void runCode() {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				@Override
				public void onEnd() {
					
				}

				@Override
				public String getName() {
					return "delay";
				}
			};
		}
	}
}
