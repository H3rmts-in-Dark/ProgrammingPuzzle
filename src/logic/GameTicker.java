package logic;


import java.util.ArrayList;

import abstractclasses.Task;


public class GameTicker extends Thread implements Constants {

	private long currentTick = 0;
	private ArrayList<Task> taskList = new ArrayList<>();
	private ArrayList<Task> addQueue = new ArrayList<>();
	private ArrayList<Task> removeQueue = new ArrayList<>();

	public GameTicker() {
	}

	@Override
	public void run() {
		while (true) {
			Long lastTicktime = System.currentTimeMillis();
			taskList.addAll(addQueue);
			addQueue.clear();

			Debugger.starttick();

			for (Task task : taskList) {
				if (task.tryRun(currentTick)) {
					task.onEnd();
					removeQueue.add(task);
				}
				if (task.getEnded()) {
					removeQueue.add(task);
				}
			}

			taskList.removeAll(removeQueue);
			removeQueue.clear();

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

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	public void clear() {
		taskList.clear();
	}

	public void reset() {
		currentTick = 0;
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
	}

}
