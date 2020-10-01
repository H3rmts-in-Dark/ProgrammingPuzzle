package logic;

import frame.Frame;

public class Statemanager {

	public enum States {
		mainmenu, pause, programming, running, Levelselecting
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
		Frame.setState(actualState);
	}

	public States getState() {
		return actualState;
	}
}
