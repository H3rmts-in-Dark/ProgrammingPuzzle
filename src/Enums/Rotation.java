package Enums;


public enum Rotation {

	norotation,up,down,left,right;

	public static String toPath(Rotation r) {
		switch (r) {
			case down:
				return "unten/";
			case left:
				return "links/";
			case right:
				return "rechts/";
			case up:
				return "oben/";
			case norotation:
				return "";
		}
		return "";
	}

}
