/**
 * @(#)Treant.java
 *
 * Child class of Monsters that has a randomly generated int for
 * power and health. Treants represent the average strength Monsters which
 * the Player encounters in the Map. All data that represents a Treant
 * is contained in the parent class Monsters, and is accessed through 
 * the public methods that are in Monsters which are also available to
 * child classes by inheritance.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
import java.util.*;

public class Treant extends Monsters {
	// constructor that uses the Random object to set the health and power
	// of the Treant object by sending the integer values to the parent constructor
	// in the Monsters class. The health and power are relatively medium compared to
	// the other Monsters.
	public Treant() {
		super (rand.nextInt(3) + 12, rand.nextInt(2) + 6);
	}
	// returns a String representation of an Treant object
	// Overrides the abstract toString() in Monsters
	public String toString(){
		return "treant";
	}
}