package logic;

import java.util.ArrayList;
import java.util.Iterator;

import abstractclasses.Task;

public class GameTicker extends Thread implements Constants {

	private Double currentTick = 0.0;
	private ArrayList<Task> taskList;
	private ArrayList<Task> addQueue;

	public GameTicker() {
		taskList = new ArrayList<>();
		addQueue = new ArrayList<>();
	}

	@Override
	public void run() {
		while (true) {
			Long lastTicktime = System.currentTimeMillis();
			taskList.addAll(addQueue);
			addQueue.clear();

			Debugger.startTask();

			Iterator<Task> iterator = taskList.iterator();
			while (iterator.hasNext()) {
				Task task = iterator.next();
				if (task.tryRun()) {
					task.onEnd();
					iterator.remove();
				}
			}

			Debugger.tick();
			currentTick++;

			// Wartet, bis der nächste Tick beginnt.
			while ((System.currentTimeMillis() - lastTicktime) <= (1000 / TPS)) {
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

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	private void resetTick() {
		currentTick = 0.0;
	}

	public void addTask(Task task) {
		addQueue.add(task);
	}

	private void sysoutTasks() {
		System.out.println(getTick());
		for (Task task : taskList) {
			System.out.println(task.toString());
		}
	}
}
