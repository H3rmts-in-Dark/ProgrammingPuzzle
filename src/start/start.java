package start;


import frame.Frame;
import logic.DebuggingWindow;
import logic.MainControl;


public class start {

	/**
	 * Die Tatsächliche main Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainControl.initialize();
		Frame.init();

		MainControl.start();
		Frame.setVisible();

		MainControl.createWorld();

		new DebuggingWindow();

	}

}
