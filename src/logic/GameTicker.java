package logic;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GameTicker extends Thread {

	private Double currentTick = 0.0;
	private Integer tps = 10; // ticks per second
	private ArrayList<Task> taskList;

	public GameTicker() {
		taskList = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			Long lastTicktime = System.currentTimeMillis();
			
			for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
				try {
					Task task = iterator.next();
					if (task.tryRun(currentTick))
						taskList.remove(task);
				} catch (ConcurrentModificationException e) {
					e.printStackTrace();
				}
			}
			currentTick++;
			
			/** repaint Frame
			 */
			try {Main.frame.repaint();
			} catch (NullPointerException e) {}
			
			/** wait for next tick
			 */
			while ((System.currentTimeMillis() - lastTicktime) <= (1000 / tps)) {
				try {
					Thread.sleep(0, 1);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public Double getTick() {
		return currentTick;
	}

	@SuppressWarnings("unused")
	private Double getTickIn(Double time) {
		return currentTick + time;
	}

	@SuppressWarnings("unused")
	private void resetTick() {
		currentTick = 0.0;
	}

	public void addTask(Task task) {
		taskList.add(task);
	}
}
