package logic;

public enum Rotation {
	up,
	down,
	left,
	right;
	
	public static String toString(Rotation r) {
		try {
			switch (r) {
			case down:
				return "unten/";
			case left:
				return "links/";
			case right:
				return "rechts/";
			case up:
				return "oben/";
			default:
				return "";
			}
		} catch (NullPointerException e) {
			return "";
		}
	}

}