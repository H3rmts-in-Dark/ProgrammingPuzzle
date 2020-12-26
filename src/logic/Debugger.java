package logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import abstractclasses.CustomWindow;
import abstractclasses.Task;


public class Debugger implements Constants {

	protected static Thread controlThread;

	static ArrayList<Long> repaints = new ArrayList<>();
	static ArrayList<Long> ticks = new ArrayList<>();
	static ArrayList<Long> removeRepaints = new ArrayList<>();
	static ArrayList<Long> removeTicks = new ArrayList<>();
	static ArrayList<Long> addRepaints = new ArrayList<>();
	static ArrayList<Long> addTicks = new ArrayList<>();

	static HashMap<String,Integer> taskTypes = new HashMap<>();
	static long startTasks = 0,endTasks = 0,startDraw = 0,setstartTasks = 0;
	static int taskSize = 0;
	static HashMap<CustomWindow,Window> windows = new HashMap<>();

	private Debugger() {
	}

	public static void initialize() {
		controlThread = new Thread() {

			long nextcontroll = System.currentTimeMillis();

			@Override
			public void run() {
				while (true) {
					while ((System.currentTimeMillis() < nextcontroll)) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							Thread.currentThread().interrupt();
						}
					}

					nextcontroll = System.currentTimeMillis() + (1000 / RPS);

					try {
						long lastrepaint = repaints.get(repaints.size() - 1);
						for (Long repaint : repaints) {
							if (repaint + 1000 < lastrepaint)
								removeRepaints.add(repaint);
						}

						long lasttick = ticks.get(ticks.size() - 1);
						for (long tick : ticks) {
							if (tick + 1000 < lasttick)
								removeTicks.add(tick);
						}
					} catch (IndexOutOfBoundsException | NullPointerException e) {
					}

					for (long repaint : removeRepaints) {
						repaints.remove(repaint);
					}
					for (long tick : removeTicks) {
						ticks.remove(tick);
					}

					removeRepaints.clear();
					removeTicks.clear();

					ticks.addAll(addTicks);
					repaints.addAll(addRepaints);

					addRepaints.clear();
					addTicks.clear();

					taskSize = MainControl.getGameTicker().getTaskList().size();

					Integer tasksize = 0;
					for (Entry<String,Integer> entry : taskTypes.entrySet()) {
						tasksize += entry.getValue();
					}

					if (tasksize != taskSize) {
						taskTypes.clear();

						ArrayList<Task> tasks = MainControl.getGameTicker().getTaskList();

						for (Task task : tasks) {
							try {
								if (taskTypes.containsKey(task.getName())) {
									taskTypes.replace(task.getName(),taskTypes.get(task.getName()) + 1);
								} else {
									taskTypes.put(task.getName(),1);
								}
							} catch (NullPointerException e) {
							}
						}
					}
				}
			}

		};
		controlThread.start();
	}

	public static void startTask() {
		setstartTasks = System.nanoTime();
	}

	public static void tick() {
		addTicks.add(System.currentTimeMillis());
		endTasks = System.nanoTime();
		startTasks = setstartTasks;
	}

	public static void startDraw(CustomWindow wind) {
		if (!windows.containsKey(wind))
			windows.put(wind,new Window(wind));
		windows.get(wind).startdraw();
	}

	public static void endDraw(CustomWindow wind) {
		windows.get(wind).enddraw();
	}

	public static void repaint() {
		addRepaints.add(System.currentTimeMillis());
	}

	public static int getFPS() {
		return repaints.size();
	}

	public static int getTPS() {
		return ticks.size();
	}

	public static long getExecutionTime() {
		return (endTasks - startTasks) / 1000000;
	}

	public static int getTaskSize() {
		return taskSize;
	}

	public static Set<Entry<String,Integer>> getTasktypes() {
		return taskTypes.entrySet();
	}

	public static Set<Entry<String,String>> getWindows() {
		var returnmap = new HashMap<String,String>();
		for (Entry<CustomWindow,Window> entry : windows.entrySet()) {
			returnmap.put(entry.getKey().getName(),entry.getValue().getDrawtime());
		}
		return returnmap.entrySet();
	}

}



class Window {

	private long startdraw = 0;
	private long setstart = 0;
	private long enddraw = 0;

	public Window(CustomWindow win) {

	}

	void startdraw() {
		setstart = System.currentTimeMillis();
	}

	void enddraw() {
		startdraw = setstart;
		enddraw = System.currentTimeMillis();
	}

	public String getDrawtime() {
		return Long.toString(enddraw - startdraw);
	}

}
