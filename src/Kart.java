
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;

/**
 * Represents the all the karts in the world.
 *
 */
public class Kart extends GameObject {
	/** Rotation speed, in radians per ms. */
	private static final double ROTATE_SPEED = 0.004;
	/** Acceleration while the kart is driving, in px/ms^2. */
	private static final double ACCELERATION = 0.0005;
	/** The duration for the hazard effect. */
	private double hazardDuration = 0;

	/**
	 * The angle the kart is currently facing. Note: This is in neither degrees
	 * nor radians -- the Angle class allows angles to be manipulated without
	 * worrying about units, and the angle value can be extracted in either
	 * degrees or radians.
	 */
	private Angle angle;
	/** The kart's current velocity (px/ms). */
	private double velocity;

	/**
	 * Creates a new Player.
	 * 
	 * @param start_x
	 *            The kart's initial X location (pixels).
	 * @param start_y
	 *            The kart's initial Y location (pixels).
	 * @param angle
	 *            The kart's initial angle.
	 * @param imgPath
	 * 			  The file path of the image.
	 */
	public Kart(double start_x, double start_y, Angle angle, String imgPath)
			throws SlickException {
		super(start_x, start_y, imgPath);
		this.angle = angle;
		this.velocity = 0;
	}

	/**
	 * Update the kart for a frame. Adjusts the kart's angle and velocity based
	 * on input, and updates the kart's position. Prevents the kart from
	 * entering a blocking tile.
	 * 
	 * @param rotate_dir
	 *            The kart's direction of rotation (-1 for anti-clockwise, 1 for
	 *            clockwise, or 0).
	 * @param move_dir
	 *            The kart's movement in the car's axis (-1, 0 or 1).
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the kart is on (to get friction and blocking).
	 */
	public void move(double rotate_dir, double move_dir, int delta,
			World world) {
		Point pos = super.getPos();
		double rotation_speed = ROTATE_SPEED;
		// Check if the kart is affected by hazard and change strategy
		// accordingly.
		if (hazardDuration > 0 && hazardDuration < Hazard.HAZARD_TIME) {
			rotate_dir = 1;
			move_dir = 1;
			rotation_speed = Hazard.HAZARD_ROTATE_SPEED;
			hazardDuration = hazardDuration + delta;
		} else {
			hazardDuration = 0;
		}

		// Modify the player's angle
		Angle rotateamount = new Angle(rotation_speed * rotate_dir * delta);
		angle = angle.add(rotateamount);
		// Determine the friction of the current location
		double friction = world.frictionAt(pos);
		// Modify the player's velocity. First, increase due to acceleration.
		velocity += ACCELERATION * move_dir * delta;
		// Then, reduce due to friction (this has the effect of creating a
		// maximum velocity for a given coefficient of friction and
		// acceleration)
		velocity *= Math.pow(1 - friction, delta);

		// Modify the position, based on velocity
		// Calculate the amount to move in each direction
		double amount = velocity * delta;
		// Compute the next position, but don't move there yet
		double next_x = pos.getX() + angle.getXComponent(amount);
		double next_y = pos.getY() + angle.getYComponent(amount);
		Point next_pos = new Point(next_x, next_y);
		// If the intended destination is a blocking tile or another kart, do
		// not move there
		// (crash) -- instead, set velocity to 0
		if (world.checkBlocks(next_pos)
				|| world.checkCollisions(next_pos, this) != null) {
			velocity = 0;
		} else {
			// Actually move to the intended destination
			pos.setX(next_x);
			pos.setY(next_y);
		}

	}

	/**
	 * Draw the kart to the screen at the correct place.
	 * 
	 * @param camera
	 *            The camera to draw relative to.
	 */
	public void render(Camera camera) {
		// Calculate the player's on-screen location from the camera
		super.render(camera);
		super.getImg().setRotation((float) angle.getDegrees());
	}

	/** Get the X coordinate of the kart. */
	public double getX() {
		return super.getX();
	}

	/** Get the Y coordinate of the kart. */
	public double getY() {
		return super.getY();
	}

	/** Get the angle the kart is facing. */
	public Angle getAngle() {
		return angle;
	}

	/**
	 * The kart's current velocity, in the direction the player is facing
	 * (px/ms).
	 */
	public double getVelocity() {
		return velocity;
	}

	/** Get the hazard duration of the kart is facing. */
	public double gethazardDuration() {
		return hazardDuration;
	}

	/** Set the hazard duration of the kart is facing. */
	public void sethazardDuration(double hazardDuration) {
		this.hazardDuration = hazardDuration;
	}
}
