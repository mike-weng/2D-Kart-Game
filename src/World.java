
/* SWEN20003 Object Oriented Software Development
 * Kart Racing Game
 * Author: Mike Weng
 */

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Represents the entire game world. (Designed to be instantiated just once for
 * the whole game).
 */
public class World {
	/** The string identifier to use for looking up the map friction. */
	private static final String MAP_FRICTION_PROPERTY = "friction";
	/** Player's starting x-coordinate. */
	private static final int PLAYER_START = 1332;
	/** Dog's starting x-coordinate. */
	private static final int DOG_START = 1404;
	/** Octopus's starting x-coordinate. */
	private static final int OCTOPUS_START = 1476;
	/** Elephant's starting x-coordinate. */
	private static final int ELEPHANT_START = 1260;
	/** The y-coordinate of the starting line. */
	private static final int START_LINE = 13086;
	/** The y-coordinate of the finish line. */
	private static final int FINISH_LINE = 1026;
	/** The x-coordinate offset of the end game message. */
	private static final int MSG_X_OFFSET = 50;
	/** The y-coordinate offset of the end game message. */
	private static final int MSG_Y_OFFSET = 50;
	/** The number of items. */
	private static final int NUM_ITEMS = 12;
	/** The number of AIPlayers. */
	private static final int NUM_OPPONENTS = 3;
	/** The number of waypoints. */
	public static final int NUM_WAYPOINTS = 48;
	/** The x-coordinates of all waypoints */
	private static final int[] WAYPOINT_X = { 1350, 990, 990, 1350, 1638, 1962,
			1638, 774, 846, 1026, 1710, 1584, 1584, 1584, 918, 702, 882, 882,
			1386, 1602, 2034, 1926, 1278, 918, 738, 900, 900, 1674, 2034, 1962,
			1746, 1350, 954, 558, 702, 1170, 1530, 1134, 1386, 1998, 1998, 1890,
			1566, 738, 918, 1350, 1350, 1296 };
	/** The y-coordinates of all waypoints */
	private static final int[] WAYPOINT_Y = { 12186, 11682, 11466, 11070, 10890,
			10458, 10170, 10206, 9882, 9738, 9738, 9378, 8946, 8334, 8226, 7974,
			7686, 6606, 6498, 7038, 7002, 6174, 5958, 6030, 5706, 5562, 4986,
			4770, 4770, 4518, 4122, 3978, 4410, 4122, 3150, 2934, 3150, 3726,
			3870, 3762, 3150, 2790, 2214, 2142, 1818, 1746, 1530, 0 };
	/** The x-coordinates of all items */
	private static final int[] ITEM_X = { 1350, 990, 990, 864, 1962, 1818, 1206,
			990, 1206, 1314, 1926, 1422 };
	/** The y-coordinates of all items */
	private static final int[] ITEM_Y = { 12438, 11610, 10242, 7614, 6498, 6534,
			5130, 4302, 3690, 3690, 3510, 2322 };
	/** The names of all items */
	private static final String[] ITEM_NAME = { "Oil can", "Tomato", "Boost",
			"Oil can", "Oil can", "Boost", "Tomato", "Boost", "Tomato",
			"Oil can", "Boost", "Tomato" };

	/**
	 * The world map (two dimensional grid of tiles). The concept of tiles is a
	 * private implementation detail to World. All public methods deal with
	 * pixels, not tiles.
	 */
	private TiledMap map;
	/** The player's kart. */
	private Player player;
	/** The all the apponents. */
	private AIPlayer[] opponents;
	/** The all the items. */
	private ArrayList<Item> items;
	/** The all the waypoints. */
	private ArrayList<Point> waypoints;
	/** The all the hazards. */
	private ArrayList<Hazard> hazards;
	/** The camera used to track what the screen should show. */
	private Camera camera;
	/** The panel showing the player's ranking and item picked up. */
	private Panel panel;

	/** Create a new World object. */
	public World() throws SlickException {
		map = new TiledMap(Game.ASSETS_PATH + "/map.tmx", Game.ASSETS_PATH);
		panel = new Panel();
		items = new ArrayList<Item>(NUM_ITEMS);
		createItems();		// Create all items in and populate array
		waypoints = new ArrayList<Point>(NUM_WAYPOINTS);
		createWaypoints();	// Create all waypoints in and populate array
		hazards = new ArrayList<Hazard>();
		player = new Player(PLAYER_START, START_LINE, Angle.fromDegrees(0));
		opponents = new AIPlayer[NUM_OPPONENTS];
		opponents[0] = new Elephant(ELEPHANT_START, START_LINE, Angle.fromDegrees(0));
		opponents[1] = new Dog(DOG_START, START_LINE, Angle.fromDegrees(0));
		opponents[2] = new Octopus(OCTOPUS_START, START_LINE, Angle.fromDegrees(0));
		camera = new Camera(Game.SCREENWIDTH, Game.SCREENHEIGHT, this.player);
	}

