package start;


import javax.swing.SwingUtilities;

import Programming.Interpreter;
import frame.Frame;
import logic.MainControl;


public class start {

	/**
	 * Die Tatsächliche main Methode
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Interpreter.interpret();
		if (true)
			return;
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainControl.initialize();
				Frame.init();

				MainControl.start();
				Frame.getFrame().setVisible(true);

				// MainControl.createProgrammingWindow();
				// MainControl.createWorld();
				MainControl.createWorldselectionWindow();
			}

		});

	}

}
