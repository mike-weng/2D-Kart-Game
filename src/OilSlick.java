
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

public class OilSlick extends Hazard {
	/** The file path of the image of the oilslick. */
	private static final String IMG_PATH = "/items/oilslick.png";

	/**
	 * Create a new oilslick.
	 * 
	 * @param start_x
	 *            The oilslick's initial X location (pixels).
	 * @param start_y
	 *            The oilslick's initial Y location (pixels).
	 * @throws SlickException
	 */
	public OilSlick(double start_x, double start_y) throws SlickException {
		super(start_x, start_y, IMG_PATH);
	}

	/**
	 * Update the oilslick's position and affect the collided kart.
	 */
	public void update(Player player, double delta, World world) {
		Kart affectedKart = world.checkCollisions(super.getPos(), null);
		if (world.checkBlocks(super.getPos()) == true) {
			super.setDestroyed(true);
		}
		if (affectedKart != null) {
			super.affect(affectedKart, delta);
			super.setDestroyed(true);
		}
	}

}
