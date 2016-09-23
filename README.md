# 2D-Kart-Game

In this project, I created a graphical kart racing game, Shadow Kart, in the Java programming language with Slick library. 

Shadow Kart is a kart racing game where one player races against computer-controlled enemies to be the first one to the finish. The player controls Donkey, who is skilled at picking up various items found on the road to use or throw at his opponents. The goal of the game is to reach the finish line first, by any means necessary. Figure 1 shows the game in action.
In this game, the “world” is a two-dimensional grid of tiles. While the map is designed as a narrow track, the player is able to drive freely around the world (but of course, the player cannot drive on trees, mountains, water, and other types of terrain).
During the course of completing this project:
- I have gained experience in designing object-oriented software with simple class diagrams. 
- I have gained experience working with an object-oriented programming language (Java)
- I have learnt simple game programming concepts (user interfaces, collisions, simple AI)


## Project Requirements
There are two parts to the project:
#### Part 1
The first task is to produce a class design, using Draw.io, outlining how I planed to implement the game. This consists of a UML class diagram, showing all of the classes I implemented, the relationships (inheritance and association) between them, and their attributes and primary public methods.
#### Part 2
The second task is to complete the implementation of the game, as described in the manual which follows.

## Project Overview
### Gameplay
The game takes place in frames. Much like a movie, many frames occur per second, so the animation is smooth. Each frame:
1. Each millisecond: all four karts have a chance to accelerate forwards/backwards, rotate left/right, and/or use items. Enemy karts may accelerate or rotate automatically, depending on their racing strategy. The player moves, rotates, and/or uses items if the appropriate keys are being pressed. Then, each kart and on-screen projectile moves according to its current velocity and angle.
2. The “camera” is updated so that it is centered around the player’s position.
3. The entire screen is “rendered,” so the display on-screen reflects the new state of the world.
All of the karts accelerate forwards or backwards at a rate of 0.0005 px/ms, and rotate at a rate of 0.004 radians per millisecond, except in special circumstances explained later. Also, all karts are automatically decelerated by friction, which is dependent on the type of terrain underneath the kart.
### Controls
The game is controlled entirely using the keyboard. The up key causes the player to accelerate in the forward direction; the down key, in the backward direction. The left key rotates the player in the anti-clockwise direction; the right key, in the clockwise direction. If the player is carrying an item, either the left or right control key uses the item.
### Karts
There are four karts in the game, each with di↵erent skills and strategies. Figure 2 shows the starting position of each kart. All karts start at angle 0 radians, which should place all four karts on the starting line, facing north.
The karts are divided into two general types:
- The player (Donkey). Controlled by you; able to pick up and use items. • Enemies. The computer-controlled opponents, shown in Figure 3.
There are three enemy karts, determined to beat you in the race. Each has a di↵erent racing strategy. Enemy karts follow the same basic rules of movement and physics as the player, except that they cannot pick up or use items.
- Elephant – A ruthless fighter from the jungle, Elephant strives to win at all costs. He doesn’t care about Donkey, and simply drives through the course as fast as he can.
- Dog – A man’s best friend, Dog has developed a sense of honour. When she is ahead of Donkey (see Ranking and game ending), she accelerates at 0.9 times the normal rate (0.00045px/ms2), to give you a chance to catch up. But, when she is behind Donkey, she accelerates at 1.1 times the normal rate (0.00055px/ms2), to catch up to you!
- Octopus – Nobody knows much about Octopus, but he seems like kind of a jerk. When he’s between 100 and 250 pixels away from Donkey, he drives straight towards the player! Otherwise, he follows the normal course. Is he even trying to win, or just get in your way?

Enemy karts are always accelerating forwards; they never stop accelerating. The direction of rotation, and the amount to rotate each frame, is determined by the kart’s current destination.
The enemy karts know how to drive from the start to the end of the race track, by following a series of carefully placed waypoints. A waypoint is simply a pixel coordinate on the map. Each computer-controlled kart remembers which waypoint they are planning to visit next (for Octopus, even when following the player, he still remembers the next waypoint). All karts begin by planning to visit the first waypoint. Once a kart is within 250 pixels of the destination waypoint, they forget about that waypoint, and instead plan to visit the next waypoint in the sequence (so karts never actually reach the waypoint – they only get “close enough”).
See the data file data/waypoints.txt for the exact location of each waypoint. See Implemen- tation tips for more details on the waypoint algorithm.
Each frame, the kart rotates as far as possible to point towards the destination point. For Ele- phant and Dog, the destination point is always the next waypoint. For Octopus, this is sometimes the next waypoint, and sometimes the centre of the player’s kart. The kart rotates 0.004 radians per millisecond, or less (if already facing the destination).

### Items and Projectiles
There are a handful of special items (or “power-ups”) scattered throughout the track, as shown in Figure 4. Like karts, each item has an (x,y) coordinate in pixels. Items can be used to slow down enemies or speed up the player, so you stand a better chance of winning the race. Only Donkey (the player) can pick up items.
At any point in time, the player may or may not be holding an item. The player’s current item, if any, is shown in the lower right-hand corner (see The Status Panel). The player can pick up any item by moving within 40 pixels of its position. When picked up, an item will be removed from the world, and be set as the player’s current item. If the player was already carrying an item, the old item will be deleted. The player can use their current item by tapping the control key. Using an item deletes it from the player’s hand, and has some e↵ect, depending on the item type.
- Oil can – Creates an oil slick behind the player. The oil slick is placed 40 pixels away from the player, in the opposite direction to that which the player is facing. This is useful for delaying enemies that are coming up behind you.
- Tomato – Can be thrown at enemies in front of the player. A tomato projectile is created 40 pixels away from the player, in the direction the player is facing. The tomato projectile is given a fixed velocity of 1.7px/ms in the direction the player is facing. This is useful for delaying enemies that are just in front of you.
- Boost – Activates a rocket afterburner that increases the player’s acceleration for a short time. The boost lasts for 3 seconds, and during that time, the player’s acceleration is fixed at 0.0008px/ms2 in the forwards direction (regardless of whether or not the player is pressing the up or down keys). This is useful for catching up to enemies that are far ahead.
