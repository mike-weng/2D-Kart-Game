
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represents the oilcan item.
 */
public class Oilcan extends Item {
	/** The file path of the image of the oilcan. */
	private static final String IMG_PATH = "/items/oilcan.png";

	/**
	 * Create a oilcan item.
	 * 
	 * @param name
	 *            Name of the item.
	 * @param start_x
	 *            The oilcan's initial X location (pixels).
	 * @param start_y
	 *            The oilcan's initial Y location (pixels).
	 * @throws SlickException
	 */
	public Oilcan(String name, double start_x, double start_y)
			throws SlickException {
		super(name, start_x, start_y, IMG_PATH);
	}
	
	/**
	 * Create a oilslick hazard at 40 pixels behind the player.
	 */
	public void use(Player player, int delta, World world) throws SlickException {
		player.setItem(null);
		double x = player.getX() + player.getAngle().getXComponent(-ITEM_DISTANCE);
		double y = player.getY() + player.getAngle().getYComponent(-ITEM_DISTANCE);
		Hazard hazard = new OilSlick(x, y);
		world.getHazards().add(hazard);
	}
}
