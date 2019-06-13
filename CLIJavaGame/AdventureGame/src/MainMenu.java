/**
 * @(#)MainMenu.java
 *
 * AdventureGame application
 *
 * Menu class that takes input from the user to perform different
 * actions through the program based on what the user does. Closes
 * the loop when either run is false or gameNotOver is false.
 *
 * * FILE OF A GAME IN PROGRESS TO BE LOADED AND TESTED IS CALLED "GameData.txt". *
 * *              THE GAME WILL BE ABOUT HALF WAY THROUGH THE GAME.               *
 * 
 * Run Instructions:
 *	   	1. Press 'd' to view game instructions to get a better understanding of how the game works.
 *		2. Press 'l' to load the saved game progress. The saved game will have an inventory at max capacity.
 *		3. Press 'v' to view the Library of Monsters. Sort the list and type a name to find a Monster.
 *		4. Quit the Library and type "check x", where x is whatever parts of nature that are in the
 *		   current area are to find a Weapon.
 *		5. When a weapon is found, the Inventory will be at max capacity so the Weapon won't be added to
 *		   the Inventory, so press 'i' to view the Inventory.
 *		6. Sort the Inventory, and type "d" and the number of a Weapon's "weapon #" to drop a Weapon to make space.
 *		7. Quit the Inventory, and check the part of nature that the Weapon was in previously to put the new Weapon in the Inventory
 *		8. Press 'i' again to access the Inventory, sort the Inventory, and type "eq" and the number of
 * 		   a Weapon's "weapon #" to equip the specified Weapon and put the previously equipped Weapon
 *		   in the Inventory.
 *		9. Quit the Inventory, and type "move x", where x is either west, north, or east to move to a new part
 *		   of the Map.
 *		10. If a Monster doesn't appear, move again. If a Monster does appear, type either 'a' or 'b' to
 *		    either attack the Monster, or block the Monster. Attacking the Monster has a chance to do double
 *			damage and blocking returns the Monster's damage to it if it deals a critical strike, while also
 *			reducing the amount of damage you take by half.
 *		11. After combat, press 'p' to use a potion and increase the Player's health if needed.
 *		12. Press 's' to save the current game progress to the "GamaData.txt" file.
 *		13. Press 'q' to quit the game.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
 import java.util.*;
 import java.io.*;
 
public class MainMenu {
	// creates an object of the class GameController to send commands from the keyboard to control the game
	private GameController controller = new GameController();
	// game runs while while run variable is true
	private boolean run = true;
	// method to run the program until user command to quit or game over condition is met
	private void run(){
		// keeps iterating to run the program while run is true and a game over condition isn't met
		while((run)&&(controller.gameNotOver())){
			try {
				String choice = "empty";
				// displays the current area of the Map
				controller.displayArea();
				// displays the different possible choices for user input to control the game
				displayChoices();
				// calls the getChoice method to get user input for a String to send to the GameController
				choice = getChoice();
				// switch that takes user inputted String and calls a certain GameController method based on what is entered
				switch(choice){
					// displays the instructions for the game, including possible user entered commands
					case "d" : controller.displayInstructions(); break;
					// iterates to keep viewing the library while allowing for user input until false is returned
					case "v" : while(controller.displayLibrary(getLibraryChoice())); break;
					// iterates to keep the inventory open while allowing for user input until false is returned
					case "i" : while(controller.openInventory(getInventoryChoice())); break;
					// calls the saveGame method to write the objects to a file
					case "s" : controller.saveGame(); break;
					// calls the loadGame method to read objects from a file to continue game progress
					case "l" : controller.loadGame(); break;
					// sends the 'p' character to playerAction to use a potion and increase Player health
					case "p" : controller.playerAction('p'); break;
					// sends the 'r' character to playerAction to check if a rock in the area has a hidden weapon
					case "check rock" : controller.playerAction('r'); break;
					// sends the 't' character to playerAction to check if a rock in the area has a hidden weapon 
					case "check tree" : controller.playerAction('t'); break;
					// sends the 'b' character to playerAction to check if a rock in the area has a hidden weapon
					case "check bush" : controller.playerAction('b'); break;
					// sends the 'w' character to playerMover to move the Player's position 1 way through the map to the west
					case "move west" : {
						// if there is a Monster in the next section of the Map the Player has moved to, true is returned
						// to initiate combat
						if (controller.playerMover('w')){
							// keeps iterating to allow continuous user input until false is returned when the Monster is
							// either killed, the Player dies to lose the game, or the Player kills a Dragon to win the game
							while (controller.combat(getCombatChoice()));
						} 
						break;
					}
					// sends the 'n' character to playerMover to move the Player's position 1 way through the map to the north
					case "move north" : {
						// if there is a Monster in the next section of the Map the Player has moved to, true is returned
						// to initiate combat
						if (controller.playerMover('n')){
							// keeps iterating to allow continuous user input until false is returned when the Monster is
							// either killed, the Player dies to lose the game, or the Player kills a Dragon to win the game
							while (controller.combat(getCombatChoice()));
						} 
						break;
					}
					// sends the 'e' character to playerMover to move the Player's position 1 way through the map to the east
					case "move east" : {
						// if there is a Monster in the next section of the Map the Player has moved to, true is returned
						// to initiate combat
						if (controller.playerMover('e')){
							// keeps iterating to allow continuous user input until false is returned when the Monster is
							// either killed, the Player dies to lose the game, or the Player kills a Dragon to win the game
							while (controller.combat(getCombatChoice()));
						} 
						break;
					}
					// if "q" is entered, run is set to false to end the loop and close the program
					case "q" : run = false; break;
					// if the user input doesn't match anything, outputs a message to indicate that the input is wrong
					default : System.out.println("Command not available!"); break;
				}
			} catch (FileNotFoundException e){
				// prints a message indicating that the file is not found for loading a game
				System.out.println("ERROR: File not Found");
			} catch (ClassNotFoundException e){
				// prints a message indicating that the class is not found for loading a game
				System.out.println("ERROR: Class not Found");
			} catch (IOException e){
				// prints a message indicating that a class isn't being serialized properly for loading or saving
				System.out.println("ERROR: Serialization Problem");
			} catch (NumberFormatException e){
				// prints out the stack trace for the error if there is some problem with parseInt somewhere
				e.printStackTrace(System.err);
			} catch (InterruptedException e){
				// prints out the stack trace for the error if there is some problem with Thread.sleep somewhere
				e.printStackTrace(System.err);
			}
		}
	}
	// method that displays the possible choices for general user input
	private void displayChoices(){
		System.out.println("");
		System.out.println("(D)isplay instructions, (V)iew monster library, (I)nventory, use (P)otion, (S)ave game, (L)oad game, (Q)uit game");
		System.out.println(" OR type a command!");
		System.out.print("--> ");
	}
	// method that returns a String for whatever the user inputted on the keyboard
	private String getChoice(){
		// creates a String for user input
		String input = "empty";
		// creates a new object of the class Scanner for user input
		Scanner in = new Scanner(System.in);
		try {
			// tries to set the entire inputted String to lower case
			input = in.nextLine().toLowerCase();
		} catch (Exception e) {
			// if there is some problem with setting the String to lower case, prints the stack trace for the exception
			e.printStackTrace(System.err);
		}
		System.out.println("");
		// returns the String
		return input;
	}
	// method to be used with the parameters for controller.displayLibrary() that returns a String for a possible command
	// to control the library
	private String getLibraryChoice(){
		System.out.println("");
		// prints the possible commands for controlling the library
		System.out.println("Sort by: (N)ame, (H)ealth; (Q)uit the library");
    	System.out.println("... or type the specific name of the monster.");
    	System.out.print("--> ");
    	// creates a String for user input
		String input = "empty";
		// creates a new object of the class Scanner for user input
		Scanner in = new Scanner(System.in);
		try {
			// tries to set the next token of the inputted String to lower case
			input = in.next().toLowerCase();
		} catch (Exception e) {
			// if there is some problem with setting the String to lower case, prints the stack trace for the exception
			e.printStackTrace(System.err);
		}
		System.out.println("");
		// return the String
		return input;
	}
	// method to be used with the parameters for controller.openInventory() that keeps iterating until an acceptable String
	// is inputted to be used to control the Inventory and returns the String
	private String getInventoryChoice(){
		// keeps iterating if there is a problem with parsing the integer from the inputted String if the user wants to either
		// delete a weapon or equip a weapon
		while(true){
			System.out.println("");
			// prints the possible commands for controlling the library
			System.out.println("What would you like to do?");
    		System.out.println("Sort by: (P)ower, (E)nchantment; (Q)uit the inventory");
    		System.out.println("... or type (D) followed by the weapon number of a weapon to delete.");
    		System.out.println("... or type (EQ) followed by the weapon number of a weapon to equip.");
    		System.out.print("--> ");
    		// creates a String for user input
			String input = "empty";
			// creates a new object of the class Scanner for user input
			Scanner in = new Scanner(System.in);
			try {
				// tries the set the entire user input to lower case
				input = in.nextLine().toLowerCase();
				// removes all spaces by replacing them with "", which is no spaces
				input = input.replaceAll("\\s+","");
				// if the user doesn't input anything and presses enter, allows the user to input something again
				if (input.equals("")) continue;
				// same as part of the accessInventory method in Player, but tests if the String has a parseable integer
				// to be used with equipping a Weapon before sending it down to the GameController
				if ((input.charAt(0)=='e')&&(input.charAt(1)=='q')){
					// if the first to letters in the String are 'e' and 'q', tries to parse the rest to an int
					int testnums = Integer.parseInt(input.substring(2));
				}
				// same as part of the accessInventory method in Player, but tests if the String has a parseable integer
				// to be used with dropping a Weapon before sending it down to the GameController
				else if (input.charAt(0)=='d'){
					// if the first letter in the String is a 'd', tries to parse the rest to an int
					int testnums = Integer.parseInt(input.substring(1));
				}
			} catch (NumberFormatException e) {
				// outputs a message and allows the user to input something else if the String isn't parseable where
				// it is supposed to be
				System.out.println("\nInvalid Entry!");
				continue;
			} catch (Exception e) {
				// prints the stack trace of the error if any other Exception is caught
				e.printStackTrace(System.err);
			}
			System.out.println("");
			// returns the String to be used to control the Inventory
			return input;
		}
	}
	// method to be used as parameters for controller.combat() that returns a character to control the combat
	// actions when fighting a monster
	private char getCombatChoice(){
		System.out.println("");
		// prints the possible combat commands
		System.out.println("Combat Options: ");
    	System.out.println("(A)ttack or (B)lock");
    	System.out.print("--> ");
    	// creates a String for user input
    	String input = "empty";
    	// creates a new object of the class Scanner to take in input from the keyboard
		Scanner in = new Scanner(System.in);
		try {
			// tries to set the next token in the input to a lower case String
			input = in.next().toLowerCase();
		} catch (Exception e) {
			// prints the stack trace for the Exception if there is a problem with setting the String to lower case
			e.printStackTrace(System.err);
		}
		System.out.println("");
		// returns the first character in the String
		return input.charAt(0);
	}
	// main method to start the program
    public static void main(String[] args) {
    	// creates a new object of the class MainMenu to run the program
    	MainMenu game = new MainMenu();
    	// calls the run() method to run the the game
    	game.run();
    }
}
