

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {

		while (true) {
			ServerSocket serverSocket = null;
			try {
				serverSocket = new ServerSocket(4444);
			} catch (IOException e) {
				System.err.println("SERVER: Could not listen on port: 4444, " + e);
				System.exit(1);
			}
			System.out.println("SERVER: Server connection opened on port 4444.");

			Socket clientSocket = null;
			try {
				// after this method server stops and waits for client connection
				clientSocket = serverSocket.accept();
				serverSocket.close();
			} catch (IOException e) {
				System.err.println("SERVER: Accept failed: 4444, " + e);
				System.exit(1);
			}
			System.out.println("SERVER: Accepted client connecion on port 4444.");
			LibraryServerCommuncationThread thread = new LibraryServerCommuncationThread(clientSocket);
			thread.start();
		}
	}
}
