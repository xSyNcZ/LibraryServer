import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private boolean isRunning;

    public Server() {
        isRunning = false;
    }

    public void run() {
        while (isRunning) {
            try {
                serverSocket = new ServerSocket(4444);
                System.out.println("SERVER: Server connection opened on port 4444.");

                // after this method server stops and waits for client connection
                Socket clientSocket = serverSocket.accept();
                serverSocket.close();

                System.out.println("SERVER: Accepted client connection on port 4444.");

                LibraryServerCommuncationThread thread = new LibraryServerCommuncationThread(clientSocket);
                thread.start();
            } catch (IOException e) {
                System.err.println("SERVER: Accept failed: 4444, " + e);
            }
        }
    }

    public void startServer() {
        isRunning = true;
        start();
    }

    public void stopServer() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("SERVER: Error while closing server socket: " + e);
        }
    }
}