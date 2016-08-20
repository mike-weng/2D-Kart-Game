
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represents the tomato item.
 */
public class Tomato extends Item {
	/** The file path of the image of the tomato. */
	private static final String IMG_PATH = "/items/tomato.png";

	/**
	 * Create a tomato item.
	 * 
	 * @param name
	 *            Name of the item.
	 * @param start_x
	 *            The tomato's initial X location (pixels).
	 * @param start_y
	 *            The tomato's initial Y location (pixels).
	 * @throws SlickException
	 */
	public Tomato(String name, double start_x, double start_y)
			throws SlickException {
		super(name, start_x, start_y, IMG_PATH);
	}

	/**
	 * Create a projectile hazard at 40 pixels behind the player.
	 */
	public void use(Player player, int delta, World world)
			throws SlickException {
		player.setItem(null);
		double x = player.getX()
				+ player.getAngle().getXComponent(ITEM_DISTANCE);
		double y = player.getY()
				+ player.getAngle().getYComponent(ITEM_DISTANCE);
		Hazard hazard = new Projectile(x, y, player.getAngle());
		world.getHazards().add(hazard);
	}
}
