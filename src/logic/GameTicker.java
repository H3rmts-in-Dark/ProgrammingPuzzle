package logic;


import java.util.ArrayList;

import abstractclasses.Task;
import frame.Frame;


public class GameTicker extends Thread implements Constants {

	private long currentTick = 0;
	private ArrayList<Task> taskList = new ArrayList<>();
	private ArrayList<Task> addQueue = new ArrayList<>();
	private ArrayList<Task> removeQueue = new ArrayList<>();

	boolean running;

	public GameTicker() {
		running = false;
	}

	@Override
	public synchronized void start() {
		running = true;
		super.start();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long reporttimer = System.currentTimeMillis();

		double totick = 0;
		double tickdelay = 1000000000 / TPS;
		double torepaint = 0;
		double repaintdelay = 1000000000 / FPS;

		int frames = 0;
		int ticks = 0;

		while (running) {
			long now = System.nanoTime();
			totick += (now - lastTime) / tickdelay;
			torepaint += (now - lastTime) / repaintdelay;
			lastTime = now;
			while (totick >= 1) {
				tick();
				totick--;
				ticks++;
			}
			while (torepaint >= 1) {
				render();
				torepaint--;
				frames++;
			}

			if (System.currentTimeMillis() - reporttimer > 1000) {
				reporttimer += 1000;
				Debugger.setFps(frames);
				Debugger.setTps(ticks);
				frames = 0;
				ticks = 0;
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}

		}
	}

	private static void render() {
		Debugger.startrender();
		for (int i = 0; i < Frame.getWindowManager().getWindows().size(); i++) {
			Frame.getWindowManager().getWindows().get(i).Render();
		}
		Debugger.endrender();
	}

	private void tick() {
		Debugger.starttick();
		taskList.addAll(addQueue);
		addQueue.clear();
		try {
			for (Task task : taskList) {
				if (task.getEnded()) {
					removeQueue.add(task);
				} else if (task.tryRun(currentTick)) {
					task.onEnd();
					removeQueue.add(task);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Debugger.update();

		taskList.removeAll(removeQueue);
		removeQueue.clear();

		Debugger.endtick();
		currentTick++;
	}

	public Long getTick() {
		return currentTick;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Task> getTaskList() {
		return (ArrayList<Task>) taskList.clone();
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
