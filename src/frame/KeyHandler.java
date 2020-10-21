package frame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyHandler extends KeyAdapter {

	private String commandString;
	private char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'ö', 'Ö', 'ä', 'Ä', 'ü', 'Ü', '§' };
	private ArrayList<Character> character = new ArrayList<>();

	public KeyHandler() {
		commandString = "";
		initialize();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(commandString);
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
		if (character.contains(e.getKeyChar()))
			commandString += e.getKeyChar();
		super.keyTyped(e);
	}

	private static void commandProcessor(String command) {
		command = command.replace("\n", "");
		System.out.println(command);
		command = command.toLowerCase();
		if (command.equals("debug")) {
			new DebuggingWindow();
		} else if (command.startsWith("play§")) {
			logic.SoundManager.playSound(command.substring(5));
		}
	}

	public void initialize() {
		for (char c : chars) {
			character.add(c);
		}
	}
}
