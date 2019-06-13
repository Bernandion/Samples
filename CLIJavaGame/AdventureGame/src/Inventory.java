/**
 * @(#)Inventory.java
 *
 * Class that represents the Player's Inventory that contains Weapons
 * and health potions. Allows for Weapons and potions to be taken out and
 * for Weapons to be added to the Inventory. Also manipulates the collection
 * of Weapons and displays them to the user. Implements Serializable to allow
 * for the Class objects to be written and read in a file.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
import java.util.*;
import java.io.*;

public class Inventory implements Serializable {
	// constant and static variable needed for the class to be recognized properly when serialized for saving and loading
	private static final long serialVersionUID = 77L;
	// creates a LinkedList of type Integer that contains the integer values for potions that can be used
	// to increase health
	private LinkedList<Integer> healthpotions = new LinkedList<Integer>();
	// creates an ArrayList of type Weapons that contains Weapon objects which are found throughout the Map
	private ArrayList<Weapons> weapons = new ArrayList<Weapons>();
	// static constant integer that represents the max amount of Weapons which can be contained in the Inventory
	private static final int MAX_SIZE = 3;
	// constructor that populates the list of health potions with 5 potions which each restore 12 health
	public Inventory() {
		for (int i=0; i<5; i++){
			healthpotions.add(12);
		}
	}
	// method that is called in controlInventory() and accessInventory() that uses an iterator to
	// traverse through the ArrayList of Weapons and display the stats of each Weapon
	public void displayWeapons(){
		// outputs the current amount of health potions remaining
		System.out.println(healthpotions.size() + " health potions left.");
		// creates a new iterator which is used to go through the ArrayList
		Iterator<Weapons> it = weapons.iterator();
		while(it.hasNext()){
			// sets the current Weapon to the next Weapon in the ArrayList
			Weapons currentweap = it.next();
			System.out.println("\n\tWeapon #" + currentweap.getWeaponID());
			System.out.println("\tPower: " + currentweap.getPower());
			// takes the character for the enchantment of the Weapon and outputs what the character means
			switch(currentweap.getEnchantment()){
				case 'e' : System.out.println("\tEnchantment: Electricity"); break;
				case 'f' : System.out.println("\tEnchantment: Fire"); break;
				case 'i' : System.out.println("\tEnchantment: Ice"); break;
				case 'a' : System.out.println("\tEnchantment: Arcane"); break;
				default : System.out.println("ENCHANTMENT PROBLEM"); break;
			}
		}
	}
	// method that is called in accessInventory() which is used to return a Weapon
	// with the matching Weapon number for equipping
	public Weapons getWeapon(int weapnum){
		// creates a new iterator which is used to go through the ArrayList
		Iterator<Weapons> it = weapons.iterator();
		while(it.hasNext()){
			// sets the current Weapon to the next Weapon in the ArrayList
			Weapons currentweap = it.next();
			// if the current Weapon matches the passed int Weapon number, removes
			// the Weapon from the ArrayList (take it out of the Inventory), and
			// returns the Weapon to the Player
			if (currentweap.getWeaponID()==weapnum){
				it.remove();
				return currentweap;
			}
		}
		// else the no Weapon matches the passed weapon number, so just outputs a message
		// and returns null (indicating that no Weapon is passed)
		System.out.println("That weapon isn't available!");
		return null;
	}
	// method that is called by addWeapon() (in Player) that takes the passed Weapon
	// and puts it in the Inventory (the ArrayList)
	public void addWeapon(Weapons weapon){
		weapons.add(weapon);
	}
	// method that is called in increaseHealth() that returns the int value for a potion
	// and removes 1 potion from the LinkedList of potions
	public int usePotion(){
		// if there are no more health potions left, just outputs a message and returns nothing
		if (healthpotions.size()==0){
			System.out.println("You are out of health potions!");
			return 0;
		}
		// if there are currently 2 potions, outputs a message saying there is 1 left
		if (healthpotions.size()==2) System.out.println("You have " + ((healthpotions.size())-1) + " potion left.");
		// else there is either 0 potions or more than 1 after 1 is removed and outputs a message saying that
		else System.out.println("You have " + ((healthpotions.size())-1) + " potions left.");
		// removes the last potion from the LinkedList and returns its integer value
		return healthpotions.removeLast();
	}
	// method that is called in accessInventory() that removes a Weapon from the
	// ArrayList based on the passed integer for the Weapon number
	public void deleteWeapon(int weapnum){
		// creates a new iterator for the ArrayList
		Iterator<Weapons> it = weapons.iterator();
		while(it.hasNext()){
			// sets the current Weapon to the new Weapon in the ArrayList
			Weapons currentweap = it.next();
			// if the current Weapon has a Weapon number that matches the passed int,
			// removes the Weapon from the list (drops the Weapon) and exits the method
			if (currentweap.getWeaponID()==weapnum){
				it.remove();
				System.out.println("You dropped the selected weapon.");
				return;
			}
		}
		// else the Weapon with the indicated Weapon number isn't in the ArrayList
		// and a message is outputted
		System.out.println("That weapon isn't available!");
	}
	// method that uses the static sort method in Collections and the defined way to sort
	// the Weapons in the Weapons class to sort the Weapons in acsending order according
	// to each of their power
	private void sortPower(){
		Collections.sort(weapons, Weapons.getPowerComparator());
	}
	// method that uses the static sort method in Collections and the defined way to sort
	// the Weapons in the Weapons class to sort the Weapons in acsending order according 
	// to each of their enchantment's letter
	private void sortEnchantment(){
		Collections.sort(weapons, Weapons.getEnchantComparator());
	}
	// methed that is called in accessInventory() that takes a passed String that was
	// from the user and performs a certain action on the ArrayList of Weapons based
	// on the passed String
	public boolean controlInventory(String action){
		// switch that takes the passed String
    	switch(action){
    		// if the String is "p", sorts the ArrayList according to Weapon's power and displays the list.
    		// Returns true to keep viewing the Inventory
    		case "p" : sortPower(); displayWeapons(); return true;
    		// if the String is "e", sorts the ArrayList according to Weapon's enchantment and displays the list.
    		// Returns true to keep viewing the Inventory
    		case "e" : sortEnchantment(); displayWeapons(); return true;
    		// if the String is "q", returns false to stop viewing the Inventory
    		case "q" : return false;
    		// outputs a message if some how the String passed isn't equal to any of the other cases
    		default : System.out.println("Invalid Entry!"); return true;
    	}
	}
	// method that is called in inventoryFull() (in the Player class) that checks if the ArrayList
	// of Weapons is at its max capacity and returns a boolean depending on if it is or not
	public boolean inventoryFull(){
		// if the ArrayList of Weapons is full, returns true
		if (weapons.size()==MAX_SIZE) return true;
		// if it isn't, returns false
		return false;
	}
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
