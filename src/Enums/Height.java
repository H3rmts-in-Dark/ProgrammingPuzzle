package Enums;


import logic.Constants;


public enum Height implements Constants {

	/**
	 * Höhe des tiefsten Layers.
	 */
	FLOORHEIGHT,
	/**
	 * Der höchstmögliche Wert eines Layers.
	 */
	UNPASSABLE,
	/**
	 * Förderband
	 */
	TRANSPORT;

	public static int getint(Height height) {
		switch (height) {
			case FLOORHEIGHT:
				return 0;
			case TRANSPORT:
				return (int) (DEFAULTIMAGEWIDHTHEIGHT / 2.1);
			case UNPASSABLE:
				return DEFAULTIMAGEWIDHTHEIGHT;
		}
		return 0;
	}

}
