package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import abstractclasses.Task;

public class Debugger implements Constants {

	protected static Thread controlThread;

	static ArrayList<Long> repaints;
	static ArrayList<Long> ticks;
	static ArrayList<Long> removeRepaints;
	static ArrayList<Long> removeTicks;
	static ArrayList<Long> addRepaints;
	static ArrayList<Long> addTicks;

	static HashMap<String, Integer> taskTypes;
	static Long startTasks = (long) 0, endTasks = (long) 0,setstartTasks = (long) 0;
	static Integer taskSize = 0;
	

	private Debugger() {
	}

	public static void initialize() {
		repaints = new ArrayList<>();
		ticks = new ArrayList<>();
		removeRepaints = new ArrayList<>();
		removeTicks = new ArrayList<>();
		addRepaints = new ArrayList<>();
		addTicks = new ArrayList<>();
		taskTypes = new HashMap<>();

		controlThread = new Thread() {
			
			Long nextcontroll = System.currentTimeMillis();
			
			@Override
			public void run() {
				while (true) {
					
					while ((System.currentTimeMillis()<nextcontroll)) {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							System.out.println(e.getMessage());
							Thread.currentThread().interrupt();
						}
					}
					
					nextcontroll = System.currentTimeMillis() + (1000 / RPS);

					try {
						Long lastrepaint = repaints.get(repaints.size() - 1);
						for (Long repaint : repaints) {
							if (repaint + 1000 < lastrepaint)
								removeRepaints.add(repaint);
						}

						Long lasttick = ticks.get(ticks.size() - 1);
						for (Long tick : ticks) {
							if (tick + 1000 < lasttick)
								removeTicks.add(tick);
						}
					} catch (IndexOutOfBoundsException e) {
					}
					for (Long repaint : removeRepaints) {
						repaints.remove(repaint);
					}
					for (Long tick : removeTicks) {
						ticks.remove(tick);
					}

					ticks.addAll(addTicks);
					repaints.addAll(addRepaints);

					addRepaints.clear();
					addTicks.clear();

					taskSize = MainControl.getGameTicker().getTaskList().size();

					Integer tasksize = 0;
					for (Entry<String, Integer> entry : taskTypes.entrySet()) {
						tasksize += entry.getValue();
					}

					if (tasksize != taskSize) {
						taskTypes.clear();

						Task[] tasks = MainControl.getGameTicker().getTaskList().toArray(new Task[0]);

						for (Task task : tasks) {
							try {
								if (taskTypes.containsKey(task.getName())) {
									taskTypes.replace(task.getName(),
											taskTypes.get(task.getName()) + 1);
								} else {
									taskTypes.put(task.getName(), 1);
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
		setstartTasks = System.currentTimeMillis();
	}

	public static void tick() {
		addTicks.add(System.currentTimeMillis());
		endTasks = System.currentTimeMillis();
		startTasks = setstartTasks;
	}

	public static void repaint() {
		addRepaints.add(System.currentTimeMillis());
	}

	public static Integer getFPS() {
		return repaints.size();
	}

	public static Integer getTPS() {
		return ticks.size();
	}

	public static Double getExecutionTime() {
		return ((double) (endTasks - startTasks));
	}

	public static Integer getTaskSize() {
		return taskSize;
	}

	public static Set<Entry<String, Integer>> getTasktypes() {
		return taskTypes.entrySet();
	}
}
