package com.nek;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
	public static AtomicBoolean connected = new AtomicBoolean(false);
	
	public static void main(String[] args) throws IOException {
		final Scanner scanner = new Scanner(System.in);
		final String msg1 = "Do you want to connect or listen (c/l): ";
		final String msg2 = "Do you want to safe the chat history or continue from an existing chat history (if there"
				+ " already exists one for the ip) (y/n): ";
		final String errMsg = "Wrong input please try again";

		final String answer = checkInput(errMsg, "c", "l", msg1, scanner);
		final String answer2 = checkInput(errMsg, "y", "n", msg2, scanner);

		boolean safe = false;

		switch (answer2) {
		case "y":
			safe = true;
			DBManager.createDb();
			break;
		case "n":
			safe = false;
			break;
		}

		switch (answer) {
		case "c":
			System.out.print("Enter server ip and press enter: ");
			final String ip = scanner.next();
			System.out.print("Enter server port and press enter: ");
			final int port = scanner.nextInt();
			final Client client = new Client(ip, port, safe);
			client.run();
			break;
		case "l":
			System.out.print("What port do you want to listen on (0 to 65535): ");
			final int listenerPort = scanner.nextInt();
			final Server server = new Server(listenerPort, safe);
			server.run();
			break;
		}

		scanner.close();
	}

	private static String checkInput(String errMsg, String input1, String input2, String msg, Scanner scanner) {
		System.out.print(msg);
		String answer = scanner.next();
		while (!answer.equals(input1) && !answer.equals(input2)) {
			System.out.println(errMsg);
			System.out.print(msg);
			answer = scanner.next();
		}
		return answer;
	}
}
