package abstractclasses;

import logic.MainControl;

public abstract class Task {

	private Double runTick;
	private Integer Loop;
	private final Integer tickDifference;

	protected Task(Integer tickDifference, Integer loop) {
		this.tickDifference = tickDifference;
		this.Loop = loop;
		updateTickDifference();
		MainControl.getGameTicker().addTask(this);
	}

	protected Task(Integer tickDifference) {
		this.tickDifference = tickDifference;
		this.Loop = 0;
		updateTickDifference();
		MainControl.getGameTicker().addTask(this);
	}

	private void updateTickDifference() {
		runTick = MainControl.getGameTicker().getTickIn(tickDifference);
	}

	/**
	 * 
	 * @param tick
	 * @return true -) remove
	 */
	public Boolean tryRun(Double tick) {
		if (tick >= runTick) {
			runCode();
			if (Loop > 0) {
				updateTickDifference();
				Loop--;
				return false;
			} else if (Loop == -1) {
				updateTickDifference();
				return false;
			}
			return true;
		}
		return false;
	}

	public abstract void runCode();
}
