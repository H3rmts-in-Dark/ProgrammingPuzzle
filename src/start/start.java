package start;

import frame.Frame;
import logic.MainControll;

public class start {
	/**
	 * Die Tats�chliche main Methode
	 * @param args
	 */
	public static void main(String[] args) {
		MainControll.init();
		Frame.init();
		
		MainControll.start();
		Frame.setVisible();
		MainControll.createWorld();
	}
}