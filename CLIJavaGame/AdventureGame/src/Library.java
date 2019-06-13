/**
 * @(#)Library.java
 *
 * Class that allows the user to see information on all the possible
 * Monsters in the game and control how they view the information in
 * different ways.
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */
 import java.util.*;

public class Library {
	// creates an array of type Monsters that contains objects of every Monster type
	private Monsters[] monsters = {new Orc(), new Treant(), new Golem(), new Dragon()};
	// method that uses the static sort method in Arrays and the defined way to sort
	// the Monsters in the Monsters class to sort the Monsters in acsending order according
	// to each of the letters in their names
    private void sortName(){
    	Arrays.sort(monsters, Monsters.getNameComparator());
    }
    // method that uses the static sort method in Arrays and the defined way to sort
	// the Monsters in the Monsters class to sort the Monsters in acsending order according
	// to each of the integer values for their health
    private void sortHealth(){
    	Arrays.sort(monsters, Monsters.getHealthComparator());
    }
    // method that takes a passed String and finds the Monster in the array that 
    // has a name that matches the String and displays information on that Monster
    private void findName(String choice) {
    	// uses an enhanced for loop to cycle through the array of Monsters
    	for(Monsters mon : monsters){
    		// if a Monster in the array has a name equal to the String, displays
    		// information on that Monster and stops the method
    		if (mon.toString().equals(choice)){
    			System.out.println("\n\tName: " + mon.toString());
    			// uses a switch of the Monster's health to display information on
    			// the possible range of health for that specific Monster
    			switch(mon.getHealth()){
    				case 7 :
    				case 8 :
    				case 9 : System.out.println("\tHealth: 7-9"); break;
    				case 12 :
    				case 13 :
    				case 14 : System.out.println("\tHealth: 12-14"); break;
    				case 17 :
    				case 18 :
    				case 19 : System.out.println("\tHealth: 17-19"); break;
    				case 22 :
    				case 23 :
    				case 24 : System.out.println("\tHealth: 22-24"); break;
    				// if the Monster's health is somehow not the others, outputs a message
    				default : System.out.println("HEALTH OUT OF BOUNDS"); break;
    			}
    			// uses a switch of the Monster's power to display information on
    			// the possible range of power for that specific Monster
    			switch(mon.getPower()){
    				case 4 :
    				case 5 : System.out.println("\tPower: 4-5"); break;
    				case 6 :
    				case 7 : System.out.println("\tPower: 6-7"); break;
    				case 8 :
    				case 9 : System.out.println("\tPower: 8-9"); break;
    				case 10 :
    				case 11 : System.out.println("\tPower: 10-11"); break;
    				// if the Monster's power is somehow not the others, outputs a message
    				default : System.out.println("POWER OUT OF BOUNDS"); break;
    			}
    			return;
    		}
    	}
    	// prints a message indicating that the specific String for the Monster's name
    	// isn't in the array
    	System.out.println("No monster of that name could be found!");
    }
    // method that cycles through each Monster in the array and displays the information
    // for each one
    private void displayLibrary() {
    	// uses an enhanced for loop to cycle through the array of Monsters
    	for (Monsters mon : monsters){
    		System.out.println("\n\tName: " + mon.toString());
    		// uses a switch of the Monster's health to display information on
    		// the possible range of health for that specific Monster
    		switch(mon.getHealth()){
    			case 7 :
    			case 8 :
    			case 9 : System.out.println("\tHealth: 7-9"); break;
    			case 12 :
    			case 13 :
    			case 14 : System.out.println("\tHealth: 12-14"); break;
    			case 17 :
    			case 18 :
    			case 19 : System.out.println("\tHealth: 17-19"); break;
    			case 22 :
    			case 23 :
    			case 24 : System.out.println("\tHealth: 22-24"); break;
    			// if the Monster's health is somehow not the others, outputs a message
    			default : System.out.println("HEALTH OUT OF BOUNDS"); break;
    		}
    		// uses a switch of the Monster's power to display information on
    		// the possible range of power for that specific Monster
    		switch(mon.getPower()){
    			case 4 :
    			case 5 : System.out.println("\tPower: 4-5"); break;
    			case 6 :
    			case 7 : System.out.println("\tPower: 6-7"); break;
    			case 8 :
    			case 9 : System.out.println("\tPower: 8-9"); break;
    			case 10 :
    			case 11 : System.out.println("\tPower: 10-11"); break;
    			// if the Monster's power is somehow not the others, outputs a message
    			default : System.out.println("POWER OUT OF BOUNDS"); break;
    		}
    	}
    }
    // method that is called in displayLibrary() that performs a certain action on the
    // array of Monsters and displays them depending on the user entered String that is passed
    public boolean controlLibrary(String choice){
    	// uses a switch of the passed String to determine what happens
   		switch(choice){
   			// if the String is "n", sorts the array of Monsters according to name and displays them.
   			// Returns true to keep viewing the library
   			case "n" : sortName(); displayLibrary(); return true;
   			// if the String is "h", sorts the array of Monsters according to health and displays them.
   			// Returns true to keep viewing the library
   			case "h" : sortHealth(); displayLibrary(); return true;
   			// if the String is "q", returns false to stop viewing the Library of Monsters
   			case "q" : return false;
   			// if the String doesn't match any of the others, calls findName() to show information on the
   			// Monster whos name matches the entered String 
   			default : findName(choice); return true;
   		}
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
