package abstractclasses;

import logic.Constants;
import logic.MainControl;

public abstract class Task implements Constants{

	private Double runTick;
	private Integer Loop, tickDifference;
	private Boolean ended;

	/**
	 * 
	 * @param tickDifference
	 * @param loop           -1 to loop infinite
	 */
	public Task(Integer tickDifference, Integer loop) {
		this.tickDifference = tickDifference;
		this.Loop = loop;
		this.ended = false;
		MainControl.getGameTicker().addTask(this);
		updateTickDifference();
	}
	
	/**
	 * repated 1 time
	 * 
	 * @param tickDifference
	 */
	protected Task(Integer tickDifference) {
		this(tickDifference, 0);
	}

	private void updateTickDifference() {
		runTick = MainControl.getGameTicker().getTickIn(tickDifference);
	}

	public Double getRunTick() {
		return runTick;
	}

	/**
	 * 
	 * @param tick
	 * @return true -) remove and onEnd();
	 */
	public Boolean tryRun() {
		if (MainControl.getGameTicker().getTick() >= runTick) {
			runCode();
			if (Loop > 0)
				Loop--;
			if (Loop == -1 || Loop > 0) {
				updateTickDifference();
				return false;
			}
			return true;
		}
		return false;
	}
	
	public Boolean getEnded() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public abstract void runCode();

	public abstract void onEnd();
}
