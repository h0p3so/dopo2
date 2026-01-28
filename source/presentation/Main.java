/**
 * Defines the program's entry point.
 * Refer to the project's README file for requirement and
 * instructtions on how to run the game properly.
 * 
 * @author juand
 */
package presentation;

import domain.Control;

public final class Main {
	public static final void main (final String [] args) {
		BaDopoCreamGUI.getInstance(new Control());
	}
}
