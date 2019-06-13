/**
 * @(#)Map.java
 *
 * Class that provides a completely different Environment, Monster, and Weapon
 * for each section of the nested Map array in GameController. Provides information
 * on each area as the Player traverses through each Map. Implements Serializable to
 * allow for the objects of the class to be serialized and written and read in a file.
 * Has inner class Environment that sets up the nature information for each Map object.
 * The inner class Environment also determines which part of nature has a hidden Weapon.
 * It also implements Serializable for the same reason as the Map class.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
import java.util.*;
import java.io.*;

public class Map implements Serializable {
	// constant and static variable needed for the class to be recognized properly when serialized for saving and loading
	private static final long serialVersionUID = 28L;
	// reference to an object of type Monster that only gets set to an object if the certain Map has a Monster in it
	private Monsters monster;
	// reference to an object of type Weapons that is set to a Weapon based on the passed map number in the constructor
	private Weapons weapon;
	// creates a new object of the class Environment that represents the environment for each specific Map
	private Environment environ = new Environment();
	// boolean variable that is set to false until the Player finds the Weapon that is hidden in the specific
	// Map object
	private boolean weaponfound = false;
	// constructor that takes the passed map number and sets the Map object's Weapon and Monster based on the
	// passed value
    public Map(int mapnum) {
    	// sets the Weapon in this part of the map to a Weapon object of the current map number
    	weapon = new Weapons(mapnum);
    	// creates a new object of the class Random
    	Random rand = new Random();
    	// if the passed map number is 1 to 3, and a random int from 0 to 2 is 1 or 2,
    	// creates a new Orc object and sets the monster reference to it
    	if ((mapnum>=1)&&(mapnum<=3)&&(rand.nextInt(3)>=1)){
    		monster = new Orc();
    	}
    	// if the passed map number is 4 to 6, and a random int from 0 to 2 is 1 or 2,
    	// creates a new Treant object and sets the monster reference to it
    	else if ((mapnum>=4)&&(mapnum<=6)&&(rand.nextInt(3)>=1)){
    		monster = new Treant();
    	}
    	// if the passed map number is 7 to 9, and a random int from 0 to 2 is 1 or 2,
    	// creates a new Golem object and sets the monster reference to it
    	else if ((mapnum>=7)&&(mapnum<=9)&&(rand.nextInt(3)>=1)){
    		monster = new Golem();
    	}
    	// if the passed map number is 10, creates a Dragon object and sets the monster
    	// reference to it
    	else if (mapnum==10){
    		monster = new Dragon();
    	}
    }
    // private inner class that represents the natural Environment for each Map object,
    // also determines which part of nature has a Weapon
    private class Environment implements Serializable {
    	// constant and static variable needed for the class to be recognized properly when serialized for saving and loading
    	private static final long serialVersionUID = 42L;
		// String reference that is populated randomly by the constructor
		private String nature;
		// int variable that is either 0, 1, or 2 and represents where the Weapon is on the Map
		private int weaponnaturechance = -1;
		// constructor that randomly creates the String for nature and randomly
		// sets the weaponnaturechance integer for where the Weapon is located
    	public Environment() {
    		// Strings for each kind of nature that are initially empty
    		String bush = "";
    		String tree = "";
    		String rock = "";
    		// integer variable that is used to to allow up to 2 parts of nature to be set
    		int count = 0;
    		// creates a new object of the class Random
    		Random rand = new Random();
    		// iterates 2 times to allow for the Strings for the nature to be set with a 
    		// maximum of 2 different parts of nature. If the same part of nature is set
    		// twice in a row, then the current Map object will only have 1 nature part
    		// in the area
    		while(count<2){
    			// switch with a random integer from 0 to 2
    			switch(rand.nextInt(3)){
    				// if the int is 0, sets the bush String to be part of the environment
    				case 0 : bush = "bush"; break;
    				// if the int is 1, sets the tree String to be part of the environment
    				case 1 : tree = "tree"; break;
    				// if the int is 2, sets the rock String to be part of the environment
    				case 2 : rock = "rock"; break;
    				// if somehow the int is not the others, outputs a message
    				default : System.out.println("ENVIRON PROBLEM"); break;
    			}
    			// increments the count integer
    			count++;
    		}
    		// sets the String nature reference to whatever nature part Strings that
    		// happened to be created concatenated to eachother
    		nature = bush + tree + rock;
    		// gets a random int from 0 to 1 that determines where the Weapon is hidden in nature
    		int randnum = rand.nextInt(2);
    		// switch with the nature String of concatenated parts that sets the integer for where the
    		// Weapon is located based on the random int, randnum, and the passed String
    		switch(nature){
    			// if the nature String is "bushtree"...
    			case "bushtree" : {
    				// if randnum is equal to 0, sets the Weapon to be hidden by the bush
    				if (randnum==0) weaponnaturechance = 2;
    				// else sets the Weapon to be hidden by the tree
    				else weaponnaturechance = 1;
    				break;
    			}
    			// if the nature String is "bushrock"...
    			case "bushrock" : {
    				// if randnum is equal to 0, sets the Weapon to be hidden by the bush
    				if (randnum==0) weaponnaturechance = 2;
    				// else sets the Weapon to be hidden by the rock
    				else weaponnaturechance = 0;
    				break;
    			}
    			// if the nature String is "treerock"...
    			case "treerock" : {
    				// if randnum is equal to 0, sets the Weapon to be hidden by the tree
    				if (randnum==0) weaponnaturechance = 1;
    				// else sets the Weapon to be hidden by the rock
    				else weaponnaturechance = 0;
    				break;
    			}
    			// if the nature String is just "rock", sets the Weapon to be hidden by the rock
    			case "rock" : weaponnaturechance = 0; break;
    			// if the nature String is just "tree", sets the Weapon to be hidden by the tree
    			case "tree" : weaponnaturechance = 1; break;
    			// if the nature String is just "bush", sets the Weapon to be hidden by the bush
    			case "bush" : weaponnaturechance = 2; break;
    			// if somehow the String isn't any of the others, outputs a message
    			default : System.out.println("WEAP CHANCE NUM PROBLEM"); break;
    		}
    	}
    	// accessor method that returns the nature String for the Environment.
    	// Called in descripion() in Map
    	public String getEnvironment(){
    		return nature;
    	}
    	// accessor method that returns the weaponnaturechance integer for where the Weapon is hidden for the Environment.
    	// Called in getChanceNumber() in Map
    	public int getWeaponChance(){
    		return weaponnaturechance;
    	}
	}
    // accessor method that returns the String for the description of the Environment for the Map object.
    // Called in displayArea()
    public String description(){
    	return environ.getEnvironment();
    }
    // method that is called in playerAction() that returns the Weapon for the certain Map object when the Player
    // finds it in that section and sets the weaponfound bool to true, indicating that the Weapon has been found
    // for the Map
    public Weapons getWeapon(){
    	this.weaponfound = true;
    	return weapon;
    }
    // accessor method that is called in playerMover() that either returns the Monster for the current section
    // of the Map or returns null if there is no Monster in that section
    public Monsters getMonster(){ 
    	return monster;
    }
    // accessor method that is called in playerAction() that returns the integer that represents which
    // part of nature where the hidden Weapon is located for the Map
    public int getChanceNumber(){
    	return environ.getWeaponChance();
    }
    // accessor method that is called in playerAction() that returns the boolean variable for if 
    // the Weapon has been found in the Map section yet.
    public boolean isWeaponFound(){
    	return weaponfound;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
