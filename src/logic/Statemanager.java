package logic;

public class Statemanager {

	public enum States {
		mainmenu, pause, programming, running
	}

	/*
	 * Start: mainmenu danach welt erzeugen danach wechsel zwischen programming,
	 * ruinning, pause danach zurück zu mainmenu
	 */

	private States actualState;

	public Statemanager() {
		actualState = States.mainmenu;
	}

	public void setState(States newState) {
		actualState = newState;
		Main.frame.setState(actualState);
	}

	public States getState() {
		return actualState;
	}
}
