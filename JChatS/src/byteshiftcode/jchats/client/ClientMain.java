package byteshiftcode.jchats.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * delegated main called by Main class to run client
 */
public class ClientMain {
	public void start() {
		System.out.println("Client Setup Starting.");
		Scanner in = new Scanner(System.in);
		System.out.println("What is the URL server?");
		String url = in.nextLine();
		System.out.println("What is the port number of the server?");
		int port = in.nextInt();
		in.nextLine();
		System.out
				.println("Connecting, type \"/\" to begin a command, type \"/help\" for list of commands");
		try {
			Socket socket = new Socket(url, port);
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			// This will wait for the server to send the string to the client
			// saying a connection
			// has been made.
			String inputString = input.readLine();
			System.out.println(inputString);
			// Again, here is the code that will run the client, this will
			// continue looking for
			// input from the user then it will send that info to the server.
			while (true) {
				// Here we look for input from the user
				String userInput = in.nextLine();
				if (userInput.startsWith("/")) {
					if (userInput.equals("/help")) {
						System.out
								.println("commands:\n\t/help\tthis help text\n\t/quit\tdisconects and quits client");
					} else if (userInput.equals("/quit")) {
						System.out.println("Disconecting");
						System.out.println("Closing input");
						in.close();
						System.out.println("Closing socket");
						socket.close();
						System.out.println("Quitting");
					}
				} else {
					// Now we write it to the server
					output.println(userInput);
				}
			}
		} catch (IOException exception) {
			System.out.println("Error: " + exception);
		}
	}
}
