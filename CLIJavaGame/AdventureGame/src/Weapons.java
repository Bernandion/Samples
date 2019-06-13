/**
 * @(#)Weapons.java
 *
 * Class that represents Weapons which are hidden in each section of the Map
 * and can be found by the Player and contained in the Player's Inventory.
 * Each Weapon is used to fight Monster's that are encountered while
 * traversing through the Map. Implements Serializable to allow for objects
 * of the class to be serialized and written and read in a file.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
 import java.util.*;
 import java.io.*;

public class Weapons implements Serializable {
	// constant and static variable needed for the class to be recognized properly when serialized for saving and loading
	private static final long serialVersionUID = 52L;
	// integer variable for the amount of damage the specific Weapon can do
	private int power = 0;
	// integer variable for the weapon number for the specific Weapon. Used
	// to allow the user to choose certain Weapons in the Inventory for different tasks
	private int weaponid = 0;
	// static integer variable that is used to set the specific weaponid for each Weapon
	private static int idcounter = 0;
	// character variable that represents the enchantment for the specific Weapon.
	// Used in combat to determine if an attack on a Monster should have a higher
	// chance to deal a critical strike
	private char enchantment = 'x';
	// constructor that takes a passed integer for the map number and sets the power,
	// weaponid, and enchantment for each Weapon based on the passed integer
    public Weapons(int mapnum) {
    	// increments the idcounter and sets the weapon number to that number
    	weaponid = ++idcounter;
    	// creates a new object of the class Random
    	Random rand = new Random();
    	// switch with the passed map number that sets the power based on the map number
    	// power of the Weapon increases as the map number increases. (The further through
    	// the Map the Player travels, the better Weapons they will find)
    	switch(mapnum){
    		case 1 : power = rand.nextInt(3) + 2; break;
    		case 2 : power = rand.nextInt(3) + 3; break;
    		case 3 : power = rand.nextInt(3) + 4; break;
    		case 4 : power = rand.nextInt(3) + 5; break;
    		case 5 : power = rand.nextInt(3) + 6; break;
    		case 6 : power = rand.nextInt(3) + 7; break;
    		case 7 : 
    		case 8 : 
    		case 9 : 
    		// once the map number gets to 7, all the Weapons have a power of 8 to 10 to
    		// avoid setting the Weapon's power too high and dealing too much damage to Monsters
    		case 10 : power = rand.nextInt(3) + 8; break;
    		// if somehow the map number isn't the others, outputs a message
    		default : System.out.println("MAPNUM PROBLEM"); break;
    	}
    	// sets an int to determine the specific Weapon's enchantment to a random number from 0 to 3
		int enchantnum = rand.nextInt(4);
		// switch with the random int enchantnum to determine what the enchantment is
		switch(enchantnum){
			// if 0, sets the enchantment to 'e', representing electricity
			case 0 : enchantment = 'e'; break;
			// if 1, sets the enchantment to 'f', representing fire
			case 1 : enchantment = 'f'; break;
			// if 2, sets the enchantment to 'i', representing ice
			case 2 : enchantment = 'i'; break;
			// if 3, sets the enchantment to 'a', representing arcane
			case 3 : enchantment = 'a'; break;
			// if somehow the enchantnum isn't the others, outputs a message
			default : System.out.println("ENCHANTNUM PROBLEM"); break;
		}
    }
    // static method that returns an instance of a Comparator object which is called in
    // sortPower() to sort Weapons in ascending order according to power.
    // The data for the Comparator class is defined in the anonymous inner class
    public static Comparator getPowerComparator(){
    	// create an instance of a Comparator class with an anonymous inner class
    	return new Comparator() {
    		// method that takes 2 objects, casts them as Weapons, and returns an int
    		// depending on how the 2 Weapon's power compare to eachother
    		public int compare(Object weap1, Object weap2){
    			int power1 = ((Weapons)weap1).getPower();
    			int power2 = ((Weapons)weap2).getPower();
    			// swaps their spots only if 1 is returned (spot on left is greater than on right)
    			return (power1 < power2 ? -1 : (power1 == power2 ? 0 : 1));
    		}
    	};
    }
    // static method that returns an instance of a Comparator object which is called in
    // sortEnchantment() to sort Weapons in ascending order according to the enchantment char.
    // The data for the Comparator class is defined in the anonymous inner class
    public static Comparator getEnchantComparator(){
    	// create an instance of a Comparator class with an anonymous inner class
    	return new Comparator() {
    		// method that takes 2 objects, casts them as Weapons, and returns an int
    		// depending on how the 2 Weapon's enchantment char compare to eachother
    		public int compare(Object weap1, Object weap2){
    			char enchant1 = ((Weapons)weap1).getEnchantment();
    			char enchant2 = ((Weapons)weap2).getEnchantment();
    			// swaps their spots only if 1 is returned (spot on left is greater than on right)
    			return (enchant1 < enchant2 ? -1 : (enchant1 == enchant2 ? 0 : 1));
    		}
    	};
    }
    // accessor method that returns the integer for the power of the current Weapon
    public int getPower(){
    	return power;
    }
    // accessor method that returns the character for the enchantment of the current Weapon
    public char getEnchantment(){
    	return enchantment;
    }
    // accessor method that returns the integer weapon number (id) of the current Weapon
    public int getWeaponID(){
    	return weaponid;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
