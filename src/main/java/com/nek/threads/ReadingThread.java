package com.nek.threads;

import java.io.BufferedReader;
import java.io.IOException;

import com.nek.DBManager;

public class ReadingThread extends Thread {
	private final BufferedReader bufferedReader;
	private final String ip;
	private final boolean safe;

	public ReadingThread(BufferedReader bufferedReader, String ip, boolean safe) {
		this.bufferedReader = bufferedReader;
		this.ip = ip;
		this.safe = safe;
	}

	@Override
	public void run() {
		String inputLine = null;
		try {
			inputLine = bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (inputLine != null) {
			if (safe) {
				DBManager.insert(ip, inputLine, true);
			}
			System.out.println("    " + inputLine);
			try {
				inputLine = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
