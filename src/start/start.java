package start;

import frame.Frame;
import logic.MainControl;

public class start {
	/**
	 * Die Tats�chliche main Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainControl.init();
		Frame.init();
		MainControl.start();
		Frame.setVisible();
		MainControl.createWorld();
	}
}