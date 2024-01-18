import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4444);
            System.out.println("CLIENT: Server connected on port 4444");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ExecutorService executor = Executors.newFixedThreadPool(2);

            executor.submit(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null && !serverResponse.equals("END")) {
                    	//System.out.println("Server: " + serverResponse);
                    	System.out.println(serverResponse);
                    }
                } catch (Exception e) {
                    System.err.println("CLIENT: Exception in serverReader: " + e);
                }
            });

            executor.submit(() -> {
                try {
                    BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
                    String userInput;
                    while (true) {
                        userInput = console.readLine();
                        out.println(userInput);
                        out.flush();
                        if (userInput.equals("5")) {
                            break;
                        }
                    }
                    // Close the server connection after client sends "5"
                    socket.close();
                } catch (Exception e) {
                    System.err.println("CLIENT: Exception in userInputThread: " + e);
                }
            });

            executor.shutdown();
        } catch (Exception e) {
            System.err.println("CLIENT: Exception:  " + e);
        }
    }
}
