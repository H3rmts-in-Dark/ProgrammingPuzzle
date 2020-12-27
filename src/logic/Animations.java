package logic;


public enum Animations {

	noanimation,defaultanimation,interactanimation,offanimation,onanimation;
	
	public static Layers getLayer(Animations a) {
		switch (a) {
			case defaultanimation:
				return Layers.Objects;
			case interactanimation:
				return Layers.Objects;
			case noanimation:
				return null;
			case offanimation:
				return Layers.Cable;
			case onanimation:
				return Layers.Cable;
		}
		return null;
	}
	
}
