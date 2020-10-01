package ListenersandHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.MainControll;
import logic.Statemanager.States;

public class MainmenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		MainControll.getStatemanager().setState(States.programming);
	}
}