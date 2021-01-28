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

	@SuppressWarnings("incomplete-switch")
	public static String toString(Layers layer) {
		switch (layer) {
			case Cable:
				return "cable pictures/";
			case Effects:
				return "effect pictures/";
			case Floor:
				return "floor pictures/";
			default:
				return "";
		}
	}

}
