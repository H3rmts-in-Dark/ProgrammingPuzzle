package frame;

import java.awt.Color;

import javax.swing.plaf.metal.MetalToggleButtonUI;

public class CustomUIs {

	public static MetalToggleButtonUI getBasicToggleButtonUI() {
		return new MetalToggleButtonUI() {
			@Override
			protected Color getSelectColor() {
				return Color.GRAY;
			}
		};
	}
}