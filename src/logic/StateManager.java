package logic;

import frame.Frame;

public class StateManager {

	public enum States {
		mainmenu, pause, programming, running, Levelselecting
	}

	/*
	 * Start: mainmenu danach welt erzeugen danach wechsel zwischen programming,
	 * ruinning, pause danach zur�ck zu mainmenu
	 */

	private States actualState;

	public StateManager() {
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
