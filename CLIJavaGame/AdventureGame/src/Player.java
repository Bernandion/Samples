/**
 * @(#)Player.java
 *
 * Class that represents a player that can traverse the Map,
 * and fight Monsters with an Inventory, a Weapon, and a certain
 * amount of health. Implements CombatMoves to allow for the Player
 * to take damage and fight Monsters in combat, and implements 
 * Serializable to allow for the class to be serialized and saved
 * on a file.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
 import java.util.*;
 import java.io.*;

public class Player implements CombatMoves, Serializable {
	// constant and static variable needed for the class to be recognized properly when serialized for saving and loading
	private static final long serialVersionUID = 25L;
	// creates a new object of the class Weapons to represent the starting weapon that the Player has before the move
	// anywhere. 1 is passed to the constructor of Weapons to create a Weapon of the lowest power
	private Weapons weapon = new Weapons(1);
	// creates a new object of the class Inventory that represents an Inventory which the Player can store their items in
	private Inventory inventory = new Inventory();
	// int variable for the starting and max health of the Player. If this goes to 0 then the player is dead
	private int health = 70;
    // method that is called when some part of nature is 'checked' with playerAction(). Takes whatever Weapon that
    // is passed in the parameters and puts it in the Inventory
    public void addWeapon(Weapons addition){
    	// In the case that the Weapon passed is the Weapon that is currently equipped (when the user wants
    	// to equip a different Weapon, so the currently equipped Weapon is put in the Inventory), outputs a message
    	if (addition==weapon){
    		System.out.println("You put your equipped weapon in your inventory.");
    	}
    	// outputs a message when a new Weapon is found and put in the Inventory
    	else {
    		System.out.println("You found a weapon and put it in your inventory.");
    	}
    	// adds the Weapon passed in the parameters to the Inventory
    	inventory.addWeapon(addition);
    }
    // method that is called in playerAction() that increases the Player's health by using a health potion
    // from the Inventory
    public void increaseHealth(){
    	// if the Player's current health is equal to or less than 58, adds the int value of the potion to
    	// the health.
    	if (health<=58){
    		health += inventory.usePotion();
    	}
    	// else the Player's current health is greater than 58, so using a health potion would make the health
    	// greater than 70. So instead just removes a health potion for the Inventory and sets the Player's
    	// health to 70 (max)
    	else {
    		int throwawayhealth = inventory.usePotion();
    		health = 70;
    	}
    	// prints a message for the new health value
    	System.out.println("Health now at " + health + ".");
    }
    // method that is called by openInventory() that performs a certain action with the Player's Inventory
    // based on the user entered String that is passed in the parameters
    public boolean accessInventory(String action) throws NumberFormatException{
    	// bool to be returned to determine if the Inventory should stay open or not. Set to true to
    	// keep the Inventory open automatically unless changed
    	boolean keepinventoryopen = true;
    	// if the passed String is equal to 'p', 'e', or 'q', sends the String to controlInventory()
    	// to perform a certain action
    	if ((action.equals("p"))||(action.equals("e"))||(action.equals("q"))){
    		// sets keepinventoryopen to the return value from controlInventory()
    		keepinventoryopen = inventory.controlInventory(action);
    	}
    	// if the first letter in the String is 'e' and the second  letter is 'q', seperates
    	// the numbers (that must be there) from the String to equip a certain Weapon based on
    	// the numbers
    	else if ((action.charAt(0)=='e')&&(action.charAt(1)=='q')){
    		// seperates the numbers from the letters into another String
    		String numbers = action.substring(2);
    		// parses the numbers from the String into an integer (throws NumberFormatException)
    		int weapnum = Integer.parseInt(numbers);
    		// gets the Weapon to equip from the Inventory with the getWeapon(int) method
    		Weapons equipweap = inventory.getWeapon(weapnum);
    		// if the Weapon was properly retrieved from the Inventory...
    		if (equipweap!=null){
    			// puts the currently equipped Weapon into the Inventory
    			addWeapon(this.weapon);
    			// sets the Weapon the Player equips to the new Weapon that was retrieved from the Inventory
    			this.weapon = equipweap;
    			System.out.println("You equip the selected weapon.");
    		}
    		// displays the contents of the Inventory
    		inventory.displayWeapons();
    	}
    	// if the first letter in the Strng is 'd', seperates the numbers (that must be there)
    	// from the 'd' to drop (delete) a certain Weapon based on the entered numbers
    	else if (action.charAt(0)=='d'){
    		// seperates the numbers from the letter into another String
    		String numbers = action.substring(1);
    		// parses the numbers from the String into an integer (throws NumberFormatException)
    		int weapnum = Integer.parseInt(numbers);
    		// attempts to delete the Weapon from the Inventory that matches the weapnum
    		inventory.deleteWeapon(weapnum);
    		// displays the contents of the Inventory
    		inventory.displayWeapons();
    	}
    	// else if the passed String doesn't match, outputs a message indicating that a new String needs
    	// to be entered
    	else System.out.println("Invalid Entry!");
    	// returns the boolean value of keepinventoryopen
    	return keepinventoryopen;
    }
    // method that is called in playerAction() and checks if the Inventory is at max capacity and returns a 
    // boolean variable depending on if it is or isn't
    public boolean inventoryFull(){
    	// calls inventoryFull(), returns true if it is full, false if it isn't
    	return inventory.inventoryFull();
    }
    // method that is implemented from CombatMoves and is called in combat() that returns a value
    // for the amount of damage the Player does based on the currently equipped Weapon's power
    public int attack(int modifier){
    	// creates a new object of the class Random
    	Random rand = new Random();
    	// gets a random integer value from 0 to 99, used for determining if the Player deals a
    	// critical strike or not
    	int dmgchance = rand.nextInt(100);
    	// if a number is passed that isn't 0 as a modifier (the enchantment matches the Monster's
    	// weakness) and the random int is less than 70, or there is no modifier and the random int
    	// is less than 30, a value of 2 times the equipped Weapon's power is returned indicating
    	// that the Player deals a critical strike to the Monster
    	if (((modifier!=0)&&(dmgchance<70))||((modifier==0)&&(dmgchance<30))){
    		System.out.println("You deal a critical strike for " + 2*weapon.getPower() + " damage!");
    		return 2*weapon.getPower();
    	}
    	// else just returns the currently equipped Weapon's power indicating that the Player
    	// didn't deal a critical strike
    	System.out.println("You deal " + weapon.getPower() + " damage.");
    	return weapon.getPower();
    }
    // method that is implemented from CombatMoves and is called in combat() that takes a value
    // from the Monsters attack() and a number for the modifier if the user chooses to block the
    // Monster's attack and changes the value for the Player's health based on the damage
    public void block(int damage, int modifier){
    	// if a number other than 0 is passed as a modifier (the Player blocks the attack),
    	// sets the Player's health to the Player's health minus half the Monster's damage
    	if (modifier!=0){
    		health -= damage/2;
    		System.out.println("You block the attack and take " + damage/2 + " damage.");
    	}
    	// else no number is passed as a modifier (the Player didn't block), so just sets
    	// the Player's health to the Player's health minus half the Monster's damage
    	else {
    		health -= damage;
    		System.out.println("You take " + damage + " damage.");
    	}
    }
    // method that is called in combat() to determine what the Player's health is during combat
    public int getHealth(){
    	return health;
    }
    // method that is called in combat() to determine if the Weapon's enchantment matches the Monster's
    // weakness during combat
    public Weapons getEquippedWeap(){
    	return weapon;
    }    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
