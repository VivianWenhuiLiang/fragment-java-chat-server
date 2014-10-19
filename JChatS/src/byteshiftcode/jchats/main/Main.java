package byteshiftcode.jchats.main;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * class run at start of program, will allow both arguments and console prompts
 * to switch to client or server
 * 
 */
public class Main {
	public static void main(String[] args) {
		// program starts here
		System.out.println("Welcome to JChatS");
		// prompt input for starting a server or connecting to one
		int choice;
		Scanner in = new Scanner(System.in);
		// prompt
		System.out
				.println("Would you like to:\n\t(0) Run a server.\n\t(1) Connect to a server.");
		// reads choice
		try {
			choice = in.nextInt();
		} catch (InputMismatchException ime) {
			System.out
					.println("Not a legal input, the police have been dispatched to your location (Ha Ha, kidding).");
			System.exit(-1);
		}
		// closes input
		in.close();
	}
}