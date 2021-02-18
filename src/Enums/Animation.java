package Enums;

public enum Animation {

	/**
	 * Objekt
	 */
	noanimation, deactivatedanimation, activatedanimation, interactanimation,

	/**
	 * Cable
	 */
	offanimation, onanimation;

	public static Layer getLayer(Animation a) {
		switch (a) {
		case deactivatedanimation:
			return Layer.Objects;
		case activatedanimation:
			return Layer.Objects;
		case interactanimation:
			return Layer.Objects;
		case offanimation:
			return Layer.Cable;
		case onanimation:
			return Layer.Cable;
		case noanimation:
			return Layer.Floor;
		}
		return null;
	}

	public static boolean getRepeat(Animation a) {
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
