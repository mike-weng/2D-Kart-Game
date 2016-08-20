/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: <Your name> <Your login>
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    /** Create a new World object. */
    public World()
    throws SlickException
    {
        // TODO: Fill in
    }

    /** Update the game state for a frame.
     * @param rotate_dir The player's direction of rotation
     *      (-1 for anti-clockwise, 1 for clockwise, or 0).
     * @param move_dir The player's movement in the car's axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(double rotate_dir, double move_dir, int delta)
    throws SlickException
    {
        // TODO: Fill in
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
        // TODO: Fill in
    }
}
