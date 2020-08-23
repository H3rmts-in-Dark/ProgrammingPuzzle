package ListenersandHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.Main;
import logic.Statemanager.States;

public class MainmenuListener implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Main.frame.mainMenuStartButton) {
			Main.statemanager.setState(States.programming);
		}
	}
}