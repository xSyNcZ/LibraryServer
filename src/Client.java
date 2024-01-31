import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ExecutorService executor;
    private ClientGUI clientGUI;

    public Client(ClientGUI clientGUI) {
        try {
            this.clientGUI = clientGUI;
            socket = new Socket("localhost", 4444);
            System.out.println("CLIENT: Server connected on port 4444");

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            executor = Executors.newFixedThreadPool(2);

            executor.submit(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null && !serverResponse.equals("END")) {
                        handleServerResponse(serverResponse);
                    }
                } catch (Exception e) {
                    System.err.println("CLIENT: Exception in serverReader: " + e);
                }
            });

        } catch (Exception e) {
            System.err.println("CLIENT: Exception:  " + e);
        }
    }

    private void handleServerResponse(String response) {
        // Zamiast wypisywać na konsolę, teraz wywołujemy metodę appendToTextArea w ClientGUI
        clientGUI.appendToTextArea(response);
        // Tutaj możesz umieścić logikę obsługi odpowiedzi serwera
    }

    public void sendUserInput(String userInput) {
        out.println(userInput);
        out.flush();
        if (userInput.equals("5")) {
            closeConnection();
        }
    }

    private void closeConnection() {
        try {
            socket.close();
            executor.shutdown();
        } catch (Exception e) {
            System.err.println("CLIENT: Exception while closing connection: " + e);
        }
    }

    // Dodaj metody obsługujące różne komendy
    public void showLibrary() {
        // Logika dla polecenia "Show library"
        sendUserInput("SHOW_LIBRARY_COMMAND");
    }

    public void addLibrary() {
        // Logika dla polecenia "Add new library"
        sendUserInput("ADD_LIBRARY_COMMAND");
    }

    public void duplicateBook() {
        // Logika dla polecenia "Duplicate existing book"
        sendUserInput("DUPLICATE_BOOK_COMMAND");
    }

    public void deleteBook() {
        // Logika dla polecenia "Delete existing book"
        sendUserInput("DELETE_BOOK_COMMAND");
    }

    public void addBook() {
        // Logika dla polecenia "Add new book"
        sendUserInput("ADD_BOOK_COMMAND");
    }

    public void exit() {
        // Logika dla polecenia "Exit the program"
        sendUserInput("EXIT_COMMAND");
    }
}