	/**
	 * Update the game state for a frame.
	 * 
	 * @param rotate_dir
	 *            The player's direction of rotation (-1 for anti-clockwise, 1
	 *            for clockwise, or 0).
	 * @param move_dir
	 *            The player's movement in the car's axis (-1, 0 or 1).
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param use_item
	 * 			  Check if player has pressed ctrl to use item.
	 */
	public void update(double rotate_dir, double move_dir, int delta,
			boolean use_item) throws SlickException {
		// Update all the hazards.
		updateHazards(delta, this);
		// Update all the items.
		updateItems(this, delta, use_item);
		// Rotate and move the player by rotate_dir and move_dir
		player.update(rotate_dir, move_dir, delta, this, use_item);
		// Update all AI players
		updateOpponents(delta);
		// Camera follows the player
		camera.follow(player);
		// Calculate the ranking of the player if the game hasn't finished
		if (Game.end == false) {
			calcRanking();
		}
	}

	/**
	 * Render the entire screen, so it reflects the current game state.
	 * 
	 * @param g
	 *            The Slick graphics object, used for drawing.
	 */
	public void render(Graphics g) throws SlickException {
		// Calculate the camera location (in tiles) and offset (in pixels).
		// Render 24x18 tiles of the map to the screen, starting from the
		// camera location in tiles (rounded down). Begin drawing at a
		// negative offset relative to the screen, to ensure smooth scrolling.
		int cam_tile_x = camera.getLeft() / map.getTileWidth();
		int cam_tile_y = camera.getTop() / map.getTileHeight();
		int cam_offset_x = camera.getLeft() % map.getTileWidth();
		int cam_offset_y = camera.getTop() % map.getTileHeight();
		int screen_tilew = camera.getWidth() / map.getTileWidth() + 2;
		int screen_tileh = camera.getHeight() / map.getTileHeight() + 2;

		map.render(-cam_offset_x, -cam_offset_y, cam_tile_x, cam_tile_y,
				screen_tilew, screen_tileh);
		panel.render(g, player.getRank(), player.getItem());
		// Render the player, AI players, all items, all hazards and end game
		// message
		player.render(camera);
		renderOpponents(camera);
		renderItems();
		renderHazards();
		renderEndMsg(g);
	}

	/**
	 * Get the friction coefficient of a map location.
	 * 
	 * @param pos
	 * 				The point to calculate the friction coefficient.
	 * @return Friction coefficient at that location.
	 */
	public double frictionAt(Point pos) {
		int tile_x = (int) pos.getX() / map.getTileWidth();
		int tile_y = (int) pos.getY() / map.getTileHeight();
		int tileid = map.getTileId(tile_x, tile_y, 0);
		String friction = map.getTileProperty(tileid, MAP_FRICTION_PROPERTY,
				null);
		return Double.parseDouble(friction);
	}

	/**
	 * Determines whether a particular map location blocks movement.
	 * 
	 * @param pos
	 * 				The point to check if the it's blocked
	 * @return true if the tile at that location blocks movement.
	 */

	public boolean checkBlocks(Point pos) {
		return (frictionAt(pos) >= 1);
	}

	/**
	 * Determines whether a kart is within 40 pixels of a specific point.
	 * 
	 * @param pos
	 *            The point to check if the kart collides.
	 * @param kartA
	 *            The kart that is checking for collision.
	 * @return The kart that is collided with the point.
	 */
	public Kart checkCollisions(Point pos, Kart kartA) {
		// check if the kart is within 40 pixels pos not including the kart that
		// is calling.
		if (player.collides(pos) && player != kartA) {
			return player;
		}
		for (Kart kartB : opponents) {
			if (kartB.collides(pos) && kartA != kartB) {
				return kartB;
			}
		}
		return null;
	}

