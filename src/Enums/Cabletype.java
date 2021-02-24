package Enums;

public enum Cabletype {

	n, o, s, w, no, ns, nw, os, ow, sw, nos, now, nsw, osw, nosw, notype;

	public static Cabletype convert(String string) {
		switch (string) {
		case "n":
			return n;
		case "o":
			return o;
		case "s":
			return s;
		case "w":
			return w;
		case "sn":
			return ns;
		case "no":
			return no;
		case "nw":
			return nw;
		case "sw":
			return sw;
		case "so":
			return os;
		case "wo":
			return ow;
		case "nws":
			return nsw;
		case "osw":
			return osw;
		case "wno":
			return now;
		case "nos":
			return nos;
		case "m":
			return nosw;
		default:
			return notype;
		}
	}
}
