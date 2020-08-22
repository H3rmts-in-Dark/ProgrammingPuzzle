package ListenersandHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.Main;
import logic.Statemanager.States;

public class MainmenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Main.frame.mainmenustartButton) {
			Main.statemanager.setState(States.programming);
		}
	}

}
