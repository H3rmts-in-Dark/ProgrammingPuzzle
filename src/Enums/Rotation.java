package Enums;

public enum Rotation {

	norotation, up, down, left, right;

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

	public static Rotation converter(String s) {
		switch (s) {
		case "up":
			return up;
		case "down":
			return down;
		case "left":
			return left;
		case "right":
			return right;
		case "null":
			return norotation;
		default:
			return norotation;
		}
	}
}
