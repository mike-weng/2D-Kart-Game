
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import org.newdawn.slick.SlickException;
/**
 * Represent the item.
 */
public abstract class Item extends GameObject {
	/** The name of the item. */
	private String name;
	/** If the item is picked up or not. */
	private boolean pickedUp = false;
	/** The distance of the item to be picked up. */
	public static final int ITEM_DISTANCE = 40;
	
	/**
	 * Create an item.
	 * 
	 * @param name
	 *            Name of the item.
	 * @param start_x
	 *            The item's initial X location (pixels).
	 * @param start_y
	 *            The item's initial Y location (pixels).
	 * @throws SlickException
	 */
	public Item(String name, double start_x, double start_y, String imgPath)
			throws SlickException {
		super(start_x, start_y, imgPath);
		this.name = name;
	}
	/**
	 * Draws the image of the item on the panel.
	 * @param x
	 * 			X-coordinate to be drawn at.
	 * @param y
	 * 			Y-coordinate to be drawn at.
	 */
	public void drawImage(int x, int y) {
		super.getImg().draw(x, y);
	}
	/**
	 * Check if the item is picked up by the player.
	 * @param player
	 */
	public void pickUp(Player player) {
		if (pickedUp == false) {
			player.setItem(this);
			pickedUp = true;
		}
	}
	/**
	 * Use the item.
	 * @param player
	 * @param delta
	 * @param world
	 * @throws SlickException
	 */
	public abstract void use(Player player, int delta, World world)
			throws SlickException;
	/** Get the name of the item. */
	public String getName() {
		return name;
	}
	/** Get the state of the item. */
	public boolean isPickedUp() {
		return pickedUp;
	}

}
