
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */


import org.newdawn.slick.SlickException;

/**
 * Represent the Dog kart.
 */
public class Dog extends AIPlayer {
	/** The file path of the image of the dog. */
	private static final String IMG_PATH = "/karts/dog.png";
	/** The increase acceleration factor. */
	private static final double HIGH_ACCELERATION_FACTOR = 1.1;
	/** The decrease acceleration factor. */
	private static final double LOW_ACCELERATION_FACTOR = 0.9;
	/**
	 * Creates a new dog.
	 * 
	 * @param start_x
	 *            The dog's initial X location (pixels).
	 * @param start_y
	 *            The dog's initial Y location (pixels).
	 * @param angle
	 *            The dog's initial angle.
	 */
	public Dog(double start_x, double start_y, Angle angle) throws SlickException {
		super(start_x, start_y, angle, IMG_PATH);
	}


	/**
	 * Update the dog for a frame. Set the move direction to 1.
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the dog is on (to get friction and blocking).
	 */
	public void update(int delta, World world) {
		followWaypoints(world);
		// Use different acceleration factor according to player's position.
		if (super.getY() > world.getPlayer().getY()) {
			move(super.getRotate_dir(), HIGH_ACCELERATION_FACTOR * AI_MOVE_DIR, delta, world);
		} else {
			move(super.getRotate_dir(), LOW_ACCELERATION_FACTOR * AI_MOVE_DIR, delta, world);
		}
	}
}
