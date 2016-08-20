
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

public class Projectile extends Hazard {
	/** The file path of the image of the tomato. */
	private static final String IMG_PATH = "/items/tomato-projectile.png";
	/** Angle at where the tomato is fired from. */
	Angle angle;
	/** The project velocity of the tomato. */
	private static final double PROJECT_VELOCITY = 1.7;

	/**
	 * Create a new projectile.
	 * 
	 * @param start_x
	 *            The projectile's initial X location (pixels).
	 * @param start_y
	 *            The projectile's initial Y location (pixels).
	 * @param angle
	 *            Angle at where the tomato is fired from.
	 * @throws SlickException
	 */
	public Projectile(double start_x, double start_y, Angle angle)
			throws SlickException {
		super(start_x, start_y, IMG_PATH);
		this.angle = angle;
	}

	/**
	 * Update the projectile's position and affect the collided kart.
	 */
	public void update(Player player, double delta, World world) {
		double amount = PROJECT_VELOCITY * delta;
		double next_x = super.getX() + angle.getXComponent(amount);
		double next_y = super.getY() + angle.getYComponent(amount);
		Point next_pos = new Point(next_x, next_y);
		Kart affectedKart = world.checkCollisions(next_pos, null);
		if (world.checkBlocks(next_pos) == true) {
			super.setDestroyed(true);
		}
		if (affectedKart != null) {
			super.affect(affectedKart, delta);
			super.setDestroyed(true);
		} else {
			// Actually move to the intended destination
			super.setX(next_x);
			super.setY(next_y);
		}

	}

}
