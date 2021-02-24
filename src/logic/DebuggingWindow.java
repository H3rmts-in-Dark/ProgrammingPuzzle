package logic;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import abstractclasses.CustomWindow;
import abstractclasses.Task;


public class DebuggingWindow extends CustomWindow {

	String tps = "",fps = "",executiontime = "",rendertime = "",gametick = "",tasksize = "";
	Set<Entry<String,Integer>> tasktypes;
	Set<Entry<String,String>> windows;

	// OperatingSystemMXBean os = (OperatingSystemMXBean)
	// ManagementFactory.getOperatingSystemMXBean();

	@SuppressWarnings("unused")
	public DebuggingWindow() {
		super(300,400,new Point(20,20),"Debugging",1);
		new Task(15,-1) {

			@Override
			public boolean runCode() {
				loadValues();
				return true;
			}

			@Override
			public String getname() {
				return "DebuggingWindowRefresh";
			}

		};
	}

	@SuppressWarnings("unchecked")
	private void loadValues() {
		tps = Double.toString(Debugger.getTPS());
		fps = Double.toString(Debugger.getFps());
		executiontime = Double.toString(Debugger.getExecutionTime());
		rendertime = Double.toString(Debugger.getRenderTime());
		windows = Debugger.getWindows();
		tasksize = Integer.toString(Debugger.getTaskSize());
		tasktypes = ((HashMap<String,Integer>) Debugger.getTasktypes().clone()).entrySet();
		gametick = Long.toString(MainControl.getGameTicker().getTick());
		// cpu = Double.toString(Math.round((os.getProcessCpuLoad() * 100) * 100.0) /
		// 100.0);
	}

	@Override
	public BufferedImage getImage() {

		BufferedImage image = getEmptyImage();
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.GRAY);
		g2.fillRect(0,0,getWidth(),getHeight());

		g2.setFont(new Font("Arial",Font.BOLD,16));
		g2.setColor(Color.BLACK);

		drawButtons(g2);

		Integer height = 35; // 35

		g2.drawString("TPS:" + tps + " ps / " + TPS + " ps",10,height += 25);
		g2.drawString("FPS:" + fps + " ps / " + FPS + " ps",10,height += 25);

		height += 5;
		g2.drawString("Executiontime:" + executiontime + "ms",10,height += 25);
		g2.drawString("Rendertime:" + rendertime + "ms",10,height += 25);

		height += 5;
		if (tasktypes != null) {
			g2.drawString("Tasks:" + tasktypes.size(),10,height += 25);

			for (Entry<String,Integer> entry : tasktypes) {
				g2.drawString(entry.getKey() + ":" + entry.getValue(),10,height += 25);
			}
		} else {
			g2.drawString("Tasks:0",10,height += 25);
		}

		height += 5;
		g2.drawString("Gametick:" + gametick,10,height += 25);

		height += 5;
		g2.drawString("Drawtimes:",10,height += 25);
		if (windows != null) {
			for (Entry<String,String> entry : windows) {
				g2.drawString(entry.getKey() + ":" + entry.getValue(),10,height += 25);
			}
		}

		g2.dispose();
		return image;
	}

	private static void drawButtons(Graphics2D g2) {
		Integer x = 0;

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2));

		g2.drawRect(x += 10,10,20,20);

		g2.drawLine(x + 4,13,x + 16,13);
		g2.drawLine(x + 4,27,x + 16,27);
		g2.drawLine(x + 4,14,x + 8,20);
		g2.drawLine(x + 16,14,x + 12,20);
		g2.drawLine(x + 8,20,x + 4,26);
		g2.drawLine(x + 12,20,x + 16,26);

		g2.setColor(Color.RED);
		g2.drawLine(x += 30,10,x + 20,30);
		g2.drawLine(x + 20,10,x,30);

		g2.setColor(Color.BLACK);
		g2.drawRect(x,10,20,20);
		g2.setColor(new Color(0,0,0,130));
		g2.fillOval(x + 3,13,14,14);
		g2.setColor(Color.BLACK);
		g2.drawOval(x + 3,13,14,14);
		g2.drawLine(x + 10,20,x + 12,16);
		g2.drawLine(x + 10,20,x + 6,22);

		g2.setColor(Color.BLACK);
		g2.drawRect(x += 30,10,20,20);
		g2.setColor(new Color(0,0,0,130));
		g2.fillRect(x + 2,12,15,15);
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawRect(x + 2,12,15,15);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(x + 5,15,x + 15,15);
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(x + 4,17,x + 8,17);
		g2.drawLine(x + 10,17,x + 14,17);
		g2.drawLine(x + 4,19,x + 10,19);
		g2.drawLine(x + 4,21,x + 14,21);
		g2.drawLine(x + 4,23,x + 8,23);
		g2.drawLine(x + 10,23,x + 14,23);
		g2.drawLine(x + 4,25,x + 10,25);
		g2.drawLine(x + 12,25,x + 14,25);
	}

	@Override
	public void mousePressed(Point point) {
		if (point.x > 10 && point.x < 30 && point.y > 10 && point.y < 30) {
			MainControl.getGameTicker().reset();
		} else if (point.x > 40 && point.x < 60 && point.y > 10 && point.y < 30) {
			MainControl.getGameTicker().reset();
		} else if (point.x > 70 && point.x < 90 && point.y > 10 && point.y < 30) {
			MainControl.getGameTicker().sysoutTasks();
		}
	}

}
