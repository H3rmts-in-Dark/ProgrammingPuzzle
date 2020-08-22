package logic;

public abstract class Task {

	private final Double runTick;

	protected Task(Double timeDifference) {
		runTick = Main.gameTicker.getTick() + timeDifference;
	}

	public boolean doRun(Double tick) {
		if (tick >= runTick)
			return true;
		return false;
	}

	public abstract void runCode();
}
