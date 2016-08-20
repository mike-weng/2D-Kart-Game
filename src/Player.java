
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represent the player's kart.
 */
public class Player extends Kart {
	/** The file path of the image of the player. */
	private static final String IMG_PATH = "/karts/donkey.png";
	/** The item holding by the player. */
	private Item item;
	/** The rank of the player. */
	private int rank;
	/** The duration of the boost effect. */
	private double boostDuration = 0;

	/**
	 * Creates a new Player.
	 */
	public Player(double start_x, double start_y, Angle angle)
			throws SlickException {
		super(start_x, start_y, angle, IMG_PATH);
	}

	/**
	 * Update the player for a frame.
	 *
	 * @param rotate_dir
	 *            The player's direction of rotation (-1 for anti-clockwise, 1
	 *            for clockwise, or 0).
	 * @param move_dir
	 *            The player's movement in the car's axis (-1, 0 or 1).
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the player is on (to get friction and blocking).
	 * @throws SlickException
	 */
	public void update(double rotate_dir, double move_dir, int delta,
			World world, boolean use_item) throws SlickException {
		// If the player is boosted, acceleration is increased by boost factor
		// and move direction is always 1.
		if (boostDuration > 0 && boostDuration <= Boost.BOOST_TIME) {
			move(rotate_dir, Boost.BOOST_FACTOR * Boost.BOOST_MOVE_DIR, delta,
					world);
			boostDuration = boostDuration + delta;
		} else {
			move(rotate_dir, move_dir, delta, world);
			boostDuration = 0;
		}
	}
	
	/** Get the item of the player. */
	public Item getItem() {
		return item;
	}
	/** Set the item of the player. */
	public void setItem(Item item) {
		this.item = item;
	}
	/** Get the rank of the player. */
	public int getRank() {
		return rank;
	}
	/** Set the rank of the player. */
	public void setRank(int rank) {
		this.rank = rank;
	}
	/** Get the boost duration of the player. */
	public double getBoostDuration() {
		return boostDuration;
	}

	/** Set the boost duration of the player. */
	public void setBoostDuration(double boostDuration) {
		this.boostDuration = boostDuration;
	}

}
