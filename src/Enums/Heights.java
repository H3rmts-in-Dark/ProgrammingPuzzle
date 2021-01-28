package Enums;

import logic.Constants;

public enum Heights implements Constants {

	/**
	 * Höhe des tiefsten Layers.
	 */
	FLOORHEIGHT,
	/**
	 * Der höchstmögliche Wert eines Layers.
	 */
	UNPASSABLE,
	/**
	 * kann auf ein förederband fallen
	 */
	STORAGE,
	/**
	 * Förderband
	 */
	TRANSPORT;

	public static int getheight(Heights height) {
		switch (height) {
			case FLOORHEIGHT:
				return 0;
			case STORAGE:
				return DEFAULTIMAGEWIDHTHEIGHT / 2;
			case TRANSPORT:
				return DEFAULTIMAGEWIDHTHEIGHT / 3;
			case UNPASSABLE:
				return DEFAULTIMAGEWIDHTHEIGHT;
		}
		return 0;
	}

}
