package logic;

import java.awt.Image;

public interface Constants {

// 
// -----------------------------------------------------------
// -------------------------Layer-----------------------------

	/**
	 * Höhe des tiefsten Layers.
	 */
	public static final Integer FLOORHEIGHT = 0;

	/**
	 * Der höchstmögliche Wert eines Layers.
	 */
	public static final Integer UNPASSABLE = Integer.MAX_VALUE;

	/**
	 * kann auf ein förederband fallen
	 */
	public static final Integer ABLAGE = 20;

	/**
	 * Förderband
	 */
	public static final Integer FÖRDERBANDHÖHE = 10;

// 
// -----------------------------------------------------------
// ---------------------Frame---------------------------------

	public static final Integer FRAMEWIDTH = 1000;
	public static final Integer FRAMEHEIGHT = 800;
	public static final Integer FPS = 45;

// 
// -----------------------------------------------------------
// --------------------Customwindow---------------------------

	public static final Integer SCROLLBARWIDTH = 4;
	public static final Integer CORNERWIDTH = 6;
	public static final Integer TOPBARWIDTH = 40;
	public static final Integer SIDEBARWIDTH = 8;
	public static final Integer ROUNDCURVES = 8;
	public static final Integer DEFAULTX = 40;
	public static final Integer DEFAULTY = 40;
	public static final Integer DEFAULTWITH = 200;
	public static final Integer DEFAULTHEIGHT = 100;
	public static final Integer DEFAULTMINWIDTH = (CORNERWIDTH + 2) * 2 + 20;
	public static final Integer DEFAULTMINHEIGHT = TOPBARWIDTH + (CORNERWIDTH + 2) + 20;
	public static final Integer DEFAULTMAXWIDTH = FRAMEWIDTH;
	public static final Integer DEFAULTMAXHEIGHT = FRAMEHEIGHT;

// 
// -----------------------------------------------------------
// --------------------Worldwindow----------------------------

	public static final Float MAXZOOM = 4f;
	public static final Float MINZOOM = 0.2f;

	/**
	 * SCALE_FAST: Ist nicht so schön, benötigt aber weniger RAM und dauert länger
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
// --------------------World----------------------------------

	public static final Integer DEFAULTTILEWIDTH = 64;

//
// -----------------------------------------------------------
// --------------------Gameticker-----------------------------

	/**
	 * Ticks Per Second
	 */
	public static final Integer TPS = 60;

//
// -----------------------------------------------------------
// --------------------Tile-----------------------------------

	public static final Boolean ANIMATED = true;
	public static final Boolean NOTANIMATED = false;
	public static final String DEFAULTANIMATION = "defaultanimation"; //do not change is used to load from rcs/...
	public static final String INTERACTANIMATION = "interactanimation";
}
