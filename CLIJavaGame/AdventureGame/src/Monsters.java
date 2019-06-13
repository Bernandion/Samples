/**
 * @(#)Monsters.java
 *
 * Parent class that represents Monsters which can appear in each section
 * of the Map. The Monsters fight Player if the Player enters a section of
 * the Map where a Monster is located. Implements CombatMoves to allow for
 * the Monsters to take damage and fight the Player in combat. Also implements
 * Serializable to allow for Monster objects to be serialized and written and
 * read in a file.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
 import java.util.*;
 import java.io.*;

public abstract class Monsters implements CombatMoves, Serializable {
	// constant and static variable needed for the class to be recognized properly when serialized for saving and loading
	private static final long serialVersionUID = 72L;
	// integer variable that represents the health for the specific Monster
	private int health = 0;
	// character variable that represents what the Monster is weak to.
	// Used in combat to determine if a Monster has the same weakness as
	// a Weapon's enchantment, and the Player should have a better chance to deal
	// a critical strike on the Monster
	private char weakness = 'x';
	// integer variable that represents the power for the specific Monster
	private int power = 0;
	// boolean variable that represents if the Monster just dealt a critical
	// strike against the Player with attack() during combat
	private boolean monsterjustcrit = false;
	// creates a new static object of the class Random which can be used by
	// all child classes
	protected static Random rand = new Random();
	// contructor that takes an int for health and power passed from a child constructor 
	// and sets the weakness, health, and power for the created Monster object
    public Monsters(int health, int power) {
    	// random int variable from 0 to 3 which is used to determine what the specific Monster's weakness is
    	int weaknum = rand.nextInt(4);
    	// switch that takes the random weaknum variable to set the weakness
    	switch(weaknum){
    		// if 0, sets the weakness to 'e', representing electricity
			case 0 : weakness = 'e'; break;
			// if 1, sets the weakness to 'f', representing fire
			case 1 : weakness = 'f'; break;
			// if 2, sets the weakness to 'i', representing ice
			case 2 : weakness = 'i'; break;
			// if 3, sets the weakness to 'a', representing arcane
			case 3 : weakness = 'a'; break;
			// if somehow the random int isn't the others, outputs a message
			default : System.out.println("WEAKNUM PROBLEM"); break;
		}
		// sets the health for the Monster to the passed health from the Child constructor
    	this.health = health;
    	// sets the power for the Monster to the passed power from the Child constructor
    	this.power = power;
    }
    // static method that returns an instance of a Comparator object which is called in
    // sortName() to sort Weapons in ascending order according to the String name.
    // The data for the Comparator class is defined in the anonymous inner class
    public static Comparator getNameComparator(){
    	// create an instance of a Comparator class with an anonymous inner class
    	return new Comparator() {
    		// method that takes 2 objects, casts them as Monsters, and returns an int
    		// depending on how the 2 Monster's String names compare to eachother
    		public int compare(Object mon1, Object mon2){
    			String name1 = ((Monsters)mon1).toString();
    			String name2 = ((Monsters)mon2).toString();
    			// swaps their spots only if 1 is returned (spot on left is greater than on right)
    			return (name1.compareTo(name2));
    		}
    	};
    }
    // static method that returns an instance of a Comparator object which is called in
    // sortHealth() to sort Weapons in ascending order according to the integer for power.
    // The data for the Comparator class is defined in the anonymous inner class
    public static Comparator getHealthComparator(){
    	// create an instance of a Comparator class with an anonymous inner class
    	return new Comparator() {
    		// method that takes 2 objects, casts them as Monsters, and returns an int
    		// depending on how the 2 Monster's integer health compare to eachother
    		public int compare(Object mon1, Object mon2){
    			int health1 = ((Monsters)mon1).getHealth();
    			int health2 = ((Monsters)mon2).getHealth();
    			// swaps their spots only if 1 is returned (spot on left is greater than on right)
    			return (health1 < health2 ? -1 : (health1 == health2 ? 0 : 1));
    		}
    	};
    }
    // abstract method to be overriden by child classes
    public abstract String toString();
    // accessor method that returns the integer health for the specific Monster
    public int getHealth(){
    	return health;
    }
    // accessor method that returns the character representing the weakness for the specific Monster
    public char getWeakness(){
    	return weakness;
    }
    // accessor method that returns the integer power for the specific Monster
    public int getPower(){
    	return power;
    }
    // accessor method that returns the boolean variable for if the Monster just dealt a critical strike.
    // Called in combat()
    public boolean monsterJustCrit(){
    	return monsterjustcrit;
    }
    // method that is implemented from CombatMoves and is called in combat() that returns a value
    // for the amount of damage the Monster does based on the current Monster's power
    public int attack(int modifier){
    	// sets the chance for the Monster to deal a critical strike in combat to a random number from 0 to 99
    	int dmgchance = rand.nextInt(100);
    	// if the random integer is less than 25 (25% chance), the Monster deals a critical strike and
    	// does double damage, 2 times its power is returned
    	if (dmgchance<25){
    		// sets monsterjustcrit to true, which allows the Monster to take damage back in combat()
    		// if the Player blocks
    		monsterjustcrit = true;
    		System.out.println("The " + toString() + " deals a critical strike!");
    		// returns the integer value for 2 times the Monster's power
    		return 2*power;
    	}
    	// else the random int is greater or equal to 25 and the Monster doesn't deal a critical strike
    	monsterjustcrit = false;
    	// just returns the integer value of the Monster's power for damage
    	return power;
    }
    // method that is implemented from CombatMoves and is called in combat() that takes a value
    // from the Player's attack() and reduces the Monster's health by the amount of damage passed.
    // The modifier integer is not used because the Monster can't have the damage it takes reduced.
    public void block(int damage, int modifier){
    	health -= damage;
    	// resets monsterjustcrit after the Monster takes damage to keep the block function in combat()
    	// working properly
    	monsterjustcrit = false;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}