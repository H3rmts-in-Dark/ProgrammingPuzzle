package logic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import abstractclasses.CustomWindow;
import abstractclasses.Task;


public class Debugger implements Constants {

	protected static Thread controlThread;

	static long starttime = 0;

	static ArrayList<Long> ticks = new ArrayList<>();
	static ArrayList<Long> removeRepaints = new ArrayList<>();
	static ArrayList<Long> removeTicks = new ArrayList<>();
	static ArrayList<Long> addRepaints = new ArrayList<>();
	static ArrayList<Long> addTicks = new ArrayList<>();

	static HashMap<String,Integer> taskTypes = new HashMap<>();
	static long startTasks = 0,endTasks = 0,startDraw = 0,setstartTasks = 0,executionpeek = 0;
	static int taskSize = 0,tps = 0;
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
						if (ticks.get(0) + 1000 <= System.currentTimeMillis()) {
							tps = ticks.size();
							ticks.clear();
							executionpeek = 0;
						}
					} catch (IndexOutOfBoundsException | NullPointerException e) {
					}

					ticks.removeAll(removeTicks);

					removeRepaints.clear();
					removeTicks.clear();

					ticks.addAll(addTicks);

					addRepaints.clear();
					addTicks.clear();

					taskSize = MainControl.getGameTicker().getTaskList().size();

					Integer tasksize = 0;
					for (Entry<String,Integer> entry : taskTypes.entrySet()) {
						tasksize += entry.getValue();
					}

					if (tasksize != taskSize) {
						taskTypes.clear();

						@SuppressWarnings("unchecked")
						ArrayList<Task> tasks = (ArrayList<Task>) MainControl.getGameTicker().getTaskList().clone();

						for (Task task : tasks) {
							try {
								if (taskTypes.containsKey(task.getClass().getSimpleName())) {
									taskTypes.replace(task.getClass().getSimpleName(),taskTypes.get(task.getClass().getSimpleName()) + 1);
								} else 
									taskTypes.put(task.getClass().getSimpleName(),1);
								
							} catch (NullPointerException e) {
							}
						}
					}
				}
			}

		};
		controlThread.start();
	}

	public static void starttick() {
		setstartTasks = System.nanoTime();
	}

	public static void tick() {
		addTicks.add(System.currentTimeMillis());
		endTasks = System.nanoTime();
		startTasks = setstartTasks;   // (
		executionpeek = (executionpeek < (endTasks - startTasks) / 1000000 ) ? ((endTasks - startTasks) / 1000000) : executionpeek;
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

	public static int getTPS() {
		return tps;
	}

	public static long getExecutionTimePeek() {
		return executionpeek;
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
			returnmap.put(entry.getKey().getClass().getSimpleName(),entry.getValue().getDrawtime());
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
