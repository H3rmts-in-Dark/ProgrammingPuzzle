package logic;

import java.util.ArrayList;
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

			Long continueTime = System.currentTimeMillis();
<<<<<<< HEAD
			
			for (Iterator<Task> iterator = taskList.iterator(); iterator.hasNext();) {
				Task task = iterator.next();
=======

			for (Task task : taskList) {
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
				if (task.tryRun(currentTick))
					taskList.remove(task);
			}

			while ((System.currentTimeMillis() - continueTime) <= (1000 / tps)) {
				try {
					Thread.sleep(0, 1);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}

			currentTick++;
		}
	}

	public double getTick() {
		return currentTick;
	}

	public double getTickIn(Double time) {
		return currentTick + time;
	}

	public void resetTick() {
		currentTick = 0.0;
	}

	public void addTask(Task task) {
		taskList.add(task);
	}
}
