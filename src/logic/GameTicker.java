package logic;

import java.util.ArrayList;
import java.util.Iterator;

import abstractclasses.Task;
import frame.Frame;

public class GameTicker extends Thread {

	private Double currentTick = 0.0;
	private Integer tps = 10; // ticks per second
	private ArrayList<Task> taskList = new ArrayList<Task>();

	public GameTicker() {
	}

	@Override
	public void run() {
		while (true) {
			Long lastTicktime = System.currentTimeMillis();

			Iterator<Task> iterator = taskList.iterator();
			while (iterator.hasNext()) {
				Task task = iterator.next();
				if (task.tryRun(currentTick))
					iterator.remove();
			}
			currentTick++;

			// TODO Frame repaint methode?
			Frame.getFrame().repaint();

			// delay for next tick
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

	public Double getTickIn(Integer time) {
		return currentTick + time;
	}

	@SuppressWarnings("unused")
	public void resetTick() {
		currentTick = 0.0;
	}

	public void addTask(Task task) {
		taskList.add(task);
	}
}
