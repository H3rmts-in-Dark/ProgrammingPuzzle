package Enums;

public enum Signalcolor {
	red, blue, green, nocolor;

	public static Signalcolor converter(String s) {
		switch (s) {
		case "red":
		case "rot":
			return red;
		case "blue":
		case "blau":
			return blue;
		case "green":
		case "grün":
			return green;
		case "nocolor":
		case "null":
			return nocolor;
		default:
			return nocolor;
		}
	}
}
