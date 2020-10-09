package logic;

import java.util.ArrayList;

import frame.DebugingWindow;

public class Debuger implements Constants{

	private static ArrayList<Long> repaints;
	private static ArrayList<Long> ticks;
	
	private static ArrayList<Long> removerepaints;
	private static ArrayList<Long> removeticks;
	
	private static ArrayList<Long> addrepaints;
	private static ArrayList<Long> addticks;

	protected static Thread controllthread;
	
	private static Long startTasks;
	private static Long endTasks;

	private Debuger() {
	}

	public static void init() {
		repaints = new ArrayList<>();
		ticks = new ArrayList<>();
		removerepaints = new ArrayList<>();
		removeticks = new ArrayList<>();
		addrepaints = new ArrayList<>();
		addticks = new ArrayList<>();
		
		controllthread = new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					try {
						Long lastrepaint = repaints.get(repaints.size()-1);
						for (Long repaint : repaints) {
							if (repaint + 1000 < lastrepaint)
								removerepaints.add(repaint);						}
						
						Long lasttick = ticks.get(ticks.size()-1);
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
				}
			}
		};
		controllthread.start();
		
		new DebugingWindow();
	}
	
	public static void starttask() {
		startTasks = System.currentTimeMillis();
	}
	
	public static void tick() {
		addticks.add(System.currentTimeMillis());
		endTasks = System.currentTimeMillis();
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
	public static Integer getExecutiontime() {
		return (int) Math.max((endTasks - startTasks),0);
	}
}
