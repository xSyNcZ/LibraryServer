import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientGUI extends JFrame {

    private JTextArea clientTextArea;
    private JTextField commandTextField;
    private Client client;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientGUI().setVisible(true);
            }
        });
    }

    public ClientGUI() {
        setTitle("Client GUI");
        setSize(847, 411);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeComponents();

        client = new Client(this);

        commandTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = commandTextField.getText();
                if (!command.isEmpty()) {
                    client.sendUserInput(command);
                    commandTextField.setText(""); // Wyczyszczenie pola po wysłaniu komendy
                }
            }
        });
    }

    private void initializeComponents() {
        getContentPane().setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        commandTextField = new JTextField();
        clientTextArea = new JTextArea();
        clientTextArea.setEditable(false);

        getContentPane().add(commandTextField, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(clientTextArea), BorderLayout.CENTER);
    }

    public void appendToTextArea(String message) {
        clientTextArea.append(message + "\n");
    }
}
