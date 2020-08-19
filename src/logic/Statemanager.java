package logic;

public class Statemanager {

	public enum States {
		mainmenu, pause, programming, running
	}

	/*
	 * Startet im mainmenu State, dann nachdem ein Level gestartet wurde Wechsel
	 * zwischen programming, running und pause. Nach Beenden des Levels zurück zu
	 * mainmenu.
	 */

	public States actualState;

	public Statemanager() {
		setState(States.mainmenu);
	}

	public void setState(States newState) {
		this.actualState = newState;
		Main.gameFrame.setState(newState);
	}

	public States getState() {
		return actualState;
	}
}
