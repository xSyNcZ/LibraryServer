import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame{
	
	private JButton startButton;
    private JButton stopButton;
    private JTextArea logTextArea;
    private Server server;

    public ServerGUI() {
        setTitle("Server GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
            }
        });
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Server");
        stopButton = new JButton("Stop Server");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);

        add(buttonPanel, BorderLayout.NORTH);
        add(new JScrollPane(logTextArea), BorderLayout.CENTER);
    }

    private void startServer() {
        logMessage("Starting server...");
        server = new Server();

        // Uruchom wątek serwera
        new Thread(new Runnable() {
            @Override
            public void run() {
                server.startServer();
            }
        }).start();

        logMessage("Server started on port 4444");
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private void stopServer() {
        logMessage("Stopping server...");
        server.stopServer();
        logMessage("Server stopped.");
        // Nie zmieniaj stanu przycisku, aby GUI pozostało aktywne
    }

    private void logMessage(String message) {
        logTextArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI().setVisible(true);
            }
        });
    }
}