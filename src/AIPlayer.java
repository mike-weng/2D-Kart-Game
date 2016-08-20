
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import java.util.ArrayList;

import org.newdawn.slick.SlickException;

/**
 * Represents the AI players in the world.
 */
public abstract class AIPlayer extends Kart {
	/** Move direction of the AI player, always moving forward. */
	public static final int AI_MOVE_DIR = 1;
	/**
	 * Waypoint range of the AI player, the range that determins if the player
	 * is close enough to the way point.
	 */
	private static final int WAYPOINT_RANGE = 250;
	/**
	 * Current waypoint of the AI player, the index of the waypoint the player
	 * is moving towards.
	 */
	private int currentWaypoint = 0;
	/**
	 * Rotate direction of the AI player, to determine how much to turn towards
	 * the waypoint.
	 */
	private double rotate_dir;

	/**
	 * Creates a new AIPlayer.
	 * 
	 * @param start_x
	 *            The AI player's initial X location (pixels).
	 * @param start_y
	 *            The AI player's initial Y location (pixels).
	 * @param angle
	 *            The AI player's initial angle.
	 * @param imgPath
	 * 			  The file path of the image.
	 */
	public AIPlayer(double start_x, double start_y, Angle angle, String imgPath)
			throws SlickException {
		super(start_x, start_y, angle, imgPath);
	}

	/**
	 * Update the AI player for a frame.
	 * 
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the AI player is on (to get friction and blocking).
	 */
	public abstract void update(int delta, World world);

	/**
	 * Update the next waypoint and follow the waypoint by calculating rotation
	 * direction.
	 * 
	 * @param world
	 */
	public void followWaypoints(World world) {
		ArrayList<Point> waypoints = world.getWaypoints();
		if (currentWaypoint == World.NUM_WAYPOINTS) {
			return;
		}
		calcRotateDir(waypoints.get(currentWaypoint));
		if (inWaypointRange(waypoints.get(currentWaypoint))) {
				currentWaypoint = currentWaypoint + 1;
		}
		
	}

	/**
	 * Calculate the rotate direction according to the waypoint.
	 * 
	 * @param waypoint
	 *            The waypoint used to calculate the rotate direction.
	 */
	public void calcRotateDir(Point waypoint) {
		// The diffecence between AI player pos and waypoint.
		double xDifference = waypoint.getX() - super.getX();
		double yDifference = waypoint.getY() - super.getY();
		// Use the difference to get the angle from the AI player's pos to
		// waypoint.
		Angle waypointAngle = Angle.fromCartesian(xDifference, yDifference);
		if (facingLeft(super.getAngle(), waypointAngle)) {
			// If player is facing left of the waypoint, rotate direction is set
			// to
			// clockwise.
			rotate_dir = 1;
		} else if (facingRight(super.getAngle(), waypointAngle)) {
			// If player is facing right of the waypoint, rotate direction is
			// set to
			// counter-clockwise.
			rotate_dir = -1;
		} else {
			rotate_dir = 0;
		}
	}

	/**
	 * Check if the player is facing left relative to waypoint
	 * 
	 * @param kartAngle
	 *            Angle of the player that is facing.
	 * @param waypointAngle
	 *            Angle from player to waypoint.
	 * @return true if player is facing left of the waypoint.
	 */
	public boolean facingLeft(Angle kartAngle, Angle waypointAngle) {
		boolean facingLeft = false;
		int upperBound = (int) waypointAngle.getDegrees();
		int lowerBound;
		if (waypointAngle.getDegrees() > 0) {
			// angle minus 180 to set the lower bound of left side, since upper
			// bound is positive.
			lowerBound = (int) (waypointAngle.getDegrees() - 180);
			if ((int) kartAngle.getDegrees() < upperBound
					&& (int) kartAngle.getDegrees() > lowerBound) {
				facingLeft = true;
			}
		} else if (waypointAngle.getDegrees() < 0) {
			// angle plus 180 to set the lower bound of left side, since upper
			// bound is negative.
			lowerBound = (int) (waypointAngle.getDegrees() + 180);
			if ((int) kartAngle.getDegrees() < upperBound
					|| (int) kartAngle.getDegrees() > lowerBound) {
				facingLeft = true;
			}
		}
		return facingLeft;
	}

	/**
	 * Check if the player is facing right relative to waypoint
	 * 
	 * @param kartAngle
	 *            Angle of the player that is facing.
	 * @param waypointAngle
	 *            Angle from player to waypoint.
	 * @return true if player is facing right of the waypoint.
	 */
	public boolean facingRight(Angle kartAngle, Angle waypointAngle) {
		boolean facingRight = false;
		int upperBound = (int) waypointAngle.getDegrees();
		int lowerBound;
		if (waypointAngle.getDegrees() > 0) {
			// angle minus 180 to set the lower bound of left side, since upper
			// bound is positive.
			lowerBound = (int) (waypointAngle.getDegrees() - 180);
			if ((int) kartAngle.getDegrees() > upperBound
					|| (int) kartAngle.getDegrees() < lowerBound) {
				facingRight = true;
			}
		} else if (waypointAngle.getDegrees() < 0) {
			// angle plus 180 to set the lower bound of left side, since upper
			// bound is negative.
			lowerBound = (int) (waypointAngle.getDegrees() + 180);
			if ((int) kartAngle.getDegrees() > upperBound
					&& (int) kartAngle.getDegrees() < lowerBound) {
				facingRight = true;
			}
		}
		return facingRight;
	}

	/**
	 * Check if the AI player gets close enough to the waypoint.
	 * 
	 * @param pos
	 *            Waypoint position for checks.
	 * @return true if AI player is close enough.
	 */
	public boolean inWaypointRange(Point pos) {
		boolean inRange = false;
		if (super.distTo(pos) < WAYPOINT_RANGE) {
			inRange = true;
		}
		return inRange;
	}

	/** Get the index of the current waypoint. */
	public int getCurrentWaypoint() {
		return currentWaypoint;
	}

	/** Set the index of the current waypoint. */
	public void setCurrentWaypoint(int currentWaypoint) {
		this.currentWaypoint = currentWaypoint;
	}

	/** Get the rotate direction. */
	public double getRotate_dir() {
		return rotate_dir;
	}

}
