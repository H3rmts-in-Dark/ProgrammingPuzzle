package ListenersandHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.Main;

public class MainmenuListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Main.frame.mainmenustartButton) {
			System.out.println("start");
		}
	}

}
