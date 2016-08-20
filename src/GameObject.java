
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GameObject {
	/** The point of the game object. */
	private Point pos;
	/** The image of the game object. */
	private Image img;

	/**
	 * Represents all the game objects in the world.
	 * 
	 * @param start_x
	 *            The starting x-coordinate of the game object.
	 * @param start_y
	 *            The starting y-coordinate of the game object.
	 * @param imgPath
	 *            The file path of the image of the game object.
	 * @throws SlickException
	 */
	public GameObject(double start_x, double start_y, String imgPath)
			throws SlickException {
		img = new Image(Game.ASSETS_PATH + imgPath);
		pos = new Point(start_x, start_y);
	}

	/**
	 * Render the game object on the screen
	 * 
	 * @param camera
	 *            The current screen.
	 */
	public void render(Camera camera) {
		int screen_x = (int) (pos.getX() - camera.getLeft());
		int screen_y = (int) (pos.getY() - camera.getTop());
		img.drawCentered(screen_x, screen_y);
	}

	/**
	 * Determine whether the game object is within 40 pixels of the point
	 * 
	 * @param pos
	 *            The point to check for collision.
	 * @return true if collided and false otherwise.
	 */
	public boolean collides(Point pos) {
		boolean collided = false;
		if (distTo(pos) < 40) {
			collided = true;
		}
		return collided;
	}

	/**
	 * Determines the Euclidean distance from the game object's position to the
	 * specified point.
	 * 
	 * @param pos
	 *            The point to calculate Euclidean distance.
	 * @return the Euclidean distance.
	 */
	public double distTo(Point pos) {
		double x = this.getX() - pos.getX();
		double y = this.getY() - pos.getY();
		double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		return dist;
	}

	/** Get the point of the game object. */
	public Point getPos() {
		return pos;
	}

	/** Set the point of the game object. */
	public void setPos(Point pos) {
		this.pos = pos;
	}

	/** Get the x-coordinate of the game object. */
	public double getX() {
		return pos.getX();
	}

	/** Set the x-coordinate of the game object. */
	public void setX(double x) {
		pos.setX(x);
	}

	/** Get the y-coordinate of the game object. */
	public double getY() {
		return pos.getY();
	}

	/** Set the y-coordinate of the game object. */
	public void setY(double y) {
		pos.setY(y);
	}

	/** Get the image of the game object. */
	public Image getImg() {
		return img;
	}

}
