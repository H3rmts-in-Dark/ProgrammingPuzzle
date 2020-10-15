package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import abstractclasses.Task;
import frame.DebugingWindow;

public class Debuger implements Constants {

	protected static Thread controllthread;

	private static ArrayList<Long> repaints;
	private static ArrayList<Long> ticks;

	private static ArrayList<Long> removerepaints;
	private static ArrayList<Long> removeticks;

	private static ArrayList<Long> addrepaints;
	private static ArrayList<Long> addticks;

	private static HashMap<String, Integer> tasktypes;

	private static Long startTasks = (long) 0;
	private static Long endTasks = (long) 0;

	private static Integer TaskSize = 0;

	private Debuger() {
	}

	public static void init() {
		repaints = new ArrayList<>();
		ticks = new ArrayList<>();
		removerepaints = new ArrayList<>();
		removeticks = new ArrayList<>();
		addrepaints = new ArrayList<>();
		addticks = new ArrayList<>();

		tasktypes = new HashMap<>();

		controllthread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					try {
						Long lastrepaint = repaints.get(repaints.size() - 1);
						for (Long repaint : repaints) {
							if (repaint + 1000 < lastrepaint)
								removerepaints.add(repaint);
						}

						Long lasttick = ticks.get(ticks.size() - 1);
						for (Long tick : ticks) {
							if (tick + 1000 < lasttick)
								removeticks.add(tick);
						}
					} catch (IndexOutOfBoundsException e) {
					}
					for (Long repaint : removerepaints) {
						repaints.remove(repaint);
					}
					for (Long tick : removeticks) {
						ticks.remove(tick);
					}

					ticks.addAll(addticks);
					repaints.addAll(addrepaints);

					addrepaints.clear();
					addticks.clear();

					TaskSize = MainControll.getGameTicker().getTaskList().size();

					Integer tasksize = 0;
					for (Entry<String, Integer> entry : tasktypes.entrySet()) {
						tasksize += entry.getValue();
					}

					if (tasksize != TaskSize) {
						tasktypes.clear();

						System.out.println("change:" + tasksize + "-" + TaskSize);

						Object[] it = MainControll.getGameTicker().getTaskList().toArray();

						for (Object objekt : it) {
							Task task = (Task) objekt;
							if (tasktypes.containsKey(task.getClass().getSimpleName())) {
								tasktypes.replace(task.getClass().getSimpleName(),
										tasktypes.get(task.getClass().getSimpleName()) + 1);
							} else {
								tasktypes.put(task.getClass().getSimpleName(), 1);
							}
						}
					}
				}
			}
		};
		controllthread.start();

		new DebugingWindow();
	}

	public static void starttask() {
		startTasks = System.nanoTime();
	}

	public static void tick() {
		addticks.add(System.currentTimeMillis());
		endTasks = System.nanoTime();
	}

	public static void repaint() {
		addrepaints.add(System.currentTimeMillis());
	}

	public static Integer getFps() {
		return repaints.size();
	}

	public static Integer getTps() {
		return ticks.size();
	}

	public static Double getExecutiontime() {
		return ((double) (endTasks - startTasks) / 1000000);// Math.max((endTasks - startTasks),0);
	}

	public static Integer getTaskSize() {
		return TaskSize;
	}

	public static Set<Entry<String, Integer>> getTasktypes() {
		return tasktypes.entrySet();
	}
}
