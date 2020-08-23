package logic;

public abstract class Task {

	private Double runTick;
	private final Boolean Loop;
	private final Double tickDifference;

	protected Task(Double tickDifference,Boolean loop) {
		this.tickDifference = tickDifference;
		this.Loop = loop;
		setTickDifference();
	}

	private void setTickDifference() {
		runTick = Main.gameTicker.getTick() + tickDifference;
	}

	public Boolean tryRun(Double tick) {
		//System.out.println("tryed" + tick + "-" + runTick);
		if (tick >= runTick) {
			runCode();
			System.out.println("currenttime:" + System.currentTimeMillis());
			if (!Loop)
				return true;
			setTickDifference();
			return false;
		}
		return false;
	}

	public abstract void runCode();
}
