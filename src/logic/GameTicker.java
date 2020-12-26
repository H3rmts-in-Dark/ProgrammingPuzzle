package logic;


import java.util.ArrayList;
import java.util.Iterator;

import abstractclasses.Task;


public class GameTicker extends Thread implements Constants {

	private Long currentTick = (long) 0;
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
				if (task.getEnded())
					iterator.remove();
				if (task.tryRun())
					iterator.remove();
			}

			Debugger.tick();
			currentTick++;

			// Wartet, bis der n�chste Tick beginnt.
			while ((System.currentTimeMillis() - lastTicktime) < (1000 / TPS)) {
				try {
					Thread.sleep(0,1);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public Long getTick() {
		return currentTick;
	}

	public Long getTickIn(Integer time) {
		return currentTick + time;
	}

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	public void clear() {
		taskList.clear();
	}

	public void reset() {
		currentTick = (long) 0;
		for (Task task : taskList) {
			task.updateTickDifference();
		}
	}

	public void addTask(Task task) {
		addQueue.add(task);
	}

	public void sysoutTasks() {
		System.out.println(getTick());
		for (Task task : taskList) {
			System.out.println(task.toString());
		}
		System.out.print("\n");
	}

}
