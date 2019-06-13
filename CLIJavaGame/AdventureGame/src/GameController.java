/**
 * @(#)GameController.java
 *
 * Class that controls the program by changing and managing
 * data from every class based on user input.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
 import java.util.*;
 import java.io.*;

public class GameController {
	// creates a new object of the class Player to represent a Player that traverses through the Map and fights Monsters
	private Player player = new Player();
	// creates a nested array of objects of the class Map that represents the amount of places the Player needs to
	// traverse and the directions the player can move. The outer array is the amount of places the Player moves and
	// the inner array is the directions the Player can go
	private Map[][] section = new Map[10][3];
	// creates a new object of the class Library to represent a Library of information on different Monsters
	private Library lib = new Library ();
	// int variable that represents the position in the Map the Player is out of the ten possible places they could go.
	// To be used to access the Map object in the outer array element
	private int mapposition = -1;
	// int variable that represents the direction in the Map the Player could go (east, north, west).
	// To be used to access the Map object in the inner array element
	private int mapdirection = -1;
	// String object for part of the nature in the current section of the Map array
	private String naturepart1 = "empty";
	// String object for the other part of the nature in the current section of the Map array
	private String naturepart2 = "empty";
	// Monsters reference that only gets set to a Monsters object of the current section of the Map array has a Monster
	// in it that the Player must fight
	private Monsters fightmonster;
	// boolean variable that is sent to the MainMenu to keep the program running until the game has either been lost or won
	private boolean gamenotover = true;
	// constructor that populates the Map array with different Map objects based on how far they are along the outer array
	public GameController() {
		// integer that is sent to the Map constructor to set information for the Map based on the map position
		int mapnum = 1;
		// popualtes the nested array with Map objects
		for (int i=0; i<10; i++){
			for (int j=0; j<3; j++){
				// creates the Map objects for the certain section based on the mapnum (map position)
				section[i][j] = new Map(mapnum);
			}
			// increments the mapnum to indicate the next position along the Map
			mapnum++;
		}
	}
	// method that displays all the possible commands and the instructions for the game
    public void displayInstructions(){
    	System.out.println("");
    	System.out.println("\t\tINSTRUCTIONS:\nPossible commands:\n\tcheck: rock, bush, tree");
    	System.out.println("\tmove: west, north, east\n\tYou could also use a (P)otion or access your (I)nventory after combat.");
    	System.out.println("Combat commands:\n\t(A)ttack, (B)lock");
    	System.out.println("\t*Note: commands with letters in parentheses need only the indicated letters to be entered*");
    	System.out.println("\n\tWrite the commands with a space between the first and second words.");
    	System.out.println("\tExample: 'check rock'. After you move to a new part of the game, a monster");
    	System.out.println("\tmight appear. If a monster does appear, you will forced into combat before");
    	System.out.println("\tany other action is allowed. You can access your inventory and change your equiped");
    	System.out.println("\tweapon at any time when not in combat. When you (A)ttack");
    	System.out.println("\ta monster, damage is done to its health by whatever the weapon's power is.");
    	System.out.println("\tIf the weapon's enchantment matches the monster's weakness, there is a 70%");
    	System.out.println("\tchance for the weapon to do double damage to the monster. Otherwise there");
    	System.out.println("\tis only a 30% chance for the weapon to do double damage. If you (B)lock, you");
    	System.out.println("\ttake half the damage that the monster would normally do to you to your health.");
    	System.out.println("\tThe monster has a 25% chance to do double damage to you, so if you block and the");
    	System.out.println("\tmonster does double damage, the monster takes its normal power in damage back to itself.");
    	System.out.println("\tAfter combat, you can check the environment for a chance to find");
    	System.out.println("\ta new weapon or you can type (P)otion to use a potion to restore 12 health points.");
    	System.out.println("\tThe most weapons you can hold at once is 3, so if you want another you need to drop");
    	System.out.println("\tone. In order to change your equipped weapon with one that's in your inventory, you");
    	System.out.println("\tmust access your inventory after you find a new weapon and change it from there.");
    	System.out.println("\tAfter 10 parts of the map have been traversed, you will fight a Dragon.");
    	System.out.println("\tIf the dragon is defeated, you win the game! But if you die from a monster at any");
    	System.out.println("\ttime in the game, you lose.");
    }
    // method that takes a user entered character and makes the Player perform a certain action based on what was entered
    public void playerAction(char act){
    	// if the Player hasn't moved anywhere yet, just outputs a message
    	if ((mapposition<0)||(mapdirection<0)){
    		System.out.println("Must move somewhere first!");
    		return;
    	}
    	// switch that takes the entered character to either check part of the nature, or use a potion
    	switch(act){
    		// if 'r', checks the rock in the area for a hidden weapon
    		case 'r' : {
    			// if the 'chance number' indicates that the rock has the weapon, the weapon is not yet found, and the current section of the Map
    			// actually has a rock somewhere, puts the found weapon in the Player's Inventory
    			if (((section[mapposition][mapdirection].getChanceNumber()==0)&&(section[mapposition][mapdirection].isWeaponFound()==false))&&
    				((naturepart1.equals("rock"))||(naturepart2.equals("rock")))){
    				// if the Player's Inventory is at full capacity, outputs a message telling that the user needs to make space
    				if (player.inventoryFull()){
    					System.out.println("Your inventory is full! Drop a weapon to make space.");
    					break;
    				}
    				// gets the weapon from the current section of the map, and puts it in the Player's inventory
    				player.addWeapon(section[mapposition][mapdirection].getWeapon());
    				break;
    			}
    			// if there is no rock in the area, outputs a message telling there isn't a rock
    			else if ((!naturepart1.equals("rock"))&&(!naturepart2.equals("rock"))){
    				System.out.println("There is no rock in the area.");
    				break;
    			}
    			// else just prints a message indicating there is no weapon behind the rock
    			else {
    				System.out.println("You find nothing.");
    				break;
    			}
    		}
    		// if 't', checks the tree in the area for a hidden weapon
    		case 't' : {
    			// if the 'chance number' indicates that the tree has the weapon, the weapon is not yet found, and the current section of the Map
    			// actually has a tree somewhere, puts the found weapon in the Player's Inventory
    			if (((section[mapposition][mapdirection].getChanceNumber()==1)&&(section[mapposition][mapdirection].isWeaponFound()==false))&&
    				((naturepart1.equals("tree"))||(naturepart2.equals("tree")))){
    				// if the Player's Inventory is at full capacity, outputs a message telling that the user needs to make space
    				if (player.inventoryFull()){
    					System.out.println("Your inventory is full! Drop a weapon to make space.");
    					break;
    				}
    				// gets the weapon from the current section of the map, and puts it in the Player's inventory
    				player.addWeapon(section[mapposition][mapdirection].getWeapon());
    				break;
    			}
    			// if there is no tree in the area, outputs a message telling there isn't a tree
    			else if ((!naturepart1.equals("tree"))&&(!naturepart2.equals("tree"))){
    				System.out.println("There is no tree in the area.");
    				break;
    			}
    			// else just prints a message indicating there is no weapon behind the tree
    			else {
    				System.out.println("You find nothing.");
    				break;
    			}
    		}
    		// if 'b', checks the bush in the area for a hidden weapon
    		case 'b' : {
    			// if the 'chance number' indicates that the bush has the weapon, the weapon is not yet found, and the current section of the Map
    			// actually has a bush somewhere, puts the found weapon in the Player's Inventory
    			if (((section[mapposition][mapdirection].getChanceNumber()==2)&&(section[mapposition][mapdirection].isWeaponFound()==false))&&
    				((naturepart1.equals("bush"))||(naturepart2.equals("bush")))){
    				// if the Player's Inventory is at full capacity, outputs a message telling that the user needs to make space
    				if (player.inventoryFull()){
    					System.out.println("Your inventory is full! Drop a weapon to make space.");
    					break;
    				}
    				// gets the weapon from the current section of the map, and puts it in the Player's inventory
    				player.addWeapon(section[mapposition][mapdirection].getWeapon());
    				break;
    			}
    			// if there is no bush in the area, outputs a message telling there isn't a bush
    			else if ((!naturepart1.equals("bush"))&&(!naturepart2.equals("bush"))){
    				System.out.println("There is no bush in the area.");
    				break;
    			}
    			// else just prints a message indicating there is no weapon behind the bush
    			else {
    				System.out.println("You find nothing.");
    				break;
    			}
    		}
    		// if 'p', uses a potion to increase Player health
    		case 'p' : player.increaseHealth(); break;
    		// if the entered character doesn't match anything, outputs a message indicating a problem
    		default : System.out.println("PLAYER ACTION PROBLEM"); break;
    	}
    }
    // method that takes a user entered character to move the Player either west, north, or east.
    // Returns true if a Monster is found to initiate combat, otherwise returns false
    public boolean playerMover(char act){
    	// switch that takes the user entered char to move the Player
    	switch(act){
    		// if 'w', moves the player 1 more place out 10 through the Map to the west
    		case 'w' : {
    			// increments the map position 1 more place out of 10
    			mapposition++;
    			// sets the map direction to 0, representing west in the inner part of the section array for Map objects
    			mapdirection = 0;
    			System.out.println("You move to the west.");
    			// sets the Monster for the current section of the Map to either a Monster object or null
    			fightmonster = section[mapposition][mapdirection].getMonster();
    			// if there is no Monster in the section, returns false
    			if (fightmonster==null) return false;
    			// if there is a Monster, prints a message showing the Monster and calls the monsterPlayerInfo()
    			// method to allow the user to see the Monster's and Player's stats to help the user decide what
    			// to do with the Monster
    			System.out.println("A " + fightmonster + " has appeared!");
    			monsterPlayerInfo();
    			// returns true
    			return true;
    		}
    		// if 'n', moves the player 1 more place out 10 through the Map to the north
    		case 'n' : {
    			// increments the map position 1 more place out of 10
    			mapposition++;
    			// sets the map direction to 1, representing north in the inner part of the section array for Map objects
    			mapdirection = 1;
    			System.out.println("You move to the north.");
    			// sets the Monster for the current section of the Map to either a Monster object or null
    			fightmonster = section[mapposition][mapdirection].getMonster();
    			// if there is no Monster in the section, returns false
    			if (fightmonster==null) return false;
    			// if there is a Monster, prints a message showing the Monster and calls the monsterPlayerInfo()
    			// method to allow the user to see the Monster's and Player's stats to help the user decide what
    			// to do with the Monster
    			System.out.println("A " + fightmonster + " has appeared!");
    			monsterPlayerInfo();
    			// returns true
    			return true;
    		}
    		// if 'e', moves the player 1 more place out 10 through the Map to the east
    		case 'e' : {
    			// increments the map position 1 more place out of 10
    			mapposition++;
    			// sets the map direction to 2, representing east in the inner part of the section array for Map objects
    			mapdirection = 2;
    			System.out.println("You move to the east.");
    			// sets the Monster for the current section of the Map to either a Monster object or null
    			fightmonster = section[mapposition][mapdirection].getMonster();
    			// if there is no Monster in the section, returns false
    			if (fightmonster==null) return false;
    			// if there is a Monster, prints a message showing the Monster and calls the monsterPlayerInfo()
    			// method to allow the user to see the Monster's and Player's stats to help the user decide what
    			// to do with the Monster
    			System.out.println("A " + fightmonster + " has appeared!");
    			monsterPlayerInfo();
    			// returns true
    			return true;
    		}
    		// if the entered character doesn't match anything, outputs a message indicating a problem
    		default : System.out.println("PLAYER MOVE PROBLEM"); break;
    	}
    	// return false if for some reason the switch doesn't operate
    	return false;
    }
    // method that initiates once a Monster is found in playerMover().
    // Takes user entered character to perform combat actions and either returns true
    // if nothing is dead yet, or false if either the Player or Monster is killed.
    // Has the possibility to provide conditions for either winning or losing the game.
    public boolean combat(char action) throws InterruptedException{
    	// if the inputted character is 'a', performs the action for the Player attacking the Monster
    	if (action=='a'){
    		// since the Player is attacking, checks if the Player's equipped Weapon's enchantment matches
    		// the Monster's weakness, and if it does sends 1 to the parameters of player.attack(). Sends the
    		// value for damage to the Monster as the return value of player.attack() in the fightmonster.block()
    		// parameters, along with a 0 indicating that there is no modification to the Monster blocking
    		fightmonster.block(player.attack((player.getEquippedWeap().getEnchantment()==fightmonster.getWeakness())? 1 : 0),0);
    		// makes the Player take damage from the Monster with no modification to the Monster's damage
    		// (0 in fightmonster.attack() parameters), and a 0 is sent to player.block() to indicate that
    		// there is no modification to the player taking damage
    		player.block(fightmonster.attack(0),0);
    	}
    	// if the inputted character is 'b', performs the action for the Player blocking the Monster's attack
    	else if (action=='b'){
    		// the Player takes damage from the Monster's attack with no modification to the Monster's damage,
    		// but the 1 is also sent in the player.block() parameters to indicate that the amount of damage
    		// taken should be modified
    		player.block(fightmonster.attack(0),1);
    		// checks if the Monster just dealt a critical strike when the Player just blocked the attack, and
    		// if it did, then the Monster takes damage to itself for whatever it's power is
    		if (fightmonster.monsterJustCrit()){
    			// the Monster takes damage for whatever its power is and there is no modification to its blocking
    			fightmonster.block(fightmonster.getPower(),0);
    			// prints a message telling that the Monster takes some damage back
    			System.out.println("The monster takes back " + fightmonster.getPower() + " damage.");
    		}
    	}
    	// if the entered character doesn't match 'a' or 'b', prints a message telling the command is wrong
    	else {
    		System.out.println("Invalid command.");
    		// return true to allow the user to enter something else
    		return true;
    	}
    	// if the Player's health is equal to or less than 0 after they take damage, the Player is dead and the game is over
    	if (player.getHealth()<=0){
    		System.out.println("\nThe monster has killed you.\nGAME OVER!");
    		// freezes the program for 5 seconds to allow the user to see that they lost before the program closes
    		// (throws InterruptedException)
    		Thread.sleep(5000);
    		// changes gamenotover to false, which gets sent to MainMenu and ends the program
    		gamenotover = false;
    		// returns false to end the current combat
    		return false;
    	}
    	// if the current Monster's health is less than or equal to 0 after it takes damage, and the Monster is the Dragon,
    	// then the Player has killed the Dragon and the game has been won
    	if ((fightmonster.getHealth()<=0)&&(fightmonster.toString()=="dragon")){
    		System.out.println("\nThe dragon has been defeated!\nCongratulations, YOU WIN!");
    		// freezes the program for 5 seconds to allow the user to see that they won before the program closes
    		// (throws InterruptedException)
    		Thread.sleep(5000);
    		// changes gamenotover to false, which gets sent to MainMenu and ends the program
    		gamenotover = false;
    		// returns false to end the current combat
    		return false;
    	}
    	// if the Monster's health is less than or equal to 0 after it takes damage, then the current Monster
    	// is dead and the Player is free to 'check' the current section of the Map
    	if (fightmonster.getHealth()<=0) {
    		// prints a message indicating that the current Monster has been killed
    		System.out.println("The " + fightmonster + " has been defeated!");
    		// returns false to end the current combat
    		return false;
    	}
    	// if it gets to this part, then the Monster is not dead yet, so the Player's and Monster's stats
    	// are shown with monsterPlayerInfo() to help the user decide what to do
    	monsterPlayerInfo();
    	// returns true to allow the user to choose another combat command to fight the Monster
    	return true;
    }
    // method that allows the user to observe the Player's Inventory and control the Inventory
    // based on the entered String. Returns true to keep the Inventory open or false to close
    // the inventory
    public boolean openInventory(String action) throws NumberFormatException{
    	// sends the user entered String to accessInventory() in Player to get a bool based on what the 
    	// entered String was
    	boolean runinventory = player.accessInventory(action);
    	// returns the resulting bool
    	return runinventory;
    }
    // method that allows the user to observe and control the Library based on the entered String.
    // Returns true to keep viewing the Library or flase to stop viewing the Library
    public boolean displayLibrary(String choice){
    	// sends the user entered String to controlLibrary in Library to get a bool based on what the
    	// entered string was 
        boolean runlib = lib.controlLibrary(choice);
        // returns the resulting bool
        return runlib;
    }
    // method that uses serialization to save the Player and Map objects to a file, along with a
    // String representation for the map position and direction
    public void saveGame() throws IOException{
    	// creates a new object of the class ObjectOutputStream with a FileOutputStream object wrapped in
    	// the constructor for the file "GameData.txt" to allow for Objects to be written to "GameData.txt"
    	ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("GameData.txt"));
    	// writes the Player object to the file
    	out.writeObject(player);
    	// writes the entire nested array of Map objects to a file
    	out.writeObject(section);
    	// Creates a String representation of the map position and direction for storage uses and concatenates them
    	// to eachother to allow for them to be easily seperated and set as integers again
    	String concatnums = Integer.toString(mapposition) + Integer.toString(mapdirection);
    	// writes the String for the map info to the file
    	out.writeObject(concatnums);
    	// flushes and closes the stream
    	out.close();
    	System.out.println("Game has been saved.");
    }
    // method that uses serialization to load the Player and Map objects from a file and sets them to
    // the current player and section references to allow for a previous game to be continued. Also
    // sets the current mapposition and mapdirection variables to the numbers in the String that is read
    // from the file
    public void loadGame() throws IOException, FileNotFoundException, ClassNotFoundException, NumberFormatException{
    	// creates a new object of the class ObjectInputStream with a FileInputStream object wrapped in
    	// the constructor for the file "GameData.txt" to allow for Objects to be read from "GameData.txt"
    	ObjectInputStream in = new ObjectInputStream(new FileInputStream("GameData.txt"));
    	// sets the current Player reference to the Player object on the file
    	this.player = (Player)in.readObject();
    	// sets the current nested array of Map reference to the Map objects on the file
    	this.section = (Map[][])in.readObject();
    	// sets a String to the String object of the numbers for the map info on the file
    	String concatnums = (String)in.readObject();
    	// sets the mapposition variable to the integer on the left half of the String
    	this.mapposition = Integer.parseInt(concatnums.substring(0,(concatnums.length()/2)));
    	// sets the mapdirection variable to the integer on the right half of the String
    	this.mapdirection = Integer.parseInt(concatnums.substring(concatnums.length()/2));
    	// flushes and closes the stream
    	in.close();
    	System.out.println("Previous game has been loaded.");
    }
    // method that outputs the information for the nature in the area of the current section of the Map
    public void displayArea() {
    	// prints out the Player's health and Map progress before displaying the area
    	System.out.println("\nCurrent Health: " + player.getHealth() + "\t\t\t" + (mapposition+1) + "/10 ways through the map.");
    	// if the Player hasn't moved anywhere yet (just started the game), outputs a message
    	if (mapposition == -1){
    		System.out.println("Welcome brave hero! Enter a command on the keyboard.");
    	}
    	// else displays the information for the area based on if there is only 1 or 2 parts of nature
    	else {
    		// crates a String object and sets it to the nature description String for the current Map section
    		String nature = section[mapposition][mapdirection].description();
    		// switch the prints certain messages based on the length of the nature String
    		switch(nature.length()){
    			// if the String only has one part of nature, displays a message telling of it
    			case 4 : {
    				System.out.println("You see a " + nature + " in the area.");
    				// sets the nature parts for the current section of the Map to the nature and nothing
    				naturepart1 = nature;
    				naturepart2 = "empty";
    				break;
    			}
    			// if the String has 2 parts of nature, displays a message telling of both of them
    			case 8 : {
    				// sets the nature parts for the current section of the Map to the first half and second half of the nature String
    				naturepart1 = nature.substring(0,4);
    				naturepart2 = nature.substring(4,8);
    				System.out.println("You see a " + naturepart1 + " and a " + naturepart2 + " in the area.");
    				break;
    			}
    			// if the length of the nature String is not 4 or 8, then outputs a message indicating a problem
    			default : System.out.println("NATURE LENGTH PROBLEM"); break;
    		}
    	}
    	// outputs all the information for the Player's current equipped weapon to the screen to allow
    	// for the user to decide if they want to change their Weapon or not
    	System.out.println("\n\t\t\tEquipped Weapon:");
    	System.out.println("\t\t\tWeapon #" + player.getEquippedWeap().getWeaponID());
    	System.out.println("\t\t\tPower: " + player.getEquippedWeap().getPower());
    	switch(player.getEquippedWeap().getEnchantment()){
			case 'e' : System.out.println("\t\t\tEnchantment: Electricity"); break;
			case 'f' : System.out.println("\t\t\tEnchantment: Fire"); break;
			case 'i' : System.out.println("\t\t\tEnchantment: Ice"); break;
			case 'a' : System.out.println("\t\t\tEnchantment: Arcane"); break;
			default : System.out.println("ENCHANTMENT PROBLEM"); break;
		}
    }
    // method that returns the boolean variable gamenotover to be used to keep the program running 
    public boolean gameNotOver(){
    	return gamenotover;
    }
    // method that is used in combat() and playerMover() to display information on the current Monster
    // to be faught and the Player wherever appropriate to allow for the user to decide on how they
    // want to fight the Monster
    private void monsterPlayerInfo(){
    	System.out.println("\nMonster:\t\t\t\t\t\tPlayer:");
    	System.out.println("Health: " + fightmonster.getHealth() + "\t\t\t\t\t\tHealth: " + player.getHealth());
    	System.out.println("Power: " + fightmonster.getPower() + "\t\t\t\t\t\tPower: " + player.getEquippedWeap().getPower());
    	switch(fightmonster.getWeakness()){
			case 'e' : System.out.print("Weakness: Electricity"); break;
			case 'f' : System.out.print("Weakness: Fire"); break;
			case 'i' : System.out.print("Weakness: Ice"); break;
			case 'a' : System.out.print("Weakness: Arcane"); break;
			default : System.out.println("WEAKNESS PROBLEM"); break;
		}
		switch(player.getEquippedWeap().getEnchantment()){
			case 'e' : System.out.println("\t\t\t\tEnchantment: Electricity"); break;
			case 'f' : System.out.println("\t\t\t\tEnchantment: Fire"); break;
			case 'i' : System.out.println("\t\t\t\tEnchantment: Ice"); break;
			case 'a' : System.out.println("\t\t\t\tEnchantment: Arcane"); break;
			default : System.out.println("ENCHANTMENT PROBLEM"); break;
		}
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
