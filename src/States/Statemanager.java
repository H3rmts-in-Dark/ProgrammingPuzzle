package States;

import logic.Main;

public class Statemanager {
	
	enum States{mainmenu,pause,programming,running}
	
	/*
	 * Start: mainmenu danach welt erzeugen
	 * danach wechsel zwischen programming,ruinning,pause
	 * danach mainmenu
	 */
	
	public States actualState;
	
	public Statemanager() {
		setState(States.mainmenu);
	}
	
	private void stateSwitched() {
		Main.frame.getMainmenu().setVisible(false);
		Main.frame.getPause().setVisible(false);
		Main.frame.getRunning().setVisible(false);
		Main.frame.getProgramming().setVisible(false);
		
		switch (actualState) {
		case mainmenu:
			System.out.println("changed to mainmenu");
			Main.frame.getMainmenu().setVisible(true);
			break;
		case programming:
			System.out.println("changed to programming");
			Main.frame.getProgramming().setVisible(true);
			break;
		case running:
			System.out.println("changed to running");
			Main.frame.getRunning().setVisible(true);
			break;
		case pause:
			System.out.println("changed to pause");
			break;
		}
	}
	
	public void setState(States newState) {
		this.actualState = newState;
		stateSwitched();
	}
	public States getState() {
		return actualState;
	}
}
