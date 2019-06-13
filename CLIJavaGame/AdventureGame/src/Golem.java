/**
 * @(#)Golem.java
 *
 * Child class of Monsters that has a randomly generated int for
 * power and health. Golems represent the strong Monsters which
 * the Player encounters in the Map. All data that represents a Golem
 * is contained in the parent class Monsters, and is accessed through 
 * the public methods that are in Monsters which are also available to
 * child classes by inheritance.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
import java.util.*;

public class Golem extends Monsters {
	// constructor that uses the Random object to set the health and power
	// of the Golem object by sending the integer values to the parent constructor
	// in the Monsters class. The health and power are relatively high compared to
	// the other Monsters.
	public Golem() {
		super (rand.nextInt(3) + 17, rand.nextInt(2) + 8);
	}
	// returns a String representation of an Golem object
	// Overrides the abstract toString() in Monsters
	public String toString(){
		return "golem";
	}
}