# HW3 - Projectiles

For this assignment, we will implement a simple space-invaders style game
where the user controls a ship at the bottom of the screen and aims at targets
at the top.  Ships should be destroyed when hit.  When all ships are
destroyed, spawn a new fleet of enemies and continue.  

You are encouraged to use our common `game_package`, and this assignment
should look for this project in Eclipse when imported.  If your code does not
use this shared package, then make sure to turn in all source code.


# Steps

1. Design Player class and have it move left and right in response to the
   arrow keys.  When SPACE is pressed, create a new Bullet at the appropriate
   current location and add this to the shared bullets list.
    
2. Modify Bullet to make it move vertically, deactivating itself when it
   leaves the screen.  If it intersects a Target, destroy the target.
    
3. Design Target and lay some out in the field.
    
4. Submit your work.
    

