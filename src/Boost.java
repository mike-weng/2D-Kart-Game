
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represents the boost item.
 */
public class Boost extends Item {
	/** The file path of the image of the boost. */
	private static final String IMG_PATH = "/items/boost.png";
	/** The acceleration factor of the boost. */
	public static final double BOOST_FACTOR = 0.0008/0.0005;
	/** The move direction of the boost, to change the player to move forward always. */
	public static final int BOOST_MOVE_DIR = 1;
	/** The time limit of the boost effect. */
	public static final int BOOST_TIME = 3000;

	/**
	 * Create a boost item.
	 * 
	 * @param name
	 *            Name of the item.
	 * @param start_x
	 *            The boost's initial X location (pixels).
	 * @param start_y
	 *            The boost's initial Y location (pixels).
	 * @throws SlickException
	 */
	public Boost(String name, double start_x, double start_y)
			throws SlickException {
		super(name, start_x, start_y, IMG_PATH);
	}
	
	/**
	 * Initiate the boost duration of the player.
	 */
	public void use(Player player, int delta, World world) throws SlickException {
		player.setItem(null);
		player.setBoostDuration(player.getBoostDuration() + delta);
	}

}
