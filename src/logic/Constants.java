package logic;

import java.awt.Image;

public interface Constants {

// 
// -----------------------------------------------------------
// ---------------------Frame---------------------------------

	public static final Integer FRAMEWIDTH = 1000;
	public static final Integer FRAMEHEIGHT = 800;
	public static final Integer movedelay = 100, dragdelay = 100;

// 
// -----------------------------------------------------------
// --------------------Customwindow---------------------------

	public static final Integer SCROLLBARWIDTH = 4;
	public static final Integer CORNERWIDTH = 6;
	public static final Integer TOPBARWIDTH = 36;
	public static final Integer SIDEBARWIDTH = 8;
	public static final Integer ROUNDCURVES = 7;
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
	public static final Integer Scaler = Image.SCALE_FAST; // Scale Einstellung, Erklärung darüber.

// 
// -----------------------------------------------------------
// --------------------World----------------------------------

	public static final Integer TILEHEIGHTWIDHT = 64;

//
// -----------------------------------------------------------
// -------------------------Layer-----------------------------

	/**
	 * H�he des tiefsten Layers.
	 */
	public static final Integer FLOORHEIGHT = 0;

	/**
	 * Der h�chstm�gliche Wert eines Layers.
	 */
	public static final Integer UNPASSABLE = TILEHEIGHTWIDHT;

	/**
	 * kann auf ein f�rederband fallen
	 */
	public static final Integer ABLAGE = TILEHEIGHTWIDHT / 2;

	/**
	 * F�rderband
	 */
	public static final Integer FÖRDERBANDHÖHE = TILEHEIGHTWIDHT / 3;

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
	
	public static final Integer DEFAULTIMAGEWIDHTHEIGHT = 64;
	
	public static final Integer DEFAULTTPIC = 5; //ticksperimagechange

//
// -----------------------------------------------------------
// --------------------Debugger-------------------------------
	
	public static final Integer RPS = 10; //refreshs per second
}
