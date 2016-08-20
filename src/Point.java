
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

/**
 * Represent a position point.
 */
public class Point {
	/** X-coordinate of the point. */
	private double x;
	/** Y-coordinate of the point. */
	private double y;

	/**
	 * Create a new point.
	 * @param x
	 * 			X-coordinate of the point.
	 * @param y
	 * 			Y-coordinate of the point.
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/** Get the x-coordinate. */
	public double getX() {
		return x;
	}
	/** Set the x-coordinate. */
	public void setX(double x) {
		this.x = x;
	}
	/** Get the y-coordinate. */
	public double getY() {
		return y;
	}
	/** Set the y-coordinate. */
	public void setY(double y) {
		this.y = y;
	}
	
}
