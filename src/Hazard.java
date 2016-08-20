
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represent the hazard.
 */
public abstract class Hazard extends GameObject {
	/** The file path of the image of the dog. */
	public static final double HAZARD_ROTATE_SPEED = 0.008;
	/** The time limit of the hazard effect. */
	public static final int HAZARD_TIME = 700;
	/** State of the hazard. */
	private boolean destroyed = false;

	/**
	 * Create a new hazard.
	 * 
	 * @param start_x
	 *            The hazard's initial X location (pixels).
	 * @param start_y
	 *            The hazard's initial Y location (pixels).
	 * @param imgPath
	 * @throws SlickException
	 */
	public Hazard(double start_x, double start_y, String imgPath)
			throws SlickException {
		super(start_x, start_y, imgPath);
	}

	/**
	 * Update the hazard's position and state
	 * 
	 * @param player
	 * 			  The player for checks
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the AI player is on (to get friction and blocking).
	 */
	public abstract void update(Player player, double delta, World world);

	/**
	 * Check if the hazard is destroyed or not.
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * Set the state of the hazard.
	 */
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * Initialize the hazard effect.
	 * 
	 * @param kart
	 *            Kart that is affected.
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 */
	public void affect(Kart kart, double delta) {
		kart.sethazardDuration(kart.gethazardDuration() + delta);
	}

}
