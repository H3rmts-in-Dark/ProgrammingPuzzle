package logic;

import java.util.ArrayList;

public class GameTick implements Runnable {

	private Boolean running = false;
	private Double currentTick = 0.0;
	private Long delay = (long) 100; // Wartezeit zwischen Ticks im Millisekunden
	private ArrayList<Task> taskList;

	@Override
	public void run() {
		while (true) {
			while (running) {

				for (int i = 0; i < taskList.size(); i++) {
					if (taskList.get(i).doRun(currentTick))
						taskList.get(i).runCode();
				}

				try {
					Thread.sleep(delay);
				} catch (Exception e) {
				}
				currentTick++;
			}
			try {
				wait(); // TODO Muss bei Aufrufen mit Notify gestartet werden
			} catch (InterruptedException e) {
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
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
