package frame;


import java.awt.event.KeyEvent;

import logic.DebuggingWindow;
import logic.MainControl;
import world.WorldSelectionWindow;


public class UserInputInterpreter {

	private static String commandString = "";

	private UserInputInterpreter() {
	}

	public static void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			commandString = "";
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			commandProcessor(commandString);
			commandString = "";
			return;
		} else if (e.getKeyCode() == 8 || e.getKeyCode() == 127) {
			try {
				commandString = commandString.substring(0,commandString.length() - 1);
				// System.out.println(commandString);
			} catch (StringIndexOutOfBoundsException e2) {
			}
		} else if (e.getKeyCode() != KeyEvent.VK_SHIFT) {
			char c = e.getKeyChar();
			commandString += c;
			System.out.println(commandString);
		}
	}

	private static void commandProcessor(String command) {
		command = command.replace("\n","");
		// System.out.println(command);
		command = command.toLowerCase();
		if (command.equals("debug")) {
			new DebuggingWindow();
		} else if (command.equals("select")) {
			new WorldSelectionWindow();
		} else if (command.equals("world")) {
			MainControl.createWorld();
		}
	}

}