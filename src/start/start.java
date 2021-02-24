package start;


import javax.swing.SwingUtilities;

import frame.Frame;
import logic.MainControl;


public class start {

	/**
	 * Die Tatsächliche main Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainControl.initialize();
				Frame.init();

				MainControl.start();
				Frame.getFrame().setVisible(true);
				MainControl.createWorldselectionWindow();
			}

		});

	}

}
