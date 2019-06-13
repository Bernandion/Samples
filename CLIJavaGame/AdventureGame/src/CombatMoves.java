/**
 * @(#)CombatMoves.java
 *
 * Interface that provides methods for which anything in the game that is
 * able to fight must use. Implemented by the Player and the Monsters so
 * that they are able to deal and take damage from eachother in combat().
 *
 * @author Brandon Verigin
 * @version 1.00 2015/3/12
 */


public interface CombatMoves {
	// method that returns an int which is put into the damage parameter in block().
	// The int returned should be whatever the damage is for the attacking creature,
	// and the integer for modifier is used however appropriate for the specific implementing
	// class.
	int attack(int modifier);
	// method that takes an int for damage, and changes the properties (health) of the 
	// blocking creature by whatever the integer for damage is, which is taken from attack().
	// The integer for modifier is used however appropraite for the specific implementing
	// class that is blocking an attack, or taking damage from an attack.
	void block(int damage, int modifier);
}