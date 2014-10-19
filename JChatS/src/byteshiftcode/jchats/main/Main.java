package byteshiftcode.jchats.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import byteshiftcode.jchats.client.ClientMain;
import byteshiftcode.jchats.server.ServerMain;

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
		int choice = -1;
		Scanner in = new Scanner(System.in);
		do {
			// prompt
			System.out
					.println("Would you like to:\n\t(0) Run a server.\n\t(1) Connect to a server.");
			// reads choice
			try {
				choice = in.nextInt();
				in.nextLine();
			} catch (InputMismatchException ime) {
				System.out
						.println("Not a legal input, the police have been dispatched to your location (Ha Ha, kidding).");
				System.exit(-1);
			}
		} while (!(choice == 0 | choice == 1));
		// giant if
		if (choice == 0) {
			String confirm = "";
			// prompt
			System.out
					.println("Are you sure you want to start a server?\n\t[ yes / (no) ]");
			// reads choice
			try {
				confirm = in.nextLine();
			} catch (InputMismatchException ime) {
				System.out
						.println("Not a legal input, the police have been dispatched to your location (Ha Ha, kidding).");
				System.exit(-1);
			}
			// see if it is "yes" or "Yes or "YES""
			if (confirm.equals("yes") | confirm.equals("Yes")
					| confirm.equals("YES")) {
				new ServerMain().start();
			} else {
				System.out.println("Not starting server.");
			}
			// closes input
			in.close();
		} else if (choice == 1) {
			// closes input
			in.close();
			new ClientMain().start();
		} else {
			// closes input
			in.close();
			System.exit(-1);
		}
	}
}