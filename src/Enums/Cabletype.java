package Enums;

public enum Cabletype {

	n, o, s, w, sn, no, nw, sw, so, wo, nws, osw, wno, nos, m, notype;

	public static Cabletype converter(String s) {
		switch (s) {
		case "n":
			return n;
		case "o":
			return o;
		case "s":
			return Cabletype.s;
		case "w":
			return w;
		case "sn":
			return sn;
		case "no":
			return no;
		case "nw":
			return nw;
		case "sw":
			return sw;
		case "so":
			return so;
		case "wo":
			return wo;
		case "nws":
			return nws;
		case "osw":
			return osw;
		case "wno":
			return wno;
		case "nos":
			return nos;
		case "m":
			return m;
		default:
			return notype;
		}
	}
}
