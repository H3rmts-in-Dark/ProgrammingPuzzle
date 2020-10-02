package ListenersandHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.MainControl;
import logic.StateManager.States;

public class MainmenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		MainControl.getStatemanager().setState(States.programming);
	}
}