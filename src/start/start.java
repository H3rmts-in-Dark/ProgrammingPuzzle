package start;

import frame.Frame;
import logic.Debuger;
import logic.MainControll;

public class start {
	/**
	 * Die Tatsächliche main Methode
	 * @param args
	 */
	public static void main(String[] args) {
		MainControll.init();
		Frame.init();
		Debuger.init();
		
		MainControll.start();
		Frame.setVisible();
		MainControll.createWorld();
	}
}