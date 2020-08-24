package logic;

public abstract class Task {

	private Double runTick;
	private final Boolean Loop;
	private final Integer tickDifference;

<<<<<<< HEAD
	protected Task(Integer tickDifference,Boolean loop) {
=======
	protected Task(Double tickDifference, Boolean loop) {
>>>>>>> 704ce74452233b45c6ce6e181e249505ac92d1a6
		this.tickDifference = tickDifference;
		this.Loop = loop;
		setTickDifference();
		Main.gameTicker.addTask(this);
	}

	private void setTickDifference() {
		runTick = Main.gameTicker.getTick() + tickDifference;
	}

	public Boolean tryRun(Double tick) {
		// System.out.println("tryed" + tick + "-" + runTick);
		if (tick >= runTick) {
			runCode();
			//System.out.println("currenttime:" + System.currentTimeMillis());
			if (!Loop)
				return true;
			setTickDifference();
			return false;
		}
		return false;
	}

	public abstract void runCode();
}
