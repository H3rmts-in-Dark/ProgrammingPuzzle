package start;

import javax.swing.SwingUtilities;

import Programming.Interpreter;
import frame.Frame;
import logic.DebuggingWindow;
import logic.MainControl;

public class start {

	/**
	 * Die Tatsächliche main Methode
	 * 
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
<<<<<<< HEAD
=======
		// Interpreter.interpret();
		// if (true)
		// return;
>>>>>>> bc2303dfecb47db16e83bbda4711bee48b06074f
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainControl.initialize();
				Frame.init();

				MainControl.start();
				Frame.getFrame().setVisible(true);

<<<<<<< HEAD
				MainControl.createWorld();
				// MainControl.createWorldselectionWindow();
				new DebuggingWindow();
=======
				// MainControl.createProgrammingWindow();
				MainControl.createWorld();
				MainControl.createWorldselectionWindow();
>>>>>>> bc2303dfecb47db16e83bbda4711bee48b06074f
			}
		});

	}

}
