package frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyHandler extends KeyAdapter {

	private String commandString;

	public KeyHandler() {
		commandString = "";
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			commandString = "";
			return;
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			commandProcessor(commandString);
			commandString = "";
			return;
		}
		super.keyPressed(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		commandString += c;
		super.keyTyped(e);
	}

	private void commandProcessor(String command) {
		command = command.toLowerCase();
		if (command.equals("debug")) {
			new DebuggingWindow();
		} else if (command.startsWith("play§")) {
			logic.SoundManager.playSound(command.substring(5));
		}
	}
}
