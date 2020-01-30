package com.nek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.nek.threads.ReadingThread;
import com.nek.threads.WritingThread;

class Client {
	private final String ip;
	private final int port;
	private final boolean safe;

	Client(String ip, int port, boolean safe) {
		this.ip = ip;
		this.port = port;
		this.safe = safe;
	}

	void run() {

		try (final Socket socket = new Socket(ip, port);
				// Get the input stream (sequence of bytes)
				final InputStream inputStream = socket.getInputStream();
				// InputStreamReader takes a byte stream and converts it into a character stream
				final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				// Reads more than 1 char from the stream and safes them into a buffer
				final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				// Get the output stream (sequence of bytes)
				final OutputStream outputStream = socket.getOutputStream();
				// Use PrintWriter to make the output stream buffered and work with characters
				final PrintWriter writer = new PrintWriter(outputStream, true);) {
			Main.connected.set(true);
			System.out.println("Connected\n");
			if (safe) {
				DBManager.select(ip);
			}
			final Thread readingThread = new ReadingThread(bufferedReader, ip, safe);
			final Thread writingThread = new WritingThread(writer, ip, safe);

			readingThread.start();
			writingThread.start();

			try {
				readingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				writingThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Program terminated");
	}
}
