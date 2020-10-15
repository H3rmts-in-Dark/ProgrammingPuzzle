package logic;

import java.awt.Image;

public interface Constants {

// 
// -----------------------------------------------------------
// -------------------------Layer-----------------------------

	/**
	 * tiefster layer
	 */
	public static final Integer Floor = 0;

	/**
	 * unendlich(fast) hoch
	 */
	public static final Integer Unpassable = Integer.MAX_VALUE;

	/**
	 * kann auf ein förederband fallen
	 */
	public static final Integer Ablage = 20;

	/**
	 * Förderband
	 */
	public static final Integer Förderband = 10;

// 
// -----------------------------------------------------------
// ---------------------Frame---------------------------------

	public static final Integer FrameWidht = 1000;

	public static final Integer FrameHeight = 800;

	public static final Integer fps = 45;

// 
// -----------------------------------------------------------
// --------------------Customwindow---------------------------

	public static final Integer scrollbarwidth = 4;

	public static final Integer cornerwidht = 6;

	public static final Integer topbarwhidht = 40;

	public static final Integer sidebarwhidht = 8;

	public static final Integer roundcurves = 8;

	public static final Integer defaultX = 40;

	public static final Integer defaultY = 40;

	public static final Integer defaultWidht = 200;

	public static final Integer defaultHeight = 100;

	public static final Integer defaultMinWidht = (cornerwidht + 2) * 2 + 20;

	public static final Integer defaultMinHeight = topbarwhidht + (cornerwidht + 2) + 20;

	public static final Integer defaultMaxWidht = FrameWidht;

	public static final Integer defaultMaxHeight = FrameHeight;

// 
// -----------------------------------------------------------
// --------------------Worldwindow----------------------------

	public static final Float MaxZoom = 4f;

	public static final Float MinZoom = 0.2f;
	
	/**
	 * SCALE_FAST 
	 * hässlich 
	 * 
	 * weniger ram 
	 * 
	 * nachdem man das fenster bewegt oder vergrößert wenn 
	 * man in die welt gezoom hat dauert es
	 * lange bis sich gpu und cpu verbrauch normalisieren
	 *  
	 * geringerer gpu verbrauch(auser kurze spitzen beim 
	 * fenseter vergrößern verkleinern, treten abunzu 
	 * aber auch bei cpu auf)
	 * 
	 * ticks per second immer gleich
	 * 
	 * 
	 * SCALE_SMOOTH bischen mehr ram verbrauch 
	 * 
	 * mehr ram
	 * 
	 * höherer gpu verbrauch(und kurze spitzen beim 
	 * fenseter vergrößern verkleinern, treten abunzu 
	 * aber auch bei cpu auf)
	 * 
	 * ticks per second verändern
	 * 
	 * starke verzögerung wenn man das fenster bewegt
	 * un in die welt gezoomt hat
	 */
	public static final Integer Scaler = Image.SCALE_SMOOTH;  // Fast ore smoth

// 
// -----------------------------------------------------------
// --------------------World----------------------------------

	public static final Integer defaulttilewidth = 64;

//
// -----------------------------------------------------------
// --------------------Gameticker-----------------------------

	public static final Integer tps = 60; // ticks per second

//
// -----------------------------------------------------------
// --------------------Tile-----------------------------------
	
	public static final Boolean animated = true;
	
	public static final Boolean notanimated = false;
	
	public static final String defaultanimation = "defaultanimation";
	
	public static final String interactanimation = "interactanimation";
}
