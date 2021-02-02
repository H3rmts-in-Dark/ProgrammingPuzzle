package Enums;


import logic.Layers;


public enum Animations {

	/**
	 * Objekt
	 */
	noanimation,deactivatedanimation,activatedanimation,interactanimation,

	/**
	 * Cable
	 */
	offanimation,onanimation;

	public static Layers getLayer(Animations a) {
		switch (a) {
			case deactivatedanimation:
				return Layers.Objects;
			case activatedanimation:
				return Layers.Objects;
			case interactanimation:
				return Layers.Objects;
			case offanimation:
				return Layers.Cable;
			case onanimation:
				return Layers.Cable;
			case noanimation:
				return Layers.Floor;
		}
		return null;
	}

	public static boolean getRepeat(Animations a) {
		switch (a) {
			case deactivatedanimation:
				return true;
			case activatedanimation:
				return true;
			case interactanimation:
				return false;
			case offanimation:
				return true;
			case onanimation:
				return true;
			case noanimation:
				return false;
		}
		return false;
	}

}