	/**
	 * Create all items and populate the array of items.
	 * 
	 * @throws SlickException
	 */
	private void createItems() throws SlickException {
		int i;
		for (i = 0; i < NUM_ITEMS; i++) {
			if (ITEM_NAME[i].equals("Oil can")) {
				items.add(new Oilcan(ITEM_NAME[i], ITEM_X[i], ITEM_Y[i]));
			} else if (ITEM_NAME[i].equals("Boost")) {
				items.add(new Boost(ITEM_NAME[i], ITEM_X[i], ITEM_Y[i]));
			} else if (ITEM_NAME[i].equals("Tomato")) {
				items.add(new Tomato(ITEM_NAME[i], ITEM_X[i], ITEM_Y[i]));
			}
		}
	}

	/**
	 * Create all waypoints and populate the array of waypoints.
	 * 
	 * @throws SlickException
	 */
	private void createWaypoints() throws SlickException {
		int i;
		for (i = 0; i < NUM_WAYPOINTS; i++) {
			waypoints.add(new Point(WAYPOINT_X[i], WAYPOINT_Y[i]));
		}
	}

	/**
	 * Update all the hazards except for the ones that were destroyed already
	 * 
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the kart is on (to get friction and blocking).
	 */
	public void updateHazards(double delta, World world) {
		for (Hazard hazard : hazards) {
			if (hazard.isDestroyed() == false) {
				hazard.update(world.getPlayer(), delta, world);
			}
		}
	}

	/**
	 * Update all the items
	 * 
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 * @param world
	 *            The world the player is on (to get friction and blocking).
	 * @param use_item
	 *            Check if player uses the item.
	 * @throws SlickException
	 */
	private void updateItems(World world, int delta, boolean use_item)
			throws SlickException {
		ArrayList<Item> items = world.getItems();
		// Check if item is picked up.
		for (Item item : items) {
			if (item.collides(player.getPos())) {
				item.pickUp(player);
			}
		}
		// Use the item.
		if (use_item == true && player.getItem() != null) {
			player.getItem().use(player, delta, world);
		}
	}

	/**
	 * Update all the AI players
	 * 
	 * @param delta
	 *            Time passed since last frame (milliseconds).
	 */
	private void updateOpponents(int delta) {
		for (AIPlayer opponent : opponents) {
			opponent.update(delta, this);
		}
	}

	/**
	 * Render all items on the screen.
	 * 
	 * @throws SlickException
	 */
	private void renderItems() throws SlickException {
		for (Item item : items) {
			// if item is already picked up then don't draw
			if (item.isPickedUp() == false) {
				item.render(camera);
			}
		}
	}

	/**
	 * Render all hazards on the screen.
	 * 
	 * @throws SlickException
	 */
	private void renderHazards() throws SlickException {
		for (Hazard hazard : hazards) {
			// if hazard is already destroyed then don't draw
			if (hazard.isDestroyed() == false) {
				hazard.render(camera);
			}
		}
	}

	/**
	 * Render the end game message when player has reached finish line.
	 * 
	 * @param g
	 */
	private void renderEndMsg(Graphics g) {
		if (player.getY() < FINISH_LINE) {
			g.drawString("You came " + Panel.ordinal(player.getRank()) + "!",
					Game.SCREENWIDTH / 2 - MSG_X_OFFSET,
					Game.SCREENHEIGHT / 2 - MSG_Y_OFFSET);
			Game.end = true;
		} else {
			Game.end = false;
		}
	}

	/**
	 * Render all the AI players
	 * 
	 * @param camera
	 *            Time passed since last frame (milliseconds).
	 */
	private void renderOpponents(Camera camera) {
		for (AIPlayer opponent : opponents) {
			opponent.render(camera);
		}
	}

	/**
	 * Calculate player's ranking according to y-coordinates.
	 */
	private void calcRanking() {
		int rank = 1;
		for (AIPlayer opponent : opponents) {
			if (player.getY() > opponent.getY()) {
				rank++;
			}
		}
		player.setRank(rank);
	}

	/** Get the width of the game world in pixels. */
	public int getWidth() {
		return map.getWidth() * map.getTileWidth();
	}

	/** Get the height of the game world in pixels. */
	public int getHeight() {
		return map.getHeight() * map.getTileHeight();
	}

	/** Get the array of waypoints. */
	public ArrayList<Point> getWaypoints() {
		return waypoints;
	}

	/** Get the array of items. */
	public ArrayList<Item> getItems() {
		return items;
	}

	/** Get the array of hazards. */
	public ArrayList<Hazard> getHazards() {
		return hazards;
	}

	/** Get the player of the game. */
	public Player getPlayer() {
		return player;
	}

}
