package logic;

import java.util.ArrayList;

public class GameTick implements Runnable {

	private static Boolean running = false;
	private static Double currentTick = 0.0;
	private static Long delay = (long) 100; // Wartezeit zwischen Ticks im Millisekunden
	private static ArrayList<Task> taskList;

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

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		GameTick.running = running;
	}

	public static double getTick() {
		return currentTick;
	}
	
	public static double getTickIn(double time) {
		return currentTick + time;
	}

	public static void resetTick() {
		currentTick = 0.0;
	}

	public static void addTask(Task task) {
		taskList.add(task);
	}
}
