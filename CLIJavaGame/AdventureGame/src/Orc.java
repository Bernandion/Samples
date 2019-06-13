/**
 * @(#)Orc.java
 *
 * Child class of Monsters that has a randomly generated int for
 * power and health. Orcs represent the weaker Monsters which
 * the Player encounters in the Map. All data that represents an Orc
 * is contained in the parent class Monsters, and is accessed through 
 * the public methods that are in Monsters which are also available to
 * child classes by inheritance.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
import java.util.*;

public class Orc extends Monsters {
	// constructor that uses the Random object to set the health and power
	// of the Orc object by sending the integer values to the parent constructor
	// in the Monsters class. The health and power are relatively low compared to
	// the other Monsters.
	public Orc() {
		super (rand.nextInt(3) + 7, rand.nextInt(2) + 4);
	}	 
	// returns a String representation of an Orc object.
	// Overrides the abstract toString() in Monsters
	public String toString(){
		return "orc";
	}
}