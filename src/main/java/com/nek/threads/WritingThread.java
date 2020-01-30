package com.nek.threads;

import java.io.PrintWriter;
import java.util.Scanner;

import com.nek.DBManager;

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
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String msg = scanner.nextLine();
			writer.println(msg);
			if (safe) {
				DBManager.insert(ip, msg, false);
			}
		}
		scanner.close();
	}
}
