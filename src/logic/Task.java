package logic;

public abstract class Task {

	private final double runTick;

	protected Task(double timeDifference) {
		runTick = GameTick.getTick() + timeDifference;
	}

	public boolean doRun(double tick) {
		if (tick == runTick)
			return true;
		else
			return false;
	}

	public abstract void runCode();
}
