package tasks;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JButton;

import abstractclasses.Task;
import frame.Frame;

public class ChangeButtonBrightnessTask extends Task {

	private JButton button;
	private Integer brightness = 0;

	public ChangeButtonBrightnessTask(Integer tickDifference, JButton button) {
		super(tickDifference, -1);
		this.button = button;
	}

	@Override
	public void runCode() {
		if (button.getModel().isRollover()) {
			brightness = brightness < 150 ? brightness + 30 : 150;
			Frame.getFrame().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			brightness = brightness > 0 ? brightness - 30 : 0;
		}
		button.setForeground(new Color(brightness, brightness, brightness));
		Frame.repaint();
	}

	@Override
	public void onend() {
	}
}