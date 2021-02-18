package logic;

import java.awt.Image;

public interface Constants {

	//
	// -----------------------------------------------------------
	// --------------------Customwindow---------------------------

	public static final Integer DEFAULTX = 40;
	public static final Integer DEFAULTY = 40;
	public static final Integer DEFAULTWITH = 200;
	public static final Integer DEFAULTHEIGHT = 100;

	//
	// -----------------------------------------------------------
	// --------------------Worldwindow----------------------------

	public static final Float MAXZOOM = 2.8f;
	public static final Float MINZOOM = 0.4f;

	public static final String CROSS_COLOR = "red";

	/**
	 * SCALE_FAST: Ist nicht so schön, benötigt aber weniger RAM und dauert lönger
	 * um nach dem Verschieben des Fensters wieder auf das normale GPU und CPU level
	 * zu kommen. Aber sonst ist der GPU Verbrauch geringer. Bei dieser Option
	 * bleiben die TPS meist gleich.
	 * 
	 * SCALE_SMOOTH: Benötigt mehr RAM und GPU und hat die gleichen Probleme beim
	 * Verändern der Größe wie SCALE_FAST. Bei dieser Option treten mehr TPS
	 * Einstürze auf. Beim Verschieben des Fensters gibt es starke Verzögerungen.
	 * 
	 */
	public static final Integer Scaler = Image.SCALE_SMOOTH; // Scale Einstellung, Erklärung darüber.

	//
	// -----------------------------------------------------------
	// --------------------Gameticker-----------------------------

	/**
	 * Ticks Per Second
	 */
	public static final Integer TPS = 60;

	/**
	 * Frames Per Second
	 */
	public static final Integer FPS = 30; // - 20

	//
	// -----------------------------------------------------------
	// --------------------Tile-----------------------------------

	public static final Integer DEFAULTIMAGEWIDHTHEIGHT = 64;

	public static final Integer DEFAULTIMAGECHANGETICKDELAY = 5; // ticksperimagechange

	//
	// -----------------------------------------------------------
	// --------------------Debugger-------------------------------

	public static final Integer RPS = 10; // refreshs per second

}
