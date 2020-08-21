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
<<<<<<< HEAD
	
	private States actualState;
	
=======

	public States actualState;

>>>>>>> 4d4f60ed9fafffb2b90637841b303df9e224796b
	public Statemanager() {
		actualState = States.mainmenu;
		//setState(States.mainmenu);
	}

	public void setState(States newState) {
		this.actualState = newState;
<<<<<<< HEAD
		Main.frame.setState(actualState);
=======
		Main.gameFrame.setState(newState);
>>>>>>> 4d4f60ed9fafffb2b90637841b303df9e224796b
	}

	public States getState() {
		return actualState;
	}
}
