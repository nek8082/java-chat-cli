package com.nek.threads;

import java.io.PrintWriter;
import java.util.Scanner;

import com.nek.DBManager;
import com.nek.Main;

public class WritingThread extends Thread {
	private final PrintWriter writer;
	private final String ip;
	private final boolean safe;

	public WritingThread(PrintWriter writer, String ip, boolean safe) {
		this.writer = writer;
		this.ip = ip;
		this.safe = safe;
	}
	
	@Override
	public void run() {
		final Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine() && Main.connected.get()) {
			String msg = scanner.nextLine();
			if (msg == null) {
				break;
			}
			writer.println(msg);
			if (safe) {
				DBManager.insert(ip, msg, false);
			}
			if (msg.equals("exit")) {
				Main.connected.set(false);
				System.out.println("Disconnected wait till other participant presses enter or terminate program manually");
			}
		}
		scanner.close();
	}
}
