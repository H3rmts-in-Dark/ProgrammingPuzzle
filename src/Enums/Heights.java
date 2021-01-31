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
	 * Förderband
	 */
	TRANSPORT;

	public static int getheight(Heights height) {
		switch (height) {
			case FLOORHEIGHT:
				return 0;
			case TRANSPORT:
				return (int) (DEFAULTIMAGEWIDHTHEIGHT / 1.9);
			case UNPASSABLE:
				return DEFAULTIMAGEWIDHTHEIGHT;
		}
		return 0;
	}

}
