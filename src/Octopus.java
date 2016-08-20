
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represent the Octopus kart.
 */
public class Octopus extends AIPlayer {
	/** The file path of the image of the dog. */
	private static final String IMG_PATH = "/karts/octopus.png";
	/** The upper bound to check the player's position to the dog. */
	private static final int UPPER_BOUND = 250;
	/** The lower bound to check the player's position to the dog. */
	private static final int LOWER_BOUND = 100;

	/**
	 * Creates a new octopus.
	 * 
	 * @param start_x
	 *            The octopus's initial X location (pixels).
	 * @param start_y
	 *            The octopus's initial Y location (pixels).
	 * @param angle
	 *            The octopus's initial angle.
	 */
	public Octopus(double start_x, double start_y, Angle angle)
			throws SlickException {
		super(start_x, start_y, angle, IMG_PATH);
	}

	/**
	 * Update the octopus for a frame. Set the move direction to 1.
	 * 
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the octopus is on (to get friction and blocking).
	 */
	public void update(int delta, World world) {

		// Check if player is around.
		followWaypoints(world);
		if (isBetween(world.getPlayer())) {
			calcRotateDir(world.getPlayer().getPos());
		}
		move(super.getRotate_dir(), AI_MOVE_DIR, delta, world);

	}

	/**
	 * Check if the player is within 100 pixels and 250 pixels.
	 * 
	 * @param player
	 * 				The player to check.
	 * @return true if player is between 100 pixels and 250 pixels.
	 */
	private boolean isBetween(Player player) {
		boolean isBetween = false;
		if (super.distTo(player.getPos()) < UPPER_BOUND
				&& super.distTo(player.getPos()) > LOWER_BOUND) {
			isBetween = true;
		}

		return isBetween;
	}
}
