package logic;

import java.util.ArrayList;
import java.util.Iterator;

import abstractclasses.Task;

public class GameTicker extends Thread implements Constants {

	private Double currentTick = 0.0;
	private ArrayList<Task> taskList;
	private ArrayList<Task> addqueue;

	public GameTicker() {
		taskList = new ArrayList<>();
		addqueue = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			Long lastTicktime = System.currentTimeMillis();

			taskList.addAll(addqueue);
			addqueue.clear();
			
			Debuger.starttask();
			
			Iterator<Task> iterator = taskList.iterator();
			while (iterator.hasNext()) {
				Task task = iterator.next();
				if (task.tryRun())
					iterator.remove();
			}
			
			Debuger.tick();
			
			currentTick++;

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
	private void resetTick() {
		currentTick = 0.0;
	}

	public void addTask(Task task) {
		addqueue.add(task);
	}
}
