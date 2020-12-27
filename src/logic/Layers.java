package logic;


public enum Layers {

	/**
	 * always 1 picture load with noanimation
	 */
	Floor,
	/**
	 * load with loadPicture can be more than 1
	 */
	Cable,
	/**
	 * load with loadAnimation not with loadPicture
	 */
	Objects,Entitys,
	/**
	 * load with loadPicture can be more than 1
	 */
	Effects;

	public static String toString(Layers l) {
		switch (l) {
			case Cable:
				return "cable pictures/";
			case Effects:
				return "effect pictures/";
			case Floor:
				return "floor pictures/";
			// $CASES-OMITTED$
			default:
			break;
		}
		return "";
	}

}
