package start;

import frame.Frame;

public class start {
	/**
	 * Die Tats�chliche main Methode
	 * @param args
	 */
	public static void main(String[] args) {
		new logic.MainControll();
		Frame.getFrame().setVisible(true);
	}
}