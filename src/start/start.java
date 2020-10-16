package start;

public class start {
	/**
	 * Die Tatsächliche main Methode
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logic.MainControl.initialize();
		frame.Frame.init();
		logic.Debugger.initialize();

		logic.MainControl.start();
		frame.Frame.setVisible();
		logic.MainControl.createWorld();
	}
}