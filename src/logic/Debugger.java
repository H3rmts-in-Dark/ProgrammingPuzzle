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

	static HashMap<String,Integer> taskTypes = new HashMap<>();
	static long startTasks = 0,startRender = 0,executiontime = 0,rendertime = 0;
	static int taskSize = 0,tps = 0,fps = 0;
	static HashMap<CustomWindow,Window> windows = new HashMap<>();

	private Debugger() {
	}

	public static void update() {

		ArrayList<Task> tasks = MainControl.getGameTicker().getTaskList();
		
		taskSize = tasks.size();
		taskTypes.clear();

		for (Task task : tasks) {
			String name = task.getClass().getSimpleName();
			if (taskTypes.containsKey(name))
				taskTypes.replace(name,taskTypes.get(name) + 1);
			else
				taskTypes.put(name,1);
		}
	}

	public static void startDraw(CustomWindow wind) {
		if (!windows.containsKey(wind))
			windows.put(wind,new Window(wind));
		windows.get(wind).startdraw();
	}

	public static void endDraw(CustomWindow wind) {
		windows.get(wind).enddraw();
	}
	
	public static void removeWindow(CustomWindow wind) {
		windows.remove(wind);
	}
	
	public static void starttick() {
		startTasks = System.currentTimeMillis();
	}
	
	public static void endtick() {
		executiontime = System.currentTimeMillis() - startTasks;
	}
	
	public static void startrender() {
		startRender = System.currentTimeMillis();
	}
	
	public static void endrender() {
		rendertime = System.currentTimeMillis() - startRender;
	}

	public static int getTPS() {
		return tps;
	}

	public static int getFps() {
		return fps;
	}

	public static void setTps(int tps) {
		Debugger.tps = tps;
	}

	public static void setFps(int fps) {
		Debugger.fps = fps;
	}

	public static long getExecutionTime() {
		return executiontime;
	}
	
	public static long getRenderTime() {
		return rendertime;
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
			returnmap.put(entry.getValue().getTitle(),entry.getValue().getDrawtime());
		}
		return returnmap.entrySet();
	}

}



class Window {

	private long startdraw = 0;
	private long setstart = 0;
	private long enddraw = 0;
	private String title = "";
	
	public Window(CustomWindow win) {
		title = win.getTitle();
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
	
	public String getTitle() {
		return title;
	}

}
