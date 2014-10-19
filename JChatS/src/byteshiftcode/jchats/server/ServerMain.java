package byteshiftcode.jchats.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

/**
 * delegated main called by Main class to run server
 */
public class ServerMain {
	String serverName = "defName";

	public void start() {
		System.out.println("Server Setup Starting.");
		int port = -1;
		final Scanner in = new Scanner(System.in);
		System.out.println("What port will this server be on?");
		port = in.nextInt();
		in.nextLine();
		System.out.println("What is the name of this server?");
		serverName = in.nextLine();
		// We need a try-catch because lots of errors can be thrown
		try {
			final ServerSocket sSocket = new ServerSocket(port);
			System.out.println("Server started at: " + new Date());

			// start connect thread
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("Server function thread started.");

					// Loop that runs server functions
					while (true) {
						// Wait for a client to connect
						Socket socket = null;
						try {
							socket = sSocket.accept();
							System.out.println("Reciving connection from: "
									+ socket.getInetAddress());
							// Create a new custom thread to handle the
							// connection
							ClientThread clientThread = new ClientThread(socket);

							// Start the thread!
							new Thread(clientThread).start();
						} catch (IOException e) {
							System.out.println("Error: " + e);
						}

					}
				}
			}).start();

			// start local command thread
			new Thread(new Runnable() {

				@Override
				public void run() {

					// Loop that runs local server functions
					while (true) {
						System.out
								.println("Server commands thread started.\n\tType \"help\" to list commands");
						String cmd = in.nextLine();
						if (cmd.equals("help")) {
							System.out
									.println("Commands:\n\thelp\tthis help text\n\tstop\tshutdown the server");
						} else if (cmd.equals("help")) {
							System.out.println("Stopping input.");
							in.close();
							System.out.println("Stopping socket.");
							try {
								sSocket.close();
							} catch (IOException e) {
								System.err.println("Error closing socket.");
							}
							System.out.println("Stopping server.\n\nBye!");
							System.exit(0);
						}
					}
				}
			}).start();

		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}

	// Here we create the ClientThread inner class and have it implement
	// Runnable
	// This means that it can be used as a thread
	class ClientThread implements Runnable {
		Socket threadSocket;

		// This constructor will be passed the socket
		public ClientThread(Socket socket) {
			// Here we set the socket to a local variable so we can use it later
			threadSocket = socket;
		}

		public void run() {
			// All this should look familiar
			try {
				// Create the streams
				PrintWriter output = new PrintWriter(
						threadSocket.getOutputStream(), true);
				BufferedReader input = new BufferedReader(
						new InputStreamReader(threadSocket.getInputStream()));

				// Tell the client that he/she has connected
				output.println("You have connected to:" + serverName + "at: "
						+ new Date());

				while (true) {
					// This will wait until a line of text has been sent
					String chatInput = input.readLine();
					System.out.println(chatInput);
				}
			} catch (IOException exception) {
				System.out.println("Error: " + exception);
			}
		}
	}
}
