package abstractclasses;

import logic.Constants;
import logic.MainControl;

public abstract class Task implements Constants{

	private long runTick;
	private int Cycles, tickDifference;
	private boolean ended;

	/**
	 * 
	 * @param tickDifference
	 * @param cycles           -1 to loop infinite
	 */
	protected Task(int tickDifference, int cycles) {
		this.tickDifference = tickDifference;
		this.Cycles = cycles;
		this.ended = false;
		MainControl.getGameTicker().addTask(this);
		updateTickDifference();
	}

	public void updateTickDifference() {
		runTick = MainControl.getGameTicker().getTick() + tickDifference;
	}

	public long getRunTick() {
		return runTick;
	}

	/**
	 * 
	 * @param tick
	 * @return true -) remove and onEnd();
	 */
	public boolean tryRun(long tick) {
		if (tick >= runTick) {
			runCode();
			if (Cycles > 0)
				Cycles--;
			if (Cycles == -1 || Cycles > 0) {
				updateTickDifference();
				return false;
			}
			return true;
		}
		return false;
	}
	
	public boolean getEnded() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public abstract void runCode();
	
	/**
	 * not executed when end() was called
	 */
	public void onEnd() {
		
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " runtick:" + runTick + " Cycles:" + Cycles + " tickDifference:" + tickDifference;
	}
}
