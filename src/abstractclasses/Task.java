package abstractclasses;

import logic.MainControll;

public abstract class Task {

	private Double runTick;
	private Integer Loop;
	private Integer tickDifference;
	
	private Boolean ended;
	
	/**
	 * 
	 * @param tickDifference
	 * @param loop -1 to loop infinite
	 */
	protected Task(Integer tickDifference,Integer loop) {
		this.tickDifference = tickDifference;
		this.Loop = loop;
		this.ended = false;
		MainControll.getGameTicker().addTask(this);
		updateTickDifference();
	}
	
	protected Task(Integer tickDifference) {
		this(tickDifference,0);
	}

	private void updateTickDifference() {
		runTick = MainControll.getGameTicker().getTickIn(tickDifference);
	}
	
	public Double getRunTick() {
		return runTick;
	}

	/**
	 * 
	 * @param tick
	 * @return true -) remove
	 */
	public Boolean tryRun() {
		if (ended)
			return true;
		//System.out.print(getRunTick() + "|" + MainControll.getGameTicker().getTick() + getClass());
		if (MainControll.getGameTicker().getTick() >= runTick) {
			runCode();
			//System.out.println("   exectuded");
			if (Loop > 0) {
				Loop--;
			}
			if (Loop == -1 || Loop > 0) {
				updateTickDifference();
				return false;
			}
			return true;
		}
		//System.out.println("  ");
		return false;
	}
	

	public void end() {
		ended = true;
	}
	
	@Override
	public String toString() {
		return new String(getClass().getSimpleName() + " /Runtick" + getRunTick() + " /Loop" + Loop + extratoString());
	}
	
	/**
	 * " /" + info1 + " /" + info2 
	 * @return
	 */
	abstract public String extratoString();

	public abstract void runCode();
}
