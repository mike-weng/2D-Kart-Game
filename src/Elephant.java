
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */


import org.newdawn.slick.SlickException;

/**
 * Represent the elephant kart.
 */
public class Elephant extends AIPlayer {
	/** The file path of the image of the elephant. */
	private static final String IMG_PATH = "/karts/elephant.png";
	/**
	 * Creates a new elephant.
	 * 
	 * @param start_x
	 *            The elephant's initial X location (pixels).
	 * @param start_y
	 *            The elephant's initial Y location (pixels).
	 * @param angle
	 *            The elephant's initial angle.
	 */
	public Elephant(double start_x, double start_y, Angle angle) throws SlickException {
		super(start_x, start_y, angle, IMG_PATH);
	}


	/**
	 * Update the Elephant for a frame. Set the move direction to 1.
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the elephant is on (to get friction and blocking).
	 */
	public void update(int delta, World world) {
		followWaypoints(world);
		move(super.getRotate_dir(), AI_MOVE_DIR, delta, world);
	}

}
