package start;

import frame.Frame;

public class start {
	/**
	 * Die Tatsächliche main Methode
	 * @param args
	 */
	public static void main(String[] args) {
		new logic.MainControll();
		Frame.getFrame().setVisible(true);
	}
}