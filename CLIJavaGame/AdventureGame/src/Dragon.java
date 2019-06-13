/**
 * @(#)Dragon.java
 *
 * Child class of Monsters that has a randomly generated int for
 * power and health. Dragons represent the very strong Monster which
 * the Player encounters at the very end of the Map. All data that represents a Dragon
 * is contained in the parent class Monsters, and is accessed through 
 * the public methods that are in Monsters which are also available to
 * child classes by inheritance. When a Dragon is defeated, the game is
 * completed.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
import java.util.*;

public class Dragon extends Monsters {
	// constructor that uses the Random object to set the health and power
	// of the Dragon object by sending the integer values to the parent constructor
	// in the Monsters class. The health and power are very high compared to
	// the other Monsters.
	public Dragon() {
		super (rand.nextInt(3) + 22, rand.nextInt(2) + 10);
	}
	// returns a String representation of an Dragon object
	// Overrides the abstract toString() in Monsters
	public String toString(){
		return "dragon";
	}
